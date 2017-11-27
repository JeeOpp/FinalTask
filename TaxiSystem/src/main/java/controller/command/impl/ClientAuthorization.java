package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Client;
import service.AuthorizationService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by DNAPC on 16.11.2017.
 */
public class ClientAuthorization implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AuthorizationService authorizationService = serviceFactory.getAuthorizationService();

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Client client = new Client(login,password);
        try{
            if ((client = authorizationService.authorize(client))==null){
                req.getRequestDispatcher("WEB-INF/AuthorizationProblem.jsp").forward(req,resp);
            }else {
                if(client.hasBanStatus()){
                    req.getRequestDispatcher("WEB-INF/Banned.jsp").forward(req, resp);
                } else {
                    //////////////////////// TODO refactor it
                    if(client.getLogin().equals("root")){
                        req.getRequestDispatcher("WEB-INF/Admin/AdminMain.jsp").forward(req, resp);
                    }else {
                        req.getRequestDispatcher("WEB-INF/User/UserMain.jsp").forward(req, resp);
                    }
                    ////////////////////////
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

   /*private void choosePageAuthentication(HttpServletRequest req, HttpServletResponse resp, String role) throws ServletException, IOException{
        if (role.equals("user")) {
            req.getRequestDispatcher("WEB-INF/User/UserMain.jsp").forward(req, resp);
        }
        if (role.equals("taxi")) {
            req.getRequestDispatcher("WEB-INF/Taxi/TaxiMain.jsp").forward(req, resp);
        }
        if (role.equals("admin")) {
            req.getRequestDispatcher("WEB-INF/Admin/AdminMain.jsp").forward(req, resp);
        }
    }*/
}
