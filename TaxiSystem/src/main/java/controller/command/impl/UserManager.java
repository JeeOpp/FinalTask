package controller.command.impl;

import controller.command.ControllerCommand;
import controller.command.SwitchConstant;
import entity.*;
import service.*;
import support.PasswordGen;
import support.TLSSender;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by DNAPC on 14.12.2017.
 */
public class UserManager implements ControllerCommand {
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        SwitchConstant switchConstant = SwitchConstant.getConstant(action);
        switch (switchConstant) {
            case PRE_PROFILE:
                getUserReview(req, resp);
                break;
            case CHANGE_PASSWORD:
                changePassword(req, resp);
                break;
            case GET_CLIENT_LIST:
                getClientList(req, resp);
                break;
            case GET_TAXI_LIST:
                getTaxiList(req, resp);
                break;
            case CHANGE_BAN_STATUS:
                changeBanStatus(req, resp);
                break;
            case CHANGE_BONUS_COUNT:
                changeBonusCount(req, resp);
                break;
            case CHANGE_TAXI_CAR:
                changeTaxiCar(req, resp);
                break;
            case RESTORE_PASSWORD:
                restorePassword(req,resp);
                break;
        }
    }

    private void getUserReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user = (User) req.getSession().getAttribute("user");
        String role = user.getRole();
        if (role.equals("client") || role.equals("taxi")) {
            List<Review> reviewList;
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            FeedbackService feedbackService = serviceFactory.getFeedbackService();
            reviewList = feedbackService.getUserReviews(user);

            req.setAttribute("userReviews", reviewList);
            if (role.equals("client")) {
                req.getRequestDispatcher("WEB-INF/Client/profile.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("WEB-INF/Taxi/profile.jsp").forward(req, resp);
            }
        }else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }
    private void changePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user = (User) req.getSession().getAttribute("user");
        String role = user.getRole();
        if (role.equals("client") || role.equals("taxi")) {
            String currentPassword = req.getParameter("previousPass");
            String newPassword = req.getParameter("newPass");

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            if (userManagerService.changePassword(user, currentPassword, newPassword)) {
                req.getSession().setAttribute("user", user);
            }
            getUserReview(req, resp);
        }else {
            resp.sendRedirect(REDIRECT_HOME);
        }

    }
    private void getClientList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole().equals("admin")){
            String numPage = req.getParameter("numPage");
            int page = (numPage != null) ? Integer.parseInt(numPage) : 1;
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            List<Client> clientList = userManagerService.getClientList();


            PaginationService<Client> paginationService = serviceFactory.getPaginationService();
            paginationService.buildPagination(clientList);
            List<Client> pageClientList = paginationService.getPagination().getPage(page);


            req.setAttribute("pageClientList", pageClientList);
            req.setAttribute("countPages", paginationService.getPagination().getCountPages());
            req.setAttribute("currentPage", page);

            req.getRequestDispatcher("WEB-INF/Admin/clients.jsp").forward(req, resp);
        }else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }
    private void getTaxiList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole().equals("admin")) {
            String numPage = req.getParameter("numPage");
            int page = (numPage != null) ? Integer.parseInt(numPage) : 1;
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            List<Taxi> taxiList = userManagerService.getTaxiList();


            PaginationService<Taxi> paginationService = serviceFactory.getPaginationService();
            paginationService.buildPagination(taxiList);
            List<Taxi> pageTaxiList = paginationService.getPagination().getPage(page);


            req.setAttribute("pageTaxiList", pageTaxiList);
            req.setAttribute("countPages", paginationService.getPagination().getCountPages());
            req.setAttribute("currentPage", page);

            req.getRequestDispatcher("WEB-INF/Admin/taxi.jsp").forward(req, resp);
        }else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }
    private void changeBanStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole().equals("admin")) {
            String userIdString = req.getParameter("id");
            int userId = (userIdString!=null)? Integer.parseInt(userIdString) : -1;
            boolean banStatus = Boolean.parseBoolean(req.getParameter("banStatus"));
            String rolePage = req.getParameter("role");

            User userToChange = new User(userId, banStatus, rolePage);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            userManagerService.changeBanStatus(userToChange);
            if (rolePage.equals("client")) {
                getClientList(req, resp);
            } else {
                getTaxiList(req, resp);
            }
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }
    private void changeBonusCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole().equals("admin")) {
            String clientIdString = req.getParameter("clientId");
            int clientId = (clientIdString!=null)? Integer.parseInt(clientIdString) : -1;
            String bonusPointsString = req.getParameter("bonusPoints");
            int bonusPoints = (bonusPointsString!=null)? Integer.parseInt(bonusPointsString) : 0;

            Client client = new Client(clientId, bonusPoints);
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            userManagerService.changeBonusCount(client);
            getClientList(req, resp);
        }else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }
    private void changeTaxiCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole().equals("admin")) {
            String taxiIdString = req.getParameter("changeTaxiId");
            int taxiId = (taxiIdString != null)? Integer.parseInt(taxiIdString):-1;
            String carNumber = req.getParameter("checkedCarNumber");
            Car car = new Car(carNumber);
            Taxi taxi = new Taxi(taxiId, car);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            userManagerService.changeTaxiCar(taxi);
            getTaxiList(req, resp);
        }else{
            resp.sendRedirect(REDIRECT_HOME);
        }
    }
    private void restorePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String mail = req.getParameter("mail");
        String hashPassword = req.getParameter("hashPassword");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserManagerService userManagerService = serviceFactory.getUserManagerService();
        String newPassword = PasswordGen.generate();
        if(userManagerService.restorePassword(mail, hashPassword, newPassword)){
            String locale = (String) req.getSession().getAttribute("local");
            ResourceBundle resourceBundle = ResourceBundle.getBundle("localization.local",new Locale(locale));
            String restoreSubject = resourceBundle.getString("local.restoreEmail.subject");
            String newPassTest = resourceBundle.getString("local.restoreEmail.newPassText")+"\n"+newPassword;

            Thread sendMail = new Thread(new TLSSender(mail,newPassTest,restoreSubject));
            sendMail.start();
            resp.sendRedirect("index.jsp");
        }else{
            resp.sendRedirect("index.jsp");
        }
    }
}

