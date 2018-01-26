package service.impl;

import dao.DAOFactory;
import dao.FeedbackDAO;
import entity.Review;
import entity.User;
import entity.entityEnum.UserEnum;
import org.apache.log4j.Logger;
import service.FeedbackService;

import java.sql.SQLException;
import java.util.List;

/**
 *  Delegates action with database to the {@link FeedbackDAO}
 */
public class FeedbackServiceImpl implements FeedbackService{
    private final static Logger log = Logger.getLogger(FeedbackServiceImpl.class.getClass());

    /**
     * Client gives a review.
     * Delegates review writing to the DAO layer
     *
     * @param review we are want to write
     */
    public void setReview(Review review){
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            FeedbackDAO feedbackDAO = daoFactory.getFeedbackDAO();
            feedbackDAO.setReview(review);
        }catch (SQLException ex){
            log.error(ex.getMessage());
        }
    }
    /**
     * Delegates selecting the concrete client's reviews to DAO layer and form their to list.
     *
     * @param user reviews we are want to select.
     */
    public List<Review> getUserReviews(User user){
        List<Review> reviewList = null;
        DAOFactory daoFactory = DAOFactory.getInstance();
        FeedbackDAO feedbackDAO = daoFactory.getFeedbackDAO();
        try {
            if (user.getRole().equals(UserEnum.CLIENT.getValue())) {
                reviewList = feedbackDAO.getClientReviews(user);
            } else {
                reviewList = feedbackDAO.getTaxiReviews(user);
            }
        }catch (SQLException ex){
            log.error(ex.getMessage());
        }
        return reviewList;
    }
}
