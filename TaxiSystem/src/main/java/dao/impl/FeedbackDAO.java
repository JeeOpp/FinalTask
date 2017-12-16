package dao.impl;

import dao.WrappedConnector;
import entity.Client;
import entity.Review;
import entity.Taxi;
import entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DNAPC on 12.12.2017.
 */
public class FeedbackDAO {
    private WrappedConnector connector;

    public FeedbackDAO() {
        this.connector = new WrappedConnector();
    }

    public FeedbackDAO(WrappedConnector connector) {
        this.connector = connector;
    }

    public void close() {
        connector.closeConnection();
    }

    public void setReview(Review review) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connector.setReviewPreparedStatement();
            preparedStatement.setInt(1, review.getClient().getId());
            preparedStatement.setInt(2, review.getTaxi().getId());
            preparedStatement.setString(3, review.getComment());
            preparedStatement.execute();
        }catch (SQLException ex){
            System.err.println("SQL exception (request or table failed): " + ex);
        }finally {
            connector.closePreparedStatement(preparedStatement);
        }
    }
    public List<Review> getClientReviews(User user) throws SQLException{
        List<Review> reviewList = new ArrayList<>();
        Review review = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connector.getReviewClientPreparedStatement();
            preparedStatement.setInt(1,user.getId());
            if((resultSet = preparedStatement.executeQuery()) != null){
                while (resultSet.next()){
                    review = new Review();
                    Integer taxiId = resultSet.getInt(1);
                    String taxiName = resultSet.getString(2);
                    String taxiSurname = resultSet.getString(3);
                    review.setTaxi(new Taxi(taxiId, taxiName,taxiSurname));
                    String comment = resultSet.getString(4);
                    review.setComment(comment);
                    reviewList.add(review);
                }
            }
        }catch (SQLException ex){
            System.err.println("SQL exception (request or table failed): " + ex);
        }finally {
            connector.closePreparedStatement(preparedStatement);
        }
        return reviewList;
    }
    public List<Review> getTaxiReviews(User user) throws SQLException{
        List<Review> reviewList = new ArrayList<>();
        Review review = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connector.getReviewTaxiPreparedStatement();
            preparedStatement.setInt(1,user.getId());
            if((resultSet = preparedStatement.executeQuery()) != null){
                while (resultSet.next()){
                    review = new Review();
                    Integer clientId = resultSet.getInt(1);
                    String clientName = resultSet.getString(2);
                    String clientSurname = resultSet.getString(3);
                    review.setClient(new Client(clientId, clientName,clientSurname));
                    String comment = resultSet.getString(4);
                    review.setComment(comment);
                    reviewList.add(review);
                }
            }
        }catch (SQLException ex){
            System.err.println("SQL exception (request or table failed): " + ex);
        }finally {
            connector.closePreparedStatement(preparedStatement);
        }
        return reviewList;
    }

}
