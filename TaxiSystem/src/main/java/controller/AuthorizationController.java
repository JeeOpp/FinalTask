package controller;

import service.AuthorizationService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class AuthorizationController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        if(req.getParameter("method").equals("authentication")){
            authentication(req,resp);
        }else if(req.getParameter("method").equals("registration")){
            registration(req,resp);
        }
    }
    private void authentication(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AuthorizationService authorizationService = serviceFactory.getAuthorizationService();
        String role = authorizationService.authentication();
        if(role.equals("user")){
            req.getRequestDispatcher("WEB-INF/User/UserMain.jsp").forward(req,resp);
        }
        if(role.equals("taxi")){
            req.getRequestDispatcher("WEB-INF/Taxi/TaxiMain.jsp").forward(req,resp);
        }
        if (role.equals("admin")){
            req.getRequestDispatcher("WEB-INF/Admin/AdminMain.jsp").forward(req,resp);
        }
    }
    private void registration(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    }
}
