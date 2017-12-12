package controller.command.impl;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import controller.command.ControllerCommand;
import entity.Client;
import entity.Order;
import service.DispatcherService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by DNAPC on 10.12.2017.
 */
public class Profile implements ControllerCommand
{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("getOrders")) {
            getOrders(req,resp);
        }
    }
    public void getOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Client client = (Client) req.getSession().getAttribute("user");

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        DispatcherService dispatcherService = serviceFactory.getDispatcherService();
        List<Order> orderList = dispatcherService.getClientOrders(client);
        req.setAttribute("clientOrder",orderList);
        req.getRequestDispatcher("WEB-INF/Client/profile.jsp").forward(req,resp);
    }
}
