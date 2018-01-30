package dao.impl;

import dao.FeedbackDAO;
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
 * A DAO class uses to read or change data in database and send it to the modal layer.
 */
public class FeedbackDAOImpl implements FeedbackDAO {
    private static final Logger log = Logger.getLogger(FeedbackDAOImpl.class.getClass());
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public FeedbackDAOImpl() {
    }

    /**
     * Write a review information to the database.
     *
     * @param review contains information about review there are client id, taxi id and review text.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public void setReview(Review review) throws SQLException {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_SET_REVIEW);
            preparedStatement.setInt(1, review.getClient().getId());
            preparedStatement.setInt(2, review.getTaxi().getId());
            preparedStatement.setString(3, review.getComment());
            preparedStatement.execute();
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Read a reviews which the client give before and forms it to the list.
     *
     * @param user used to identify client.
     * @return a reviews formed into the list.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public List<Review> getClientReviews(User user) throws SQLException {
        List<Review> reviewList = new ArrayList<>();
        Review review;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_CLIENT_REVIEW);
            preparedStatement.setInt(1, user.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                review = new Review();
                int taxiId = resultSet.getInt(1);
                String taxiName = resultSet.getString(2);
                String taxiSurname = resultSet.getString(3);
                review.setTaxi((Taxi) new Taxi.TaxiBuilder().
                        setId(taxiId).
                        setFirstName(taxiName).
                        setLastName(taxiSurname).build());
                String comment = resultSet.getString(4);
                review.setComment(comment);
                reviewList.add(review);
            }
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return reviewList;
    }

    /**
     * Read a client's reviews which clients are send to the taxi driver before.
     *
     * @param user used to identify taxi driver.
     * @return a reviews formed into the list.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public List<Review> getTaxiReviews(User user) throws SQLException {
        List<Review> reviewList = new ArrayList<>();
        Review review;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_TAXI_REVIEW);
            preparedStatement.setInt(1, user.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                review = new Review();
                int clientId = resultSet.getInt(1);
                String clientName = resultSet.getString(2);
                String clientSurname = resultSet.getString(3);
                review.setClient((Client)new Client.ClientBuilder().
                        setId(clientId).
                        setFirstName(clientName).
                        setLastName(clientSurname).build());
                String comment = resultSet.getString(4);
                review.setComment(comment);
                reviewList.add(review);
            }
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return reviewList;
    }
    /**
     * Read all information about reviews from database and forms it into the collection list.
     *
     * @return a list contained all reviews information.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public List<Review> getReviewList() throws SQLException {
        List<Review> reviewList = new ArrayList<>();
        Review review;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_REVIEW);
            while (resultSet.next()) {
                review = new Review();
                review.setFromResultSet(resultSet);
                reviewList.add(review);
            }
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return reviewList;
    }

    /**
     * Delete information about specific review from database.
     * @param reviewId review we want to delete
     *
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public boolean deleteReview(int reviewId) throws SQLException {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_REVIEW);
            preparedStatement.setInt(1, reviewId);
            preparedStatement.executeUpdate();
            return true;
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
        return false;
    }
}