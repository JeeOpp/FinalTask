package service;

import entity.Client;
import entity.Order;
import entity.Taxi;
import java.util.List;

public interface DispatcherService {
    /**
     * Client makes an order, calculates order price considering the bonuses client wants to spend.
     * Delegates order writing and delegates bonus decrease (if client wants to spend it) to the DAO layer
     *
     * @param order that client want to order.
     * @param bonus client want to spend.
     * @return false if client has less bonuses then he are going to spent, true if it success.
     */
    boolean callConfirm(Order order, int bonus);

    /**
     * Delegates selecting the client's orders to DAO layer and form their to list. If order has "archive" status remove it from list.
     *
     * @param client whose orders we want to select.
     * @return client's order list.
     */
    List<Order> getClientOrders(Client client);

    /**
     * Delegates selecting the taxi's orders to DAO layer and form their to list. Remove order from list if it has "archive" status .
     *
     * @param taxi whose orders we wants to select.
     * @return client's order list.
     */
    List<Order> getTaxiOrders(Taxi taxi);

    /**
     * Delegates selecting all the orders to DAO layer and form their to list.
     *
     * @return all order list.
     */
    List<Order> getAllOrderList();

    /**
     * Changes a order status according the input parameter.
     * Delegates updating orders to DAO layer.
     *
     * @param orderAction we are want to apply.
     * @param orderId     order we want to update.
     * @return true is changed successfully, otherwise false.
     */
    boolean changeOrderStatus(String orderAction, int orderId);

    /**
     * Delegates deleting the orders to DAO layer.
     *
     * @return true if deleting success, otherwise false.
     */
    boolean deleteAllOrders();
}