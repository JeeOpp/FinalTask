package controller;

import com.sun.xml.internal.bind.v2.TODO;
import controller.command.ControllerCommand;
import controller.command.ControllerDirector;
import service.AuthorizationService;
import service.MD5;
import service.RegistrationService;
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
public class Controller extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(MD5.md5Hash("root"));
        System.out.println(MD5.md5Hash("taxi"));
        resp.setContentType("text/html");

        String method = req.getParameter("method");

        ControllerDirector controllerDirector = new ControllerDirector();
        ControllerCommand controllerCommand = controllerDirector.getCommand(method);
        controllerCommand.execute(req,resp);
    }
}
