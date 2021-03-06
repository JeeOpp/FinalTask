package dao;

import entity.Review;
import entity.User;

import java.sql.SQLException;
import java.util.List;

public interface FeedbackDAO {
    String SQL_SET_REVIEW = "INSERT INTO taxisystem.review (client_id, taxi_id, comment) VALUES (?, ?, ?);";
    String SQL_GET_CLIENT_REVIEW = "SELECT taxi.id, taxi.name, taxi.surname, review.`comment` FROM review JOIN taxi ON review.taxi_id = taxi.id WHERE review.client_id = ?; ";
    String SQL_GET_TAXI_REVIEW = "SELECT client.id, client.name, client.surname, review.`comment` FROM review JOIN client ON review.client_id = client.id WHERE review.taxi_id = ?;";
    String SQL_SELECT_ALL_REVIEW = "SELECT * FROM taxisystem.review ORDER BY review_id DESC;";
    String SQL_DELETE_REVIEW = "DELETE FROM review WHERE review_id = ?;";

    /**
     * Write a review information to the database.
     *
     * @param review contains information about review there are client id, taxi id and review text.
     * @throws SQLException when there are problems with database connection.
     */
    void setReview(Review review) throws SQLException;

    /**
     * Read a reviews which the client give before and forms it to the list.
     *
     * @param user used to identify client.
     * @return a reviews formed into the list.
     * @throws SQLException when there are problems with database connection.
     */
    List<Review> getClientReviews(User user) throws SQLException;

    /**
     * Read a client's reviews which clients are send to the taxi driver before.
     *
     * @param user used to identify taxi driver.
     * @return a reviews formed into the list.
     * @throws SQLException when there are problems with database connection.
     */
    List<Review> getTaxiReviews(User user) throws SQLException;

    /**
     * Read all information about reviews from database and forms it into the collection list.
     *
     * @return a list contained all reviews information.
     * @throws SQLException when there are problems with database connection.
     */
    List<Review> getReviewList()throws SQLException;

    /**
     * Delete information about specific review from database.
     * @param reviewId review we want to delete
     *
     * @throws SQLException when there are problems with database connection.
     */
    boolean deleteReview(int reviewId) throws SQLException;
}
