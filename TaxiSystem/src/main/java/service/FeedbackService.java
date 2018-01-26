package service;

import entity.Review;
import entity.User;
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
}
