package service;

import dao.DAOFactory;
import dao.impl.DispatcherDAO;
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
    public List<Taxi> getAvailableTaxiList() {
        List<Taxi> taxiList = getAllTaxiList();
        Iterator<Taxi> taxiIterator = taxiList.listIterator();
        while (taxiIterator.hasNext()) {
            if (!taxiIterator.next().isAvailableStatus()) {
                taxiIterator.remove();
            }
        }
        return taxiList;
    }

    public List<Taxi> getAllTaxiList(){
        List<Taxi> taxiList = null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            taxiList = dispatcherDAO.getTaxiList();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return taxiList;
    }

    public Boolean callConfirm(Order order, Integer bonus){
        if(order.getClient().getBonusPoints()<bonus){
            return false;
        }
        double newPrice;
        order.setPrice((newPrice = (order.getPrice() - bonus/100)) < 0 ? 0 : newPrice);

        //TODO проверяй на клинте чтобы бонусы не были больше чем заказ, и возвращай в этом случае

        DAOFactory daoFactory = DAOFactory.getInstance();
        DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
        try {
            dispatcherDAO.orderConfirm(order);
            if(bonus>0){
                dispatcherDAO.decreaseBonus(order.getClient(),bonus);
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
            if (orderIterator.next().getClient().getId() != client.getId()) {
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

}
