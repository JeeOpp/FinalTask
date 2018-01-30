package dao.impl;

import dao.DAOFactory;
import dao.DispatcherDAO;
import dao.FeedbackDAO;
import entity.Review;
import entity.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DNAPC on 31.01.2018.
 */
public class FeedbackDAOImplTest {
    private static final DAOFactory daoFactory = DAOFactory.getInstance();
    private static final FeedbackDAO feedbackDAO = daoFactory.getFeedbackDAO();

    public int getRandomInt(){
        return (int) (Math.random()*10);
    }

    @Test
    public void getClientReviews() throws Exception {
        boolean actual = true;
        int randomId = getRandomInt();
        User user = new User.UserBuilder().setId(randomId).build();
        List<Review> reviewList = feedbackDAO.getClientReviews(user);
        if(!reviewList.isEmpty()){
            for(Review review: reviewList){
                if(review.getTaxi()==null){
                    actual = false;
                }
            }
        }
        assertTrue(actual);
    }

    @Test
    public void getTaxiReviews() throws Exception {
        boolean actual = true;
        int randomId = getRandomInt();
        User user = new User.UserBuilder().setId(randomId).build();
        List<Review> reviewList = feedbackDAO.getTaxiReviews(user);
        if(!reviewList.isEmpty()){
            for(Review review: reviewList){
                if(review.getTaxi()==null){
                    actual = false;
                }
            }
        }
        assertTrue(actual);
    }

    @Test
    public void getReviewList() throws Exception {
        List<Review> reviewList = feedbackDAO.getReviewList();
        assertTrue(reviewList!=null);
    }

}