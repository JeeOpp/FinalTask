package controller;

import com.sun.xml.internal.bind.v2.TODO;
import service.AuthorizationService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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
        PrintWriter printWriter = resp.getWriter();
        if(req.getParameter("method").equals("authentication")){
            authentication(req,resp);
        }else if(req.getParameter("method").equals("registration")){
            if(registration(req,resp)){
                printWriter.println("ИМЯ");
            }else{
                printWriter.println("Problems");
            }
        }
    }
    private void authentication(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AuthorizationService authorizationService = serviceFactory.getAuthorizationService();
        String role;
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            role = authorizationService.authenticate(login,password);
            if (role.equals("user")) {
                req.getRequestDispatcher("WEB-INF/User/UserMain.jsp").forward(req, resp);
            }
            if (role.equals("taxi")) {
                req.getRequestDispatcher("WEB-INF/Taxi/TaxiMain.jsp").forward(req, resp);
            }
            if (role.equals("admin")) {
                req.getRequestDispatcher("WEB-INF/Admin/AdminMain.jsp").forward(req, resp);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    private Boolean registration(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AuthorizationService authorizationService = serviceFactory.getAuthorizationService();

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
           return authorizationService.register(login, password);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
}
