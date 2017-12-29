package dao.impl;

import dao.connectionPool.ConnectionPool;
import dao.connectionPool.ConnectionPoolException;
import entity.Car;
import entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DNAPC on 06.12.2017.
 */
public class DispatcherDAO {
    private static final String SQL_SELECT_ALL_ORDER="SELECT taxisystem.order.order_id, taxisystem.order.orderStatus, taxisystem.order.source_coord, taxisystem.order.destiny_coord, taxisystem.order.price, client.id, client.login, client.name, client.surname, taxi.id, taxi.login, taxi.name, taxi.surname, car.number, car.car, car.colour FROM taxisystem.order\n" +
            "\tJOIN client ON taxisystem.order.client_id = client.id\n" +
            "    JOIN taxi ON taxisystem.order.taxi_id = taxi.id\n" +
            "    JOIN car ON taxi.carNumber = car.number ORDER BY order_id DESC;";
    private static final String SQL_DELETE_ALL_ORDER="DELETE FROM `order` WHERE `order`.order_id > 0;";
    private static final String SQL_SELECT_ALL_CARS="SELECT * FROM taxisystem.car;";
    private static final String SQL_MAKE_ORDER="INSERT INTO taxisystem.order (client_id, taxi_id,source_coord, destiny_coord, price) VALUES (?,?,?,?,?);";
    private static final String SQL_CANCEL_ORDER ="DELETE FROM taxisystem.order WHERE order_id = ?;";
    private static final String SQL_ACCEPT_ORDER="UPDATE taxisystem.`order` SET orderStatus='accepted' WHERE order_id=?;";
    private static final String SQL_REJECT_ORDER="UPDATE taxisystem.`order` SET orderStatus='rejected' WHERE order_id=?;";
    private static final String SQL_PAY_ORDER="UPDATE taxisystem.`order` SET orderStatus='completed' WHERE order_id=?;";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public DispatcherDAO() {
    }

    public List<Order> getOrderList() throws SQLException{
        List<Order> orderList = new ArrayList<>();
        Order order;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            if ((resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER)) != null) {
                while (resultSet.next()) {
                    order = new Order();
                    order.setFromResultSet(resultSet);
                    orderList.add(order);
                }
            }
        }catch (ConnectionPoolException ex){
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.err.println("SQL exception (request or table failed): " + ex);
        } finally {
            connectionPool.closeConnection(connection,statement,resultSet);
        }
        return orderList;
    }
    public void orderConfirm(Order order) throws SQLException{
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_MAKE_ORDER);
            preparedStatement.setInt(1, order.getClient().getId());
            preparedStatement.setInt(2, order.getTaxi().getId());
            preparedStatement.setString(3, order.getSourceCoordinate());
            preparedStatement.setString(4, order.getDestinyCoordinate());
            preparedStatement.setDouble(5, order.getPrice());
            preparedStatement.execute();
        }catch (ConnectionPoolException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            System.err.println("SQL exception (request or table failed): " + ex);
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
    }
    public void cancelOrder(Integer orderId) throws SQLException{
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_CANCEL_ORDER);
            preparedStatement.setInt(1, orderId);
            preparedStatement.execute();
        }catch (ConnectionPoolException ex){
            ;
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
    }
    public void acceptOrder(Integer orderId) throws SQLException{
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_ACCEPT_ORDER);
            preparedStatement.setInt(1, orderId);
            preparedStatement.execute();
        }catch (ConnectionPoolException ex){
            ;;
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
    }
    public void rejectOrder(Integer orderId) throws SQLException{
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_REJECT_ORDER);
            preparedStatement.setInt(1, orderId);
            preparedStatement.execute();
        }catch (ConnectionPoolException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
    }
    public void payOrder(Integer orderId) throws SQLException{
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_PAY_ORDER);
            preparedStatement.setInt(1, orderId);
            preparedStatement.execute();
        }catch (ConnectionPoolException ex){
            ;;;
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
    }
    public void deleteAllOrders() throws SQLException{
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            statement.execute(SQL_DELETE_ALL_ORDER);
        }catch (ConnectionPoolException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            connectionPool.closeConnection(connection,statement);
        }
    }
    public List<Car> getCarList() throws SQLException{
        List<Car> carList = new ArrayList<>();
        Car car;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            if ((resultSet = statement.executeQuery(SQL_SELECT_ALL_CARS)) != null) {
                while (resultSet.next()) {
                    car = new Car();
                    car.setFromResultSet(resultSet);
                    carList.add(car);
                }
            }
        }catch (ConnectionPoolException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            connectionPool.closeConnection(connection,statement,resultSet);
        }
    return carList;
    }
}
