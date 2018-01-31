package service.impl;

import dao.DAOFactory;
import dao.DispatcherDAO;
import dao.UserManagerDAO;
import entity.Client;
import entity.Order;
import entity.Taxi;
import org.apache.log4j.Logger;
import service.DispatcherService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static entity.entityEnum.OrderEnum.OrderAction.ARCHIVE;

/**
 * Service class contains order business logic.
 * Delegates action with database to the {@link DispatcherDAO}
 */
public class DispatcherServiceImpl implements DispatcherService {
    private final static Logger log = Logger.getLogger(DispatcherServiceImpl.class.getClass());
    private final static int MIN_PRICE = 0;
    private final static int MIN_BONUS = 0;

    /**
     * Client makes an order, calculates order price considering the bonuses client wants to spend.
     * Delegates order writing and delegates bonus decrease (if client wants to spend it) to the DAO layer
     *
     * @param order that client want to order.
     * @param bonus client want to spend.
     * @return false if client has less bonuses then he are going to spent or client didn't complete previous order, true if it success.
     */
    @Override
    public boolean callConfirm(Order order, int bonus) {
        if (order.getClient().getBonusPoints() < bonus) {
            return false;
        }
        double newPrice;
        order.setPrice((newPrice = (order.getPrice() - (bonus * 1.0) / 100)) < MIN_PRICE ? MIN_PRICE : newPrice);

        DAOFactory daoFactory = DAOFactory.getInstance();
        DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
        UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
        try {
            if (dispatcherDAO.orderConfirm(order)) {
                if (bonus > MIN_BONUS) {
                    userManagerDAO.decreaseBonus(order.getClient(), bonus);
                }
                return true;
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

    /**
     * Delegates selecting the client's orders to DAO layer and form their to list. If order has "archive" status remove it from list.
     *
     * @param client whose orders we want to select.
     * @return client's order list.
     */
    @Override
    public List<Order> getClientOrders(Client client) {
        List<Order> orderList = getAllOrderList();
        if(orderList==null){
            orderList = Collections.emptyList();
        }
        Iterator<Order> orderIterator = orderList.listIterator();
        while (orderIterator.hasNext()) {
            Order order = orderIterator.next();
            if (order.getClient().getId() != client.getId() || order.getOrderStatus().equals(ARCHIVE.getValue())) {
                orderIterator.remove();
            }
        }
        return orderList;
    }

    /**
     * Delegates selecting the taxi's orders to DAO layer and form their to list. Remove order from list if it has "archive" status .
     *
     * @param taxi whose orders we wants to select.
     * @return client's order list.
     */
    @Override
    public List<Order> getTaxiOrders(Taxi taxi) {
        List<Order> orderList = getAllOrderList();
        if(orderList==null){
            orderList = Collections.emptyList();
        }
        Iterator<Order> orderIterator = orderList.listIterator();
        while (orderIterator.hasNext()) {
            Order order = orderIterator.next();
            if (order.getTaxi().getId() != taxi.getId() || order.getOrderStatus().equals(ARCHIVE.getValue())) {
                orderIterator.remove();
            }
        }
        return orderList;
    }

    /**
     * Delegates selecting all the orders to DAO layer and form their to list.
     *
     * @return all order list.
     */
    @Override
    public List<Order> getAllOrderList() {
        List<Order> orderList = null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            orderList = dispatcherDAO.getOrderList();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return orderList;
    }

    /**
     * Changes a order status according the input parameter.
     * Delegates updating orders to DAO layer.
     *
     * @param orderAction we are want to apply.
     * @param orderId     order we want to update.
     */
    @Override
    public boolean changeOrderStatus(String orderAction, int orderId) {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            return dispatcherDAO.changeOrderStatus(orderAction, orderId);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

    /**
     * Delegates deleting the orders to DAO layer.
     *
     * @return true if deleting success, otherwise false.
     */
    @Override
    public boolean deleteObsoleteOrders() {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            return dispatcherDAO.deleteObsoleteOrders();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }
}
