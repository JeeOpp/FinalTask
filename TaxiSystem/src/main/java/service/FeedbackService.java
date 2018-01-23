package service;

import dao.DAOFactory;
import dao.FeedbackDAO;
import entity.Review;
import entity.User;
import entity.entityEnum.UserEnum;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class FeedbackService {
    private final static Logger log = Logger.getLogger(FeedbackService.class.getClass());
    public void setReview(Review review){
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            FeedbackDAO feedbackDAO = daoFactory.getFeedbackDAO();
            feedbackDAO.setReview(review);
        }catch (SQLException ex){
            log.error(ex.getMessage());
        }
    }
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
