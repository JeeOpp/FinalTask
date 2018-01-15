//dispatcherDAO метод setResultSet



package controller;

import controller.command.ControllerCommand;
import controller.command.ControllerDirector;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
        resp.setContentType("text/html");

        String method = req.getParameter("method");

        ControllerDirector controllerDirector = new ControllerDirector();
        ControllerCommand controllerCommand = controllerDirector.getCommand(method);
        controllerCommand.execute(req,resp);
    }
}
