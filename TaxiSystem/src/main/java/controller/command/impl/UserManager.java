package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Client;
import entity.Review;
import entity.Taxi;
import entity.User;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by DNAPC on 14.12.2017.
 */
public class UserManager implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action.equals("preProfile")){
            getUserReview(req,resp);
        }
        if(action.equals("changePassword")){
            changePassword(req,resp);
        }
        if (action.equals("getClientList")){
            getClientList(req,resp);
        }
        if (action.equals("getTaxiList")){
            getTaxiList(req,resp);
        }
        if (action.equals("ban")){
            ban(req,resp);
        }
        if (action.equals("giveBonusPoints")){
            giveBonusPoints(req,resp);
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
        UserManagerService userManagerService = serviceFactory.getUserManagerService();
        if(userManagerService.changePassword(user, currentPassword, newPassword)){
            req.getSession().setAttribute("user",user);
            //success
        }else{
            //failed
        }
    }
    private void getClientList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int page = 1; //default page
        if(req.getParameter("page") != null){
            page = Integer.parseInt(req.getParameter("page"));
        }
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserManagerService userManagerService = serviceFactory.getUserManagerService();
        List<Client> clientList = userManagerService.getClientList();


        PaginationService<Client> paginationService = serviceFactory.getPaginationService();
        paginationService.buildPagination(clientList);
        List<Client> pageClientList = paginationService.getPagination().getPage(page);


        req.setAttribute("pageClientList", pageClientList);
        req.setAttribute("countPages", paginationService.getPagination().getCountPages());
        req.setAttribute("currentPage", page);

        req.getRequestDispatcher("WEB-INF/Admin/clients.jsp").forward(req,resp);
    }
    private void getTaxiList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int page = 1; //default page
        if(req.getParameter("page") != null){
            page = Integer.parseInt(req.getParameter("page"));
        }
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserManagerService userManagerService = serviceFactory.getUserManagerService();
        List<Taxi> taxiList = userManagerService.getTaxiList();


        PaginationService<Taxi> paginationService = serviceFactory.getPaginationService();
        paginationService.buildPagination(taxiList);
        List<Taxi> pageTaxiList = paginationService.getPagination().getPage(page);


        req.setAttribute("pageTaxiList", pageTaxiList);
        req.setAttribute("countPages", paginationService.getPagination().getCountPages());
        req.setAttribute("currentPage", page);

        req.getRequestDispatcher("WEB-INF/Admin/taxi.jsp").forward(req,resp);
    }
    private void ban(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    }
    private void giveBonusPoints(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    }
}
