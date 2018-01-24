package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Car;
import entity.Client;
import entity.Taxi;
import entity.User;
import entity.entityEnum.OrderEnum;
import entity.entityEnum.UserEnum;
import org.apache.log4j.Logger;
import service.ServiceFactory;
import service.SignService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Thread.sleep;

/**
 * Responds to requests related to sign action.
 * The class implements {@link ControllerCommand}.
 */
public class SignManager implements ControllerCommand {
    private static final Logger log = Logger.getLogger(SignManager.class.getClass());
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";
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
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
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
        }
    }
    /**
     * In the case of user's role, redirect on user's home page.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    private void goHomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(UserEnum.USER.getValue());
        req.getRequestDispatcher(chooseUserPage(user.getRole())).forward(req, resp);
    }

    /**
     * Receives login and password from the request.
     * If the data is correct redirect it on the page accessible to the user.
     * If the data is incorrect then redirects it to the appropriate page
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    private void authorize(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user;
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SignService signService = serviceFactory.getSignService();

        String login = req.getParameter(UserEnum.LOGIN.getValue());
        String password = req.getParameter(UserEnum.PASSWORD.getValue());

        try {
            sleep(2000);
        }catch (InterruptedException ex){}

        try {
            if ((user = signService.authorize(login, password)) == null) {
                resp.sendRedirect(AUTHORIZATION_PROBLEM);
            } else {
                if (user.getBanStatus()) {
                    resp.sendRedirect(BANNED_PAGE);
                } else {
                    if (user.getRole().equals(UserEnum.TAXI.getValue())) {
                        signService.changeSessionStatus((Taxi) user);
                        ((Taxi) user).setAvailableStatus(true);
                    }
                    req.getSession().setAttribute(UserEnum.USER.getValue(), user);
                    String pageAuthentication = chooseUserPage(user.getRole());
                    req.getRequestDispatcher(pageAuthentication).forward(req, resp);
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Receives all needed parameters to registration.
     * If the data is correct registers user to the database.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SignService signService = serviceFactory.getSignService();

        String login = req.getParameter(UserEnum.LOGIN.getValue());
        String password = req.getParameter(UserEnum.PASSWORD.getValue());
        String firstName = req.getParameter(UserEnum.NAME.getValue());
        String lastName = req.getParameter(UserEnum.SURNAME.getValue());
        String mail = req.getParameter(UserEnum.EMAIL.getValue()).toLowerCase();
        String carNumber = req.getParameter(OrderEnum.CHECKED_CAR.getValue());
        String role = req.getParameter(UserEnum.ROLE.getValue());
        try {
            if (role.equals(UserEnum.CLIENT.getValue())) {
                Client client = new Client(login, password, firstName, lastName, mail);
                if (signService.registerClient(client)) {
                    req.getRequestDispatcher(REGISTRATION_SUCCESS).forward(req, resp);
                } else {
                    req.getRequestDispatcher(REGISTRATION_PROBLEM).forward(req, resp);
                }
            }
            if (role.equals(UserEnum.TAXI.getValue())) {
                Car car = new Car(carNumber);
                Taxi taxi = new Taxi(login, password, firstName, lastName, role, car);
                if (signService.registerTaxi(taxi)) {
                    new UserManager().getTaxiList(req,resp);
                } else {
                    resp.sendRedirect(REDIRECT_HOME);
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }
    /**
     * Deletes a session and redirects to the main page.
     * If it was called by taxi changes a available taxi status.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    private void logOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(UserEnum.USER.getValue());

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SignService signService = serviceFactory.getSignService();
        try {
            if (user.getRole().equals(UserEnum.TAXI.getValue())) {
                signService.changeSessionStatus((Taxi) user);
            }
            session.invalidate();
            resp.sendRedirect(INDEX_PAGE);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * returns a user's main page path.
     * @param role user's role
     * @return a page path.
     */
    public String chooseUserPage(String role) {
        if (role.equals(UserEnum.CLIENT.getValue())) {
            return CLIENT_MAIN_PATH;
        }
        if (role.equals(UserEnum.TAXI.getValue())) {
            return TAXI_MAIN_PATH;
        }
        if (role.equals(UserEnum.ADMIN.getValue())) {
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
