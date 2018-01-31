package dao;

import entity.Order;

import java.sql.SQLException;
import java.util.List;

public interface DispatcherDAO {
    String SQL_DELETE_OBSOLETE_ORDER = "DELETE FROM `order` WHERE `order`.order_id > 0 AND `order`.orderStatus = 'archive';";
    String SQL_MAKE_ORDER = "INSERT INTO taxisystem.order (client_id, taxi_id,source_coord, destiny_coord, price) VALUES (?,?,?,?,?);";
    String SQL_DELETE_ORDER = "DELETE FROM taxisystem.order WHERE order_id = ?;";
    String SQL_CANCEL_ORDER = "DELETE FROM taxisystem.order WHERE order_id = ? AND (orderStatus = 'processed' OR orderStatus = 'accepted') ;";
    String SQL_ACCEPT_ORDER = "UPDATE taxisystem.`order` SET orderStatus='accepted' WHERE order_id=? AND orderStatus = 'processed';";
    String SQL_REJECT_ORDER = "UPDATE taxisystem.`order` SET orderStatus='rejected' WHERE order_id=? AND orderStatus = 'processed';";
    String SQL_PAY_ORDER = "UPDATE taxisystem.`order` SET orderStatus='completed' WHERE order_id=? AND orderStatus = 'accepted';";
    String SQL_ARCHIVE_ORDER = "UPDATE taxisystem.`order` SET orderStatus='archive' WHERE order_id=? AND orderStatus = 'completed';";
    String SQL_SELECT_ALL_ORDER = "SELECT taxisystem.order.order_id, taxisystem.order.orderStatus, taxisystem.order.source_coord, taxisystem.order.destiny_coord, taxisystem.order.price, client.id, client.login, client.name, client.surname, taxi.id, taxi.login, taxi.name, taxi.surname, car.number, car.car, car.colour FROM taxisystem.order\n" +
            " JOIN client ON taxisystem.order.client_id = client.id" +
            " JOIN taxi ON taxisystem.order.taxi_id = taxi.id" +
            " JOIN car ON taxi.carNumber = car.number ORDER BY order_id DESC;";

    /**
     * Read all information about orders from database and forms it into the collection list.
     *
     * @return a list contained all orders information.
     * @throws SQLException when there are problems with database connection.
     */
    List<Order> getOrderList() throws SQLException;

    /**
     * Used to add a new order information to the database.
     *
     * @param order contains a all information about order there are client info, taxi driver info, source, destiny and order price;
     * @throws SQLException when there are problems with database connection.
     */
    boolean orderConfirm(Order order) throws SQLException;

    /**
     * Used to change an order status from database.
     * In the dependence on the status we want to put in the database used a different prepared statement query.
     *
     * @param orderAction the action we want to apply in method.
     * @param orderId     used to identify an order we want to change.
     * @throws SQLException when there are problems with database connection.
     */
    boolean changeOrderStatus(String orderAction, int orderId) throws SQLException;

    /**
     * Delete all the information about orders from database.
     *
     * @throws SQLException when there are problems with database connection.
     */
    boolean deleteObsoleteOrders() throws SQLException;
}
