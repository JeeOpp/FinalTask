package service;

import dao.DAOFactory;
import dao.impl.FeedbackDAO;
import entity.Review;

import java.sql.SQLException;

/**
 * Created by DNAPC on 12.12.2017.
 */
public class FeedbackService {
    public void setReview(Review review){
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            FeedbackDAO feedbackDAO = daoFactory.getFeedbackDAO();
            feedbackDAO.setReview(review);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
