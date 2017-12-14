package dao.impl;

import dao.WrappedConnector;
import entity.Review;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
