package controller.command.impl;

import com.google.gson.Gson;
import controller.command.ControllerCommand;
import entity.*;
import service.DispatcherService;
import service.PaginationService;
import service.ServiceFactory;
import service.UserManagerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        if(action.equals("payOrder")){
            payOrder(req,resp);
        }
        if(action.equals("getAllOrders")){
            getAllOrders(req,resp);
        }
        if(action.equals("deleteAllOrders")){
            deleteAllOrders(req,resp);
        }
        if(action.equals("getCarList")){
            getCarList(req,resp);
        }
    }

    void getClientOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Client client = (Client) req.getSession().getAttribute("user");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        List<Order> orderList = dispatcherService.getClientOrders(client);
        req.setAttribute("clientOrder",orderList);
        req.getRequestDispatcher("WEB-INF/Client/orders.jsp").forward(req,resp);
    }
    private void getTaxiOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        Taxi taxi = (Taxi) req.getSession().getAttribute("user");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        List<Order> orderList = dispatcherService.getTaxiOrders(taxi);

        req.setAttribute("taxiOrder", orderList);
        req.getRequestDispatcher("WEB-INF/Taxi/orders.jsp").forward(req,resp);
    }
    private void preOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserManagerService userManagerService = serviceFactory.getUserManagerService();
        List<Taxi> availableTaxiList = userManagerService.getAvailableTaxiList();

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
    private void payOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Integer orderId = Integer.parseInt(req.getParameter("orderId"));

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        if (dispatcherService.payOrder(orderId)){
            getClientOrders(req,resp); //TODO Добавить вверху страницу оплачено
        }
    }
    private void getAllOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int page = 1; //default page
        if(req.getParameter("page") != null){
            page = Integer.parseInt(req.getParameter("page"));
        }
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        List<Order> orderList = dispatcherService.getAllOrderList();


        PaginationService<Order> paginationService = serviceFactory.getPaginationService();
        paginationService.buildPagination(orderList);
        List<Order> pageOrderList = paginationService.getPagination().getPage(page);


        req.setAttribute("pageOrderList", pageOrderList);
        req.setAttribute("countPages", paginationService.getPagination().getCountPages());
        req.setAttribute("currentPage", page);

        req.getRequestDispatcher("WEB-INF/Admin/orders.jsp").forward(req,resp);
    }
    private void deleteAllOrders(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        if (dispatcherService.deleteAllOrders()){
            new UserManager().getTaxiList(req,resp);
        }
    }
    private void getCarList(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        List<Car> carList = dispatcherService.getCarList();

        Gson gson = new Gson();
        String carListJson = gson.toJson(carList);

        resp.setContentType("application/json");
        resp.getWriter().write(carListJson);
    }
}
