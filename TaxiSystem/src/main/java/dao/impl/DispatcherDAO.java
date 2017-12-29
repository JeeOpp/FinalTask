package dao.impl;

import entity.Car;
import entity.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private  wrappedConnection;

    public DispatcherDAO() {
        this.wrappedConnection = new WrappedConnection();
    }
    public void close() {
        wrappedConnection.closeConnection();
    }
    public List<Order> getOrderList() throws SQLException{
        ResultSet resultSet;
        Statement statement = null;
        List<Order> orderList = new ArrayList<>();
        Order order;
        try {
            statement = wrappedConnection.getStatement();
            if((resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER))!=null){
                while (resultSet.next()){
                    order = new Order();
                    order.setFromResultSet(resultSet);
                    orderList.add(order);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception (request or table failed): " + ex);
        } finally {
            wrappedConnection.closeStatement(statement);
        }
        return orderList;
    }
    public void orderConfirm(Order order) throws SQLException{
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = wrappedConnection.makeOrderPreparedStatement();
            preparedStatement.setInt(1, order.getClient().getId());
            preparedStatement.setInt(2, order.getTaxi().getId());
            preparedStatement.setString(3, order.getSourceCoordinate());
            preparedStatement.setString(4, order.getDestinyCoordinate());
            preparedStatement.setDouble(5, order.getPrice());
            preparedStatement.execute();
        }catch (SQLException ex){
            System.err.println("SQL exception (request or table failed): " + ex);
        }finally {
            wrappedConnection.closePreparedStatement(preparedStatement);
        }
    }
    public void cancelOrder(Integer orderId) throws SQLException{
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = wrappedConnection.cancelOrder();
            preparedStatement.setInt(1,orderId);
            preparedStatement.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            wrappedConnection.closePreparedStatement(preparedStatement);
        }
    }
    public void acceptOrder(Integer orderId) throws SQLException{
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = wrappedConnection.acceptOrder();
            preparedStatement.setInt(1,orderId);
            preparedStatement.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            wrappedConnection.closePreparedStatement(preparedStatement);
        }
    }
    public void rejectOrder(Integer orderId) throws SQLException{
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = wrappedConnection.rejectOrder();
            preparedStatement.setInt(1,orderId);
            preparedStatement.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            wrappedConnection.closePreparedStatement(preparedStatement);
        }
    }
    public void payOrder(Integer orderId) throws SQLException{
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = wrappedConnection.payOrder();
            preparedStatement.setInt(1,orderId);
            preparedStatement.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            wrappedConnection.closePreparedStatement(preparedStatement);
        }
    }
    public void deleteAllOrders() throws SQLException{
        Statement statement = null;
        try {
            statement = wrappedConnection.getStatement();
            statement.execute(SQL_DELETE_ALL_ORDER);
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            wrappedConnection.closeStatement(statement);
        }
    }
    public List<Car> getCarList() throws SQLException{
        Statement statement = null;
        ResultSet resultSet = null;
        List<Car> carList = new ArrayList<>();
        Car car;
        try {
            statement = wrappedConnection.getStatement();
            if((resultSet = statement.executeQuery(SQL_SELECT_ALL_CARS))!=null){
                while (resultSet.next()){
                    car = new Car();
                    car.setFromResultSet(resultSet);
                    carList.add(car);
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            wrappedConnection.closeStatement(statement);
        }
    return carList;
    }
}
