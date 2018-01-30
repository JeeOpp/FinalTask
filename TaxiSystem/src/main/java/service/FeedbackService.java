package service;

import entity.Review;
import entity.User;

import java.sql.SQLException;
import java.util.List;

public interface FeedbackService {
    /**
     * Client gives a review.
     * Delegates review writing to the DAO layer
     *
     * @param review we are want to write
     */
    void setReview(Review review);

    /**
     * Delegates selecting the concrete client's reviews to DAO layer and form their to list.
     *
     * @param user reviews we are want to select.
     * @return the list of reviews.
     */
    List<Review> getUserReviews(User user);

    /**
     * Delegates selecting all the reviews to DAO layer and form their to list.
     *
     * @return all review list.
     */

    List<Review> getAllReviewList();

    /**
     * Delete information about specific review from database.
     *
     * @param reviewId review we want to delete
     */
    boolean deleteReview(int reviewId);
}
