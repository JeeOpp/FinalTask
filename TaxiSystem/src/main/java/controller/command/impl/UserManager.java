package controller.command.impl;

import controller.command.ControllerCommand;
import entity.*;
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
        if (action.equals("changeBanStatus")){
            changeBanStatus(req,resp);
        }
        if (action.equals("changeBonusCount")){
            changeBonusCount(req,resp);
        }
        if(action.equals("changeTaxiCar")){
            changeTaxiCar(req,resp);
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
    void getTaxiList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
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
    private void changeBanStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int userId = Integer.parseInt(req.getParameter("id"));
        boolean banStatus = Boolean.parseBoolean(req.getParameter("banStatus"));
        String role = req.getParameter("role");

        User user = new User(userId,banStatus,role);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserManagerService userManagerService = serviceFactory.getUserManagerService();
        userManagerService.changeBanStatus(user);
        if (role.equals("client")){
            getClientList(req,resp);
        }else {
            getTaxiList(req,resp);
        }
    }
    private void changeBonusCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
            Integer clientId = Integer.parseInt(req.getParameter("clientId"));
            Integer bonusPoints = Integer.parseInt(req.getParameter("bonusPoints"));

            Client client = new Client(clientId,bonusPoints);
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            userManagerService.changeBonusCount(client);
            getClientList(req,resp);
    }
    private void changeTaxiCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int taxiId = Integer.parseInt(req.getParameter("changeTaxiId"));
        String carNumber = req.getParameter("checkedCarNumber");
        Car car = new Car(carNumber);
        Taxi taxi = new Taxi(taxiId,car);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserManagerService userManagerService = serviceFactory.getUserManagerService();
        userManagerService.changeTaxiCar(taxi);
        getTaxiList(req,resp);
    }
}
