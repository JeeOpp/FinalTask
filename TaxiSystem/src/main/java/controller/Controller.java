//dispatcherDAO метод setResultSet



package controller;

import controller.command.ControllerCommand;
import controller.command.ControllerDirector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import java.io.IOException;

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
        //System.out.println(MD5.md5Hash("root"));
        //System.out.println(MD5.md5Hash("taxi"));
        resp.setContentType("text/html");

        String method = req.getParameter("method");

        ControllerDirector controllerDirector = new ControllerDirector();
        ControllerCommand controllerCommand = controllerDirector.getCommand(method);
        controllerCommand.execute(req,resp);
    }
}
