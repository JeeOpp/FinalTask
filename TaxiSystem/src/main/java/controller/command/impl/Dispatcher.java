package controller.command.impl;

import controller.command.ControllerCommand;
import entity.Taxi;
import service.DispatcherService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

/**
 * Created by DNAPC on 29.11.2017.
 */
public class Dispatcher implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("preOrder")) {
            preOrder(req,resp);
        }
        if (action.equals("callTaxi")){
            callTaxi(req,resp);
        }
    }

    private void preOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        List<Taxi> availableTaxiList = dispatcherService.getAvailableTaxiList();

        req.setAttribute("availableTaxiList", availableTaxiList);
        req.getRequestDispatcher("WEB-INF/Client/callTaxi.jsp").forward(req, resp);
    }
    private void callTaxi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    }
}
