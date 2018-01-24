package controller;

import controller.command.ControllerCommand;
import controller.command.ControllerDirector;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The single controller, receives requests from the server.
 */
public class Controller extends HttpServlet{
    private static final String METHOD = "method";
    private static final String TEXT_CONTENT_TYPE = "text/html";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(TEXT_CONTENT_TYPE);

        String method = req.getParameter(METHOD);

        ControllerDirector controllerDirector = new ControllerDirector();
        ControllerCommand controllerCommand = controllerDirector.getCommand(method);
        controllerCommand.execute(req,resp);
    }
}
