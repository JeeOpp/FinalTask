package controller.command.impl;

import controller.command.ControllerCommand;
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
 * Contains all actions related on actions with users.
 */
public class UserManager implements ControllerCommand {
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";
    /**
     * Realization of command pattern. Read a action parameter from request and execute special command depending on read parameter.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        UserManagerAction userManagerAction = UserManagerAction.getConstant(action);
        switch (userManagerAction) {
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
    /**
     * If method was called by client read all this user's reviews from database.
     * If method was called by taxi read all review about taxi.
     * Finally redirect user on user's profile page.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
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
    /**
     * Uses to change user's password. If it success redirect user to the user's profile page.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
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
    /**
     * Get all the information about client from database. Build a client's list and sent it on the admin's client page.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
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
    /**
     * Get all information about taxi from database. Build a taxi's list and sent it on the admin's taxi page.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    void getTaxiList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
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
    /**
     * change a user's ban status from database. If changed user's role is client redirect to the admin's client page,
     * if user's role is taxi redirect to admin's taxi page.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
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
    /**
     * change a user's bonus points from database.
     * Finally redirect to the admin's client page.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
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
    /**
     * change a taxi's car from database and set new car to taxi.
     * Finally redirect to the admin's taxi page.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
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
    /**
     * Checks the request's parameters.
     * form an electronic message if the parameters are correct and sends it to the user with e-mail.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
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
    private enum UserManagerAction{
        PRE_PROFILE("preProfile"),
        CHANGE_PASSWORD("changePassword"),
        GET_CLIENT_LIST("getClientList"),
        GET_TAXI_LIST("getTaxiList"),
        CHANGE_BAN_STATUS("changeBanStatus"),
        CHANGE_BONUS_COUNT("changeBonusCount"),
        CHANGE_TAXI_CAR("changeTaxiCar"),
        RESTORE_PASSWORD("restorePassword"),

        NONE("none");
        private String value;

        UserManagerAction(String value){
            this.value = value;
        }
        public String getValue(){
            return value;
        }
        /**
         * In the dependence on the received value, return special enum.
         * @param action Special enum we are want to get.
         * @return get special enum in accordance with the action value.
         * If there are no matches return a NONE enum.
         */
        public static UserManagerAction getConstant(String action){
            for (UserManagerAction each: UserManagerAction.values()){
                if(each.getValue().equals(action)){
                    return each;
                }
            }
            return NONE;
        }
    }
}

