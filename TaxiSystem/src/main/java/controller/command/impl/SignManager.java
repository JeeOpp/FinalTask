package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Car;
import entity.Client;
import entity.Taxi;
import entity.User;
import org.apache.log4j.Logger;
import service.ServiceFactory;
import service.SignService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static support.constants.OrderConstants.CHECKED_CAR;
import static support.constants.UserConstants.*;

/**
 * Responds to requests related to sign action.
 * Delegates business logic to the {@link SignService}
 * The class implements {@link ControllerCommand}.
 */
public class SignManager implements ControllerCommand {
    private static final Logger log = Logger.getLogger(SignManager.class.getClass());
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";
    private final static String REQ_TAXI_LIST = "Controller?method=userManager&action=getTaxiList";
    private static final String ADMIN_MAIN_PATH = "WEB-INF/Admin/main.jsp";
    private static final String CLIENT_MAIN_PATH = "WEB-INF/Client/main.jsp";
    private static final String TAXI_MAIN_PATH = "WEB-INF/Taxi/main.jsp";
    private static final String AUTHORIZATION_PROBLEM = "authorizationProblem.jsp";
    private static final String REGISTRATION_PROBLEM = "registrationProblem.jsp";
    private static final String REGISTRATION_SUCCESS = "registrationSuccess.jsp";
    private static final String BANNED_PAGE = "banned.jsp";
    private static final String INDEX_PAGE = "index.jsp";


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
        SignManagerAction signManagerAction = SignManagerAction.getConstant(action);
        switch (signManagerAction) {
            case AUTHORIZATION:
                authorize(req, resp);
                break;
            case REGISTRATION:
                register(req, resp);
                break;
            case LOG_OUT:
                logOut(req, resp);
                break;
            case GO_HOME_PAGE:
                goHomePage(req, resp);
                break;
            case NONE:
                resp.sendRedirect(REDIRECT_HOME);
        }
    }

    /**
     * In the case of user's role, redirect on user's home page.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void goHomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        req.getRequestDispatcher(chooseUserPage(user.getRole())).forward(req, resp);
    }

    /**
     * Receives login and password from the request.
     * If the data is correct redirect it on the page accessible to the user.
     * If the data is incorrect then redirects it to the appropriate page
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void authorize(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user;
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SignService signService = serviceFactory.getSignService();

        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);

        if ((user = signService.authorize(login, password)) == null) {
            resp.sendRedirect(AUTHORIZATION_PROBLEM);
        } else {
            if (user.getBanStatus()) {
                resp.sendRedirect(BANNED_PAGE);
            } else {
                if (user.getRole().equals(TAXI)) {
                    signService.changeSessionStatus((Taxi) user);
                    ((Taxi) user).setAvailableStatus(true);
                }
                req.getSession().setAttribute(USER, user);
                resp.sendRedirect(REDIRECT_HOME);
            }
        }
    }

    /**
     * Receives all needed parameters to registration.
     * If the data is correct registers user to the database.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SignService signService = serviceFactory.getSignService();

        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String firstName = req.getParameter(NAME);
        String lastName = req.getParameter(SURNAME);
        String mail = req.getParameter(EMAIL);
        String carNumber = req.getParameter(CHECKED_CAR);
        String role = req.getParameter(ROLE);
        if (role.equals(CLIENT)) {
            Client client = (Client) new Client.ClientBuilder().
                    setMail(mail).
                    setLogin(login).
                    setPassword(password).
                    setFirstName(firstName).
                    setLastName(lastName).build();
            if (signService.registerClient(client)) {
                resp.sendRedirect(REGISTRATION_SUCCESS);
            } else {
                resp.sendRedirect(REGISTRATION_PROBLEM);
            }
        }
        if (role.equals(TAXI)) {
            Car car = new Car(carNumber);
            Taxi taxi = (Taxi) new Taxi.TaxiBuilder().
                    setCar(car).
                    setLogin(login).
                    setPassword(password).
                    setFirstName(firstName).
                    setLastName(lastName).build();
            if (signService.registerTaxi(taxi)) {
                resp.sendRedirect(REQ_TAXI_LIST);
            } else {
                resp.sendRedirect(REDIRECT_HOME);
            }
        }
    }

    /**
     * Deletes a session and redirects to the main page.
     * If it was called by taxi changes a available taxi status.
     *
     * @param req  Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException      Standard exception
     */
    private void logOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SignService signService = serviceFactory.getSignService();
        if (user.getRole().equals(TAXI)) {
            signService.changeSessionStatus((Taxi) user);
        }
        session.invalidate();
        resp.sendRedirect(INDEX_PAGE);
    }

    /**
     * returns a user's main page path.
     *
     * @param role user's role
     * @return a page path.
     */
    public String chooseUserPage(String role) {
        if (role.equals(CLIENT)) {
            return CLIENT_MAIN_PATH;
        }
        if (role.equals(TAXI)) {
            return TAXI_MAIN_PATH;
        }
        if (role.equals(ADMIN)) {
            return ADMIN_MAIN_PATH;
        }
        return AUTHORIZATION_PROBLEM;
    }

    private enum SignManagerAction {
        AUTHORIZATION("authorization"),
        REGISTRATION("registration"),
        LOG_OUT("logOut"),
        GO_HOME_PAGE("goHomePage"),

        NONE("none");
        private String value;

        SignManagerAction(String value) {
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
        public static SignManagerAction getConstant(String action) {
            for (SignManagerAction each : SignManagerAction.values()) {
                if (each.getValue().equals(action)) {
                    return each;
                }
            }
            return NONE;
        }
    }
}