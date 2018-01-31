package dao.impl;

import dao.DAOFactory;
import dao.DispatcherDAO;
import entity.Order;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DNAPC on 30.01.2018.
 */
public class DispatcherDAOImplTest {
    private static final DAOFactory daoFactory = DAOFactory.getInstance();
    private static final DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
    private static final int FIRST_ORDER = 1;
    private static final String ARCHIVE = "archive";

    @Test
    public void getOrderList() throws Exception {
        List<Order> orderList = dispatcherDAO.getOrderList();
        assertTrue(orderList!=null);
    }

    @Test
    public void deleteObsoleteOrders() throws Exception{
        boolean actual = false;
        dispatcherDAO.deleteObsoleteOrders();
        List<Order> orderList = dispatcherDAO.getOrderList();
        if (orderList!=null){
            for (Order order : orderList) {
                if (order.getOrderStatus().equals(ARCHIVE)) {
                    actual = true;
                }
            }
        }
        assertFalse(actual);
    }

    @Test
    public void changeOrderStatus() throws Exception {
        boolean actual = false;
        dispatcherDAO.changeOrderStatus(ARCHIVE,FIRST_ORDER);
        List<Order> orderList = dispatcherDAO.getOrderList();
        if (orderList!=null) {
            for (Order order : orderList) {
                if (order.getOrderId() == FIRST_ORDER && order.getOrderStatus().equals(ARCHIVE)) {
                    actual = true;
                }
            }
        }
        assertTrue(actual);
    }

    @Test
    public void deleteAllOrders() throws Exception {
        dispatcherDAO.deleteObsoleteOrders();
        List<Order> orderList = dispatcherDAO.getOrderList();
        assertTrue(orderList!=null && orderList.isEmpty());
    }

}