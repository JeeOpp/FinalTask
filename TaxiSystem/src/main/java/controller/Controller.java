package controller;

import controller.command.ControllerCommand;
import controller.command.ControllerDirector;
import dao.connectionPool.ConnectionPool;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The single controller, receives requests from the server and sends the responses.
 */
public class Controller extends HttpServlet{
    private static final String METHOD = "method";
    private static final String TEXT_CONTENT_TYPE = "text/html";
    private static final String REDIRECT_INDEX = "index.jsp";
    private final static String REDIRECT_HOME = "Controller?method=signManager&action=goHomePage";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(TEXT_CONTENT_TYPE);

        String method = req.getParameter(METHOD);
        if(method!=null){
            ControllerDirector controllerDirector = new ControllerDirector();
            ControllerCommand controllerCommand = controllerDirector.getCommand(method);
            if(controllerCommand!=null) {
                controllerCommand.execute(req, resp);
            }else {
                resp.sendRedirect(REDIRECT_HOME); //or 404
            }
        }else {
            resp.sendRedirect(REDIRECT_INDEX); //or 404
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().dispose();
    }
}
