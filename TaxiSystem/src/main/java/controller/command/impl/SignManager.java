package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Client;
import entity.User;
import service.AuthorizationService;
import service.RegistrationService;
import service.ServiceFactory;

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
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("authorization")) {
            authorize(req,resp);
        }
        if (action.equals("registration")){
            register(req,resp);
        }
        if(action.equals("logOut")){
            logOut(req,resp);
        }
    }

    private void authorize(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AuthorizationService authorizationService = serviceFactory.getAuthorizationService();

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user;
        try {
            if ((user = authorizationService.authorize(login,password)) == null) {
                resp.sendRedirect("authorizationProblem.jsp");
            } else {
                if (user.hasBanStatus()) {
                    resp.sendRedirect("banned.jsp");
                } else {
                    req.getSession().setAttribute("user",user);
                    choosePageAuthentication(req, resp, user.getRole());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        RegistrationService registrationService = serviceFactory.getRegistrationService();

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        /// String CAR!!!!!!!!!!
        String role = req.getParameter("role");
        try {
            if (role.equals("client")) {
                Client client = new Client(login,password,firstName,lastName);
                if(registrationService.registerClient(client)){
                    req.getRequestDispatcher("registrationSuccess.jsp").forward(req,resp);
                }else {
                    req.getRequestDispatcher("registrationProblem.jsp").forward(req,resp);
                }
            }
            if (role.equals("taxi")) {

            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    private void logOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect("index.jsp");
    }


    private void choosePageAuthentication(HttpServletRequest req, HttpServletResponse resp, String role) throws ServletException, IOException{
        if (role.equals("client")) {
            req.getRequestDispatcher("WEB-INF/Client/main.jsp").forward(req, resp);
        }
        if (role.equals("taxi")) {
            req.getRequestDispatcher("WEB-INF/Taxi/main.jsp").forward(req, resp);
        }
        if (role.equals("admin")) {
            req.getRequestDispatcher("WEB-INF/Admin/main.jsp").forward(req, resp);
        }
    }
}
