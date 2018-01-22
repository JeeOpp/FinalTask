package dao;

import dao.connectionPool.ConnectionPool;
import dao.connectionPool.ConnectionPoolException;
import entity.Client;
import entity.Review;
import entity.Taxi;
import entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DNAPC on 12.12.2017.
 */
public class FeedbackDAO {
    private static final Logger log = Logger.getLogger(FeedbackDAO.class.getClass());
    private static final String SQL_SET_REVIEW = "INSERT INTO taxisystem.review (client_id, taxi_id, comment) VALUES (?, ?, ?);";
    private static final String SQL_GET_CLIENT_REVIEW = "SELECT taxi.id, taxi.name, taxi.surname, review.`comment` FROM review JOIN taxi ON review.taxi_id = taxi.id WHERE review.client_id = ?; ";
    private static final String SQL_GET_TAXI_REVIEW = "SELECT client.id, client.name, client.surname, review.`comment` FROM review JOIN client ON review.client_id = client.id WHERE review.taxi_id = ?;";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    public FeedbackDAO() {
    }

    public void setReview(Review review) throws SQLException {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SET_REVIEW);
            preparedStatement.setInt(1, review.getClient().getId());
            preparedStatement.setInt(2, review.getTaxi().getId());
            preparedStatement.setString(3, review.getComment());
            preparedStatement.execute();
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
    }
    public List<Review> getClientReviews(User user) throws SQLException{
        List<Review> reviewList = new ArrayList<>();
        Review review;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_CLIENT_REVIEW);
            preparedStatement.setInt(1, user.getId());
            if ((resultSet = preparedStatement.executeQuery()) != null) {
                while (resultSet.next()) {
                    review = new Review();
                    int taxiId = resultSet.getInt(1);
                    String taxiName = resultSet.getString(2);
                    String taxiSurname = resultSet.getString(3);
                    review.setTaxi(new Taxi(taxiId, taxiName, taxiSurname));
                    String comment = resultSet.getString(4);
                    review.setComment(comment);
                    reviewList.add(review);
                }
            }
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection,preparedStatement,resultSet);
        }
        return reviewList;
    }
    public List<Review> getTaxiReviews(User user) throws SQLException{
        List<Review> reviewList = new ArrayList<>();
        Review review;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_TAXI_REVIEW);
            preparedStatement.setInt(1, user.getId());
            if ((resultSet = preparedStatement.executeQuery()) != null) {
                while (resultSet.next()) {
                    review = new Review();
                    int clientId = resultSet.getInt(1);
                    String clientName = resultSet.getString(2);
                    String clientSurname = resultSet.getString(3);
                    review.setClient(new Client(clientId, clientName, clientSurname));
                    String comment = resultSet.getString(4);
                    review.setComment(comment);
                    reviewList.add(review);
                }
            }
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection,preparedStatement,resultSet);
        }
        return reviewList;
    }
}
