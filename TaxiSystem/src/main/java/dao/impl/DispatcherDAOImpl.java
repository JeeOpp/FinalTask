package dao.impl;

import dao.DispatcherDAO;
import dao.connectionPool.ConnectionPool;
import dao.connectionPool.ConnectionPoolException;
import entity.Order;
import org.apache.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A DAO class uses to read or change data in database and send a data to the modal layer.
 */
public class DispatcherDAOImpl implements DispatcherDAO {
    private static final Logger log = Logger.getLogger(DispatcherDAOImpl.class.getClass());
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    public DispatcherDAOImpl() {
    }

    /**
     * Read all information about orders from database and forms it into the collection list.
     *
     * @return a list contained all orders information.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public List<Order> getOrderList() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Order> orderList = new ArrayList<>();
        Order order;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER);
            while (resultSet.next()) {
                order = new Order();
                order.setFromResultSet(resultSet);
                orderList.add(order);
            }
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return orderList;
    }

    /**
     * Used to add a new order information to the database.
     *
     * @param order contains a all information about order there are client info, taxi driver info, source, destiny and order price;
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public void orderConfirm(Order order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_MAKE_ORDER);
            preparedStatement.setInt(1, order.getClient().getId());
            preparedStatement.setInt(2, order.getTaxi().getId());
            preparedStatement.setString(3, order.getSourceCoordinate());
            preparedStatement.setString(4, order.getDestinyCoordinate());
            preparedStatement.setDouble(5, order.getPrice());
            preparedStatement.execute();
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Used to change an order status from database.
     * In the dependence on the status we want to put in the database used a different prepared statement query.
     *
     * @param orderAction the action we want to apply in method.
     * @param orderId     used to identify an order we want to change.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public void changeOrderStatus(String orderAction, int orderId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(chooseOrderAction(orderAction));
            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Delete all the information about orders from database.
     *
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public void deleteAllOrders() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            statement.execute(SQL_DELETE_ALL_ORDER);
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, statement);
        }
    }

    /**
     * used to identify a special SQL query we want to use dependent on action parameter.
     *
     * @param orderAction identify sql query we want to get
     * @return the special sql query.
     */
    private String chooseOrderAction(String orderAction) {
        OrderAction orderEnum = OrderAction.getConstant(orderAction);
        switch (orderEnum) {
            case CANCEL:
                return SQL_CANCEL_ORDER;
            case ACCEPT:
                return SQL_ACCEPT_ORDER;
            case REJECT:
                return SQL_REJECT_ORDER;
            case PAY:
                return SQL_PAY_ORDER;
            case ARCHIVE:
                return SQL_ARCHIVE_ORDER;
        }
        return null;
    }

    enum OrderAction {
        CANCEL("cancel"),
        ACCEPT("accept"),
        REJECT("reject"),
        PAY("pay"),
        ARCHIVE("archive"),
        NONE("none");

        private String value;

        OrderAction(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        /**
         * In the dependence on the received value, return special enum.
         *
         * @param orderStatus Special enum we want to get.
         * @return get special enum in accordance with the action value.
         * If there are no matches return a NONE enum.
         */
        public static OrderAction getConstant(String orderStatus) {
            for (OrderAction each : OrderAction.values()) {
                if (each.getValue().equals(orderStatus)) {
                    return each;
                }
            }
            return NONE;
        }
    }
}
