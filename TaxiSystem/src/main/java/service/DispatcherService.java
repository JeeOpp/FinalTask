package service;

import dao.DAOFactory;
import dao.impl.DispatcherDAO;
import dao.impl.UserManagerDAO;
import entity.Car;
import entity.Client;
import entity.Order;
import entity.Taxi;
import support.CoordinateGenerator;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DNAPC on 06.12.2017.
 */
public class DispatcherService {

    public Boolean callConfirm(Order order, int bonus){
        if(order.getClient().getBonusPoints()<bonus){
            return false;
        }
        double newPrice;
        order.setPrice((newPrice = (order.getPrice() - (bonus*1.0)/100)) < 0 ? 0 : newPrice);

        //TODO проверяй на клинте чтобы бонусы не были больше чем заказ, и возвращай в этом случае

        DAOFactory daoFactory = DAOFactory.getInstance();
        DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
        UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
        try {
            dispatcherDAO.orderConfirm(order);
            if(bonus>0){
                userManagerDAO.decreaseBonus(order.getClient(),bonus);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return true;
    }
    public List<Order> getClientOrders(Client client) {
        List<Order> orderList = getAllOrderList();
        Iterator<Order> orderIterator = orderList.listIterator();
        while (orderIterator.hasNext()) {
            Order order = orderIterator.next();
            if (order.getClient().getId() != client.getId() || order.getOrderStatus().equals("archive")) {
                orderIterator.remove();
            }
        }
        return orderList;
    }
    public List<Order> getTaxiOrders(Taxi taxi){
        List<Order> orderList = getAllOrderList();
        Iterator<Order> orderIterator = orderList.listIterator();
        while (orderIterator.hasNext()) {
            Order order = orderIterator.next();
            if (order.getTaxi().getId() != taxi.getId() || order.getOrderStatus().equals("archive")) {
                orderIterator.remove();
            }
        }
        return orderList;
    }
    public List<Order> getAllOrderList(){
        List<Order> orderList = null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            orderList = dispatcherDAO.getOrderList();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return orderList;
    }

    public boolean cancelOrder(Integer orderId){
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            dispatcherDAO.cancelOrder(orderId);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return true;
    }
    public boolean acceptOrder(Integer orderId){
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            dispatcherDAO.acceptOrder(orderId);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return true;
    }
    public boolean rejectOrder(Integer orderId){
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            dispatcherDAO.rejectOrder(orderId);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return true;
    }
    public boolean payOrder(Integer orderId){
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            dispatcherDAO.payOrder(orderId);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return true;
    }
    public boolean deleteAllOrders(){
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            dispatcherDAO.deleteAllOrders();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return true;
    }
    public boolean moveOrderToArchive(Integer orderId){
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            dispatcherDAO.moveOrderToAchive(orderId);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return true;
    }
}
