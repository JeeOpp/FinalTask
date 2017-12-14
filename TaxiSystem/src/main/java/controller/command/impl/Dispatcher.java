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
        if(action.equals("getClientOrders")){
            getClientOrders(req,resp);
        }
        if(action.equals("getTaxiOrders")){
            getTaxiOrders(req,resp);
        }
        if(action.equals("cancelOrder")){
            cancelOrder(req,resp);
        }
        if(action.equals("acceptOrder")){
            acceptOrder(req,resp);
        }
        if(action.equals("rejectOrder")){
            rejectOrder(req,resp);
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
            getClientOrders(req,resp);
        }else{
            //TODO недостаточно бонусов
            preOrder(req,resp);  //Костыль?!?!
        }
    }
    public void getClientOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Client client = (Client) req.getSession().getAttribute("user");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        List<Order> orderList = dispatcherService.getClientOrders(client);
        req.setAttribute("clientOrder",orderList);
        req.getRequestDispatcher("WEB-INF/Client/order.jsp").forward(req,resp);
    }
    public void getTaxiOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        Taxi taxi = (Taxi) req.getSession().getAttribute("user");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        List<Order> orderList = dispatcherService.getTaxiOrders(taxi);

        req.setAttribute("taxiOrder", orderList);
        req.getRequestDispatcher("WEB-INF/Taxi/order.jsp").forward(req,resp);
    }
    private void cancelOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Integer orderId = Integer.parseInt(req.getParameter("orderId"));

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        if (dispatcherService.cancelOrder(orderId)){
            getClientOrders(req,resp);
        }
    }
    private void acceptOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Integer orderId = Integer.parseInt(req.getParameter("orderId"));

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        if (dispatcherService.acceptOrder(orderId)){
            //TODO availablestatus  0
            req.getRequestDispatcher("WEB-INF/Taxi/main.jsp").forward(req,resp);
        }
    }
    private void rejectOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Integer orderId = Integer.parseInt(req.getParameter("orderId"));

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        if (dispatcherService.rejectOrder(orderId)){
            getTaxiOrders(req,resp);
        }
    }
}
