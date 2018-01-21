package controller.command.impl;

import controller.command.ControllerCommand;
import controller.command.SwitchConstant;
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
import java.sql.SQLException;

/**
 * Created by DNAPC on 16.12.2017.
 */
public class SignManager implements ControllerCommand {
    private static final Logger log = Logger.getLogger(SignManager.class.getClass());
    private static final String ADMIN_MAIN_PATH = "WEB-INF/Admin/main.jsp";
    private static final String CLIENT_MAIN_PATH = "WEB-INF/Client/main.jsp";
    private static final String TAXI_MAIN_PATH = "WEB-INF/Taxi/main.jsp";
    private static final String AUTHORIZATION_PROBLEM = "authorizationProblem.jsp";
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        SwitchConstant switchConstant = SwitchConstant.getConstant(action);
        switch (switchConstant) {
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

    private void goHomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user = (User) req.getSession().getAttribute("user");
        req.getRequestDispatcher(chooseUserPage(user.getRole())).forward(req,resp);
    }
    private void authorize(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user;
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SignService signService = serviceFactory.getSignService();

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            if ((user = signService.authorize(login,password)) == null) {
                resp.sendRedirect("authorizationProblem.jsp");
            } else {
                if (user.getBanStatus()) {
                    resp.sendRedirect("banned.jsp");
                } else {
                    if(user.getRole().equals("taxi")){
                        signService.changeSessionStatus((Taxi) user);
                        ((Taxi) user).setAvailableStatus(true);
                    }
                    req.getSession().setAttribute("user",user);
                    String pageAuthentication =  chooseUserPage(user.getRole());
                    req.getRequestDispatcher(pageAuthentication).forward(req,resp);
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }
    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SignService signService = serviceFactory.getSignService();

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("name");
        String lastName = req.getParameter("surname");
        String mail = req.getParameter("email").toLowerCase();
        String carNumber = req.getParameter("checkedCarNumber");
        String role = req.getParameter("role");
        try {
            if (role.equals("client")) {
                Client client = new Client(login,password,firstName,lastName,mail);
                if(signService.registerClient(client)){
                    req.getRequestDispatcher("registrationSuccess.jsp").forward(req,resp);
                }else {
                    req.getRequestDispatcher("registrationProblem.jsp").forward(req,resp);
                }
            }
            if (role.equals("taxi")) {
                Car car = new Car(carNumber);
                Taxi taxi = new Taxi(login,password,firstName,lastName,role,car);
                if(signService.registerTaxi(taxi)){
                    req.getRequestDispatcher("Controller?method=userManager&action=getTaxiList").forward(req,resp);
                }else {
                    req.getRequestDispatcher("Controller?method=userManager&action=getTaxiList").forward(req,resp);
                }
            }
        }catch (SQLException ex){
            log.error(ex.getMessage());
        }
    }
    private void logOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SignService signService = serviceFactory.getSignService();
        try {
            if(user.getRole().equals("taxi")) {
                signService.changeSessionStatus((Taxi)user);
            }
            session.invalidate();
            resp.sendRedirect("index.jsp");
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }
    public String chooseUserPage(String role){
        if (role.equals("client")) {
            return CLIENT_MAIN_PATH;
        }
        if (role.equals("taxi")) {
            return TAXI_MAIN_PATH;
        }
        if (role.equals("admin")) {
            return ADMIN_MAIN_PATH;
        }
        return AUTHORIZATION_PROBLEM;
    }
}
