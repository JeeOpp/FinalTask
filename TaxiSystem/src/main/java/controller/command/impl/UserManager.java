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

import static support.constants.OrderConstants.CHECKED_CAR;
import static support.constants.PaginationConstants.*;
import static support.constants.ReviewConstants.USER_REVIEWS;
import static support.constants.UserConstants.*;

/**
 * Contains all actions related on actions with users.
 * Delegates business logic to the {@link UserManagerService}
 * The class implements {@link ControllerCommand}.
 */
public class UserManager implements ControllerCommand {
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";
    private final static String CLIENT_PROFILE_PAGE = "WEB-INF/Client/profile.jsp";
    private final static String TAXI_PROFILE_PAGE = "WEB-INF/Taxi/profile.jsp";
    private final static String GET_USER_REVIEW_REQ = "Controller?method=userManager&action=preProfile";
    private final static String ADMIN_CLIENTS_PAGE = "WEB-INF/Admin/clients.jsp";
    private final static String ADMIN_TAXI_PAGE = "WEB-INF/Admin/taxi.jsp";
    private final static String INDEX_PAGE = "index.jsp";
    private final static int WRONG_ID = -1;
    private final static int DEFAULT_BONUS = 0;
    private final static int DEFAULT_PAGE = 1;
    private final static String REQ_CLIENT_LIST = "Controller?method=userManager&action=getClientList";
    private final static String REQ_TAXI_LIST = "Controller?method=userManager&action=getTaxiList";
    private final static String MAIL = "mail";
    private final static String HASH_PASS = "hashPassword";
    private final static String LOCAL = "local";
    private final static String BUNDLE = "localization.local";
    private final static String SUBJECT = "local.restoreEmail.subject";
    private final static String PASS_TEXT = "local.restoreEmail.newPassText";

