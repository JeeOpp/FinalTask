package controller;

import com.sun.xml.internal.bind.v2.TODO;
import service.AuthorizationService;
import service.MD5;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

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
        System.out.println(MD5.md5Hash("root"));
        System.out.println(MD5.md5Hash("taxi"));

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

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            Map<String,Boolean> resultMap;
            if ((resultMap = authorizationService.authenticate(login,password)).isEmpty()){
                req.getRequestDispatcher("WEB-INF/AuthenticationProblem.jsp").forward(req,resp);
            }else {
                Map.Entry<String, Boolean> entry = resultMap.entrySet().iterator().next();
                String role = entry.getKey();
                Boolean status = entry.getValue();

                if (status) {
                    req.getRequestDispatcher("WEB-INF/Banned.jsp").forward(req, resp);
                } else {
                    choosePageAuthentication(req, resp, role);
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    private void choosePageAuthentication(HttpServletRequest req, HttpServletResponse resp, String role) throws ServletException, IOException{
        if (role.equals("user")) {
            req.getRequestDispatcher("WEB-INF/User/UserMain.jsp").forward(req, resp);
        }
        if (role.equals("taxi")) {
            req.getRequestDispatcher("WEB-INF/Taxi/TaxiMain.jsp").forward(req, resp);
        }
        if (role.equals("admin")) {
            req.getRequestDispatcher("WEB-INF/Admin/AdminMain.jsp").forward(req, resp);
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
