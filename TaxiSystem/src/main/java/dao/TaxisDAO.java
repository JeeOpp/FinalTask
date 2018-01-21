package dao;

import dao.connectionPool.ConnectionPool;
import dao.connectionPool.ConnectionPoolException;
import entity.Car;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DNAPC on 03.01.2018.
 */
public class TaxisDAO {
    private static final Logger log = Logger.getLogger(TaxisDAO.class.getClass());
    private static final String SQL_SELECT_ALL_CARS="SELECT * FROM taxisystem.car;";
    private static final String SQL_INSERT_CAR = "INSERT INTO car (number, car, colour) VALUES (?,?,?);";
    private static final String SQL_DELETE_CAR = "DELETE FROM taxisystem.car WHERE number = ?;";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public TaxisDAO() {
    }

    public List<Car> getCarList() throws SQLException {
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
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return carList;
    }
    public boolean addCar(Car car){
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_CAR);
            preparedStatement.setString(1,car.getNumber());
            preparedStatement.setString(2,car.getName());
            preparedStatement.setString(3,car.getColour());
            preparedStatement.execute();
            return true;
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return false;
    }
    public boolean removeCar(Car car){
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_CAR);
            preparedStatement.setString(1, car.getNumber());
            preparedStatement.execute();
            return true;
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
        return false;
    }
}
