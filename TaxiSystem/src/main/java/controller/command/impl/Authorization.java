package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Client;
import entity.Taxi;
import entity.User;
import service.AuthorizationService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by DNAPC on 16.11.2017.
 */
public class Authorization implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

   private void choosePageAuthentication(HttpServletRequest req, HttpServletResponse resp, String role) throws ServletException, IOException{
        if (role.equals("client")) {
            req.getRequestDispatcher("WEB-INF/Client/main.jsp").forward(req, resp);
        }
        if (role.equals("taxi")) {
            req.getRequestDispatcher("WEB-INF/Taxi/taxiMain.jsp").forward(req, resp);
        }
        if (role.equals("admin")) {
            req.getRequestDispatcher("WEB-INF/Admin/adminMain.jsp").forward(req, resp);
        }
    }
}
