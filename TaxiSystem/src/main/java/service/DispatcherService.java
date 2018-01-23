package service;

import dao.DAOFactory;
import dao.DispatcherDAO;
import dao.UserManagerDAO;
import entity.Client;
import entity.Order;
import entity.Taxi;
import entity.entityEnum.OrderEnum;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import static entity.entityEnum.OrderEnum.OrderAction.ARCHIVE;

public class DispatcherService {
    private final static Logger log = Logger.getLogger(DispatcherService.class.getClass());
    private final static int MIN_PRICE = 0;
    private final static int MIN_BONUS = 0;
    public Boolean callConfirm(Order order, int bonus){
        if(order.getClient().getBonusPoints()<bonus){
            return false;
        }
        double newPrice;
        order.setPrice((newPrice = (order.getPrice() - (bonus*1.0)/100)) < MIN_PRICE ? MIN_PRICE : newPrice);

        DAOFactory daoFactory = DAOFactory.getInstance();
        DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
        UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
        try {
            dispatcherDAO.orderConfirm(order);
            if(bonus>MIN_BONUS){
                userManagerDAO.decreaseBonus(order.getClient(),bonus);
            }
        }catch (SQLException ex){
            log.error(ex.getMessage());
        }
        return true;
    }
    public List<Order> getClientOrders(Client client) {
        List<Order> orderList = getAllOrderList();
        Iterator<Order> orderIterator = orderList.listIterator();
        while (orderIterator.hasNext()) {
            Order order = orderIterator.next();
            if (order.getClient().getId() != client.getId() || order.getOrderStatus().equals(ARCHIVE.getValue())) {
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
            if (order.getTaxi().getId() != taxi.getId() || order.getOrderStatus().equals(ARCHIVE.getValue())) {
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
            log.error(ex.getMessage());
        }
        return orderList;
    }

    public boolean changeOrderStatus(String orderAction, int orderId){
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            dispatcherDAO.changeOrderStatus(orderAction, orderId);
        }catch (SQLException ex){
            log.error(ex.getMessage());
        }
        return true;
    }
    public boolean deleteAllOrders(){
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            dispatcherDAO.deleteAllOrders();
        }catch (SQLException ex){
            log.error(ex.getMessage());
        }
        return true;
    }
}
