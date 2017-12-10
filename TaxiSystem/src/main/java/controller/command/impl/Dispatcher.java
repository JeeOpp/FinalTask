package controller.command.impl;

import com.google.gson.Gson;
import controller.command.ControllerCommand;
import entity.Client;
import entity.Order;
import entity.Taxi;
import service.DispatcherService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        if(action.equals("orderForTaxi")){
            orderForTaxi(req,resp);
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
        String sourceCoordinate = req.getParameter("sourceCoordinate");
        String destinyCoordinate = req.getParameter("destinyCoordinate");
        Client client = (Client) req.getSession().getAttribute("user");
        Taxi taxi = new Taxi(Integer.parseInt(req.getParameter("taxi"))) ;
        Integer bonus = Integer.parseInt(req.getParameter("bonus"));
        Double price = Double.parseDouble(req.getParameter("price"));

        Order order = new Order(client,taxi,sourceCoordinate,destinyCoordinate,price);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        if (dispatcherService.callConfirm(order, bonus)){
            req.getRequestDispatcher("WEB-INF/Client/main.jsp").forward(req,resp);
        }else{
            //TODO недостаточно бонусов
            //баг - нужно еще раз как-то список таксистов доставать!
            req.getRequestDispatcher("WEB-INF/Client/callTaxi.jsp").forward(req,resp);
        }
    }

    private void orderForTaxi(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{

    }

}
