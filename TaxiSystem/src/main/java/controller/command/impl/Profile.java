package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Review;
import entity.User;
import service.FeedbackService;
import service.ProfileService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by DNAPC on 14.12.2017.
 */
public class Profile implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action.equals("preProfile")){
            getUserReview(req,resp);
        }
        if(action.equals("changePassword")){
            changePassword(req,resp);
        }

    }
    private void getUserReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Review> reviewList = null;
        User user = (User) req.getSession().getAttribute("user");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FeedbackService feedbackService = serviceFactory.getFeedbackService();
        reviewList = feedbackService.getUserReviews(user);

        req.setAttribute("userReviews", reviewList);
        if(user.getRole().equals("client")) {
            req.getRequestDispatcher("WEB-INF/Client/profile.jsp").forward(req, resp);
        }else {
            req.getRequestDispatcher("WEB-INF/Taxi/profile.jsp").forward(req,resp);
        }
    }

    private void changePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user = (User) req.getSession().getAttribute("user");
        String currentPassword = req.getParameter("previousPass");
        String newPassword = req.getParameter("newPass");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ProfileService profileService = serviceFactory.getProfileService();
        if(profileService.changePassword(user, currentPassword, newPassword)){
            req.getSession().setAttribute("user",user);
            //success
        }else{
            //failed
        }
    }
}
