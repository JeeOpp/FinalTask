//dispatcherDAO метод setResultSet



package controller;

import controller.command.ControllerCommand;
import controller.command.ControllerDirector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        //SSLSender sslSender = new SSLSender("demkoandrey2012@gmail.com","Neponime1234");
        //TLSSender tlsSender = new TLSSender("demkoandrey2012@gmail.com","Neponime1234");

        //tlsSender.send("TaxiSystem question", "TLS: This is text!", "demkoandrey2012@gmail.com", "123467890@p33.org");
        //sslSender.send("TaxiSystem question", "SSL: This is text!", "demkoandrey2012@gmail.com", "123467890@p33.org");
        resp.setContentType("text/html");

        String method = req.getParameter("method");

        ControllerDirector controllerDirector = new ControllerDirector();
        ControllerCommand controllerCommand = controllerDirector.getCommand(method);
        controllerCommand.execute(req,resp);
    }
}