    /**
     * Realization of command pattern. Read a action parameter from request and execute special command depending on read parameter.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ACTION);
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
                restorePassword(req, resp);
                break;
            case NONE:
                resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * If method was called by client read all this user's reviews from database.
     * If method was called by taxi read all review about taxi.
     * Finally redirect user on user's profile page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void getUserReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        String role = user.getRole();
        if (role.equals(CLIENT) || role.equals(TAXI)) {
            List<Review> reviewList;
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            FeedbackService feedbackService = serviceFactory.getFeedbackService();
            reviewList = feedbackService.getUserReviews(user);

            req.setAttribute(USER_REVIEWS, reviewList);
            if (role.equals(CLIENT)) {
                req.getRequestDispatcher(CLIENT_PROFILE_PAGE).forward(req, resp);
            } else {
                req.getRequestDispatcher(TAXI_PROFILE_PAGE).forward(req, resp);
            }
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * Uses to change user's password. If it success redirect user to the user's profile page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void changePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        String role = user.getRole();
        if (role.equals(CLIENT) || role.equals(TAXI)) {
            String currentPassword = req.getParameter(PREVIOUS_PASS);
            String newPassword = req.getParameter(NEW_PASS);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            if (userManagerService.changePassword(user, currentPassword, newPassword)) {
                req.getSession().setAttribute(USER, user);
            }
            resp.sendRedirect(GET_USER_REVIEW_REQ);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }

    }

    /**
     * Get all the information about client from database. Build a client's list and sent it on the admin's client page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void getClientList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(ADMIN)) {
            String numPage = req.getParameter(NUM_PAGE);
            int page = (numPage != null) ? Integer.parseInt(numPage) : DEFAULT_PAGE;
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            List<Client> clientList = userManagerService.getClientList();


            PaginationService<Client> paginationService = serviceFactory.getPaginationService();
            paginationService.buildPagination(clientList);
            List<Client> pageClientList = paginationService.getPagination().getPage(page);


            req.setAttribute(PAGE_CLIENT_LIST, pageClientList);
            req.setAttribute(PAGE_COUNT, paginationService.getPagination().getCountPages());
            req.setAttribute(CURRENT_PAGE, page);

            req.getRequestDispatcher(ADMIN_CLIENTS_PAGE).forward(req, resp);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * Get all information about taxi from database. Build a taxi's list and sent it on the admin's taxi page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void getTaxiList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(ADMIN)) {
            String numPage = req.getParameter(NUM_PAGE);
            int page = (numPage != null) ? Integer.parseInt(numPage) : DEFAULT_PAGE;

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            List<Taxi> taxiList = userManagerService.getTaxiList();


            PaginationService<Taxi> paginationService = serviceFactory.getPaginationService();
            paginationService.buildPagination(taxiList);
            List<Taxi> pageTaxiList = paginationService.getPagination().getPage(page);


            req.setAttribute(PAGE_TAXI_LIST, pageTaxiList);
            req.setAttribute(PAGE_COUNT, paginationService.getPagination().getCountPages());
            req.setAttribute(CURRENT_PAGE, page);

            req.getRequestDispatcher(ADMIN_TAXI_PAGE).forward(req, resp);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * change a user's ban status from database. If changed user's role is client redirect to the admin's client page,
     * if user's role is taxi redirect to admin's taxi page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void changeBanStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(ADMIN)) {
            String userIdString = req.getParameter(ID);
            int userId = (userIdString != null) ? Integer.parseInt(userIdString) : WRONG_ID;
            boolean banStatus = Boolean.parseBoolean(req.getParameter(BAN_STATUS));
            String rolePage = req.getParameter(ROLE);

            User userToChange = new User.UserBuilder().
                    setId(userId).
                    setBanStatus(banStatus).
                    setRole(rolePage).build();
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            userManagerService.changeBanStatus(userToChange);
            if (rolePage.equals(CLIENT)) {
                resp.sendRedirect(REQ_CLIENT_LIST);
            } else {
                resp.sendRedirect(REQ_TAXI_LIST);
            }
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * change a user's bonus points from database.
     * Finally redirect to the admin's client page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void changeBonusCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(ADMIN)) {
            String clientIdString = req.getParameter(CLIENT_ID);
            int clientId = (clientIdString != null) ? Integer.parseInt(clientIdString) : WRONG_ID;
            String bonusPointsString = req.getParameter(BONUS_POINTS);
            int bonusPoints = (bonusPointsString != null) ? Integer.parseInt(bonusPointsString) : DEFAULT_BONUS;

            Client client = (Client) new Client.ClientBuilder().
                    setBonusPoints(bonusPoints).
                    setId(clientId).build();
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            userManagerService.changeBonusCount(client);
            resp.sendRedirect(REQ_CLIENT_LIST);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * change a taxi's car from database and set new car to taxi.
     * Finally redirect to the admin's taxi page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void changeTaxiCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.getRole().equals(ADMIN)) {
            String taxiIdString = req.getParameter(CHANGE_TAXI_ID);
            int taxiId = (taxiIdString != null) ? Integer.parseInt(taxiIdString) : WRONG_ID;
            String carNumber = req.getParameter(CHECKED_CAR);
            Car car = new Car(carNumber);
            Taxi taxi = (Taxi) new Taxi.TaxiBuilder().
                    setCar(car).
                    setId(taxiId).build();
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserManagerService userManagerService = serviceFactory.getUserManagerService();
            userManagerService.changeTaxiCar(taxi);
            resp.sendRedirect(REQ_TAXI_LIST);
        } else {
            resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * Checks the request's parameters.
     * form an electronic message if the parameters are correct and sends it to the user with e-mail.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void restorePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mail = req.getParameter(MAIL);
        String hashPassword = req.getParameter(HASH_PASS);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserManagerService userManagerService = serviceFactory.getUserManagerService();
        String newPassword = PasswordGen.generate();
        if (userManagerService.restorePassword(mail, hashPassword, newPassword)) {
            String locale = (String) req.getSession().getAttribute(LOCAL);
            ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, new Locale(locale));
            String restoreSubject = resourceBundle.getString(SUBJECT);
            String newPassTest = resourceBundle.getString(PASS_TEXT) + "\n" + newPassword;

            Thread sendMail = new Thread(new TLSSender(mail, newPassTest, restoreSubject));
            sendMail.start();
            resp.sendRedirect(INDEX_PAGE);
        } else {
            resp.sendRedirect(INDEX_PAGE);
        }
    }

    private enum UserManagerAction {
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

        UserManagerAction(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        /**
         * In the dependence on the received value, return special enum.
         *
         * @param action Special enum we are want to get.
         * @return get special enum in accordance with the action value.
         * If there are no matches return a NONE enum.
         */
        public static UserManagerAction getConstant(String action) {
            for (UserManagerAction each : UserManagerAction.values()) {
                if (each.getValue().equals(action)) {
                    return each;
                }
            }
            return NONE;
        }
    }
}

