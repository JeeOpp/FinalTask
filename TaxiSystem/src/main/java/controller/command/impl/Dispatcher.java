package controller.command.impl;

import com.google.gson.Gson;
import controller.command.ControllerCommand;
import entity.*;
import service.*;

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
        SwitchConstant switchConstant = SwitchConstant.getConstant(action);

        switch (switchConstant){
            case ACCEPT_ORDER:
                acceptOrder(req,resp);
                break;
            case CALL_TAXI:
                callTaxi(req,resp);
                break;
            case CANCEL_ORDER:
                cancelOrder(req,resp);
                break;
            case DELETE_ALL_ORDERS:
                deleteAllOrders(req,resp);
                break;
            case GET_ALL_ORDERS:
                getAllOrders(req,resp);
                break;
            case GET_JSON_CAR_LIST:
                getCarList(req,resp);
                break;
            case GET_CLIENT_ORDERS:
                getClientOrders(req,resp);
                break;
            case GET_TAXI_ORDERS:
                getTaxiOrders(req,resp);
                break;
            case PAY_ORDER:
                payOrder(req,resp);
                break;
            case PRE_ORDER:
                preOrder(req,resp);
                break;
            case REJECT_ORDER:
                rejectOrder(req,resp);
                break;
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
        String bonusText = req.getParameter("bonus");
        Integer bonus = Integer.parseInt(bonusText.isEmpty() ? "0":bonusText);
        Double price = Double.parseDouble(req.getParameter("price"));

        Order order = new Order(client,taxi,sourceCoordinate,destinyCoordinate,price);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        if (dispatcherService.callConfirm(order, bonus)){
            getClientOrders(req,resp);
        }else{
            preOrder(req,resp);
        }
    }
    private void cancelOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user = (User) req.getSession().getAttribute("user");
        Integer orderId = Integer.parseInt(req.getParameter("orderId"));

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        if (dispatcherService.cancelOrder(orderId)){
            if(user.getRole().equals("client")){
                getClientOrders(req,resp);   //to user
            }else {
                getAllOrders(req, resp);     //to admin
            }
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
            getClientOrders(req,resp);
        }
    }
    private void getAllOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int page = 1; //default page
        if(req.getParameter("numPage") != null){
            page = Integer.parseInt(req.getParameter("numPage"));
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
        TaxisService taxisService = serviceFactory.getTaxisService();
        List<Car> carList = taxisService.getCarList();

        Gson gson = new Gson();
        String carListJson = gson.toJson(carList);

        resp.setContentType("application/json");
        resp.getWriter().write(carListJson);
    }
}
