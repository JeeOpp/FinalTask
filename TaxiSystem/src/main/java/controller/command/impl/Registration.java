package controller.command.impl;

import controller.command.ControllerCommand;
import service.RegistrationService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by DNAPC on 16.11.2017.
 */
public class Registration implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        RegistrationService registrationService = serviceFactory.getRegistrationService();

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            if(registrationService.register(login, password)){
                req.getRequestDispatcher("WEB-INF/RegistrationSuccess.jsp").forward(req,resp);
            }else {
                req.getRequestDispatcher("WEB-INF/RegistrationProblem.jsp").forward(req,resp);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
