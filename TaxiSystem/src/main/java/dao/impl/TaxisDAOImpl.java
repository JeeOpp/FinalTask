package dao.impl;

import dao.TaxisDAO;
import dao.connectionPool.ConnectionPool;
import dao.connectionPool.ConnectionPoolException;
import entity.Car;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * A DAO class uses to read or change data in database and send it to the modal layer.
 */
public class TaxisDAOImpl implements TaxisDAO {
    private static final Logger log = Logger.getLogger(TaxisDAOImpl.class.getClass());
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public TaxisDAOImpl() {
    }

    /**
     * searches all the taxi car from the database.
     *
     * @return cars formed to the list.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public List<Car> getCarList() throws SQLException {
        List<Car> carList = new ArrayList<>();
        Car car;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_CARS);
            while (resultSet.next()) {
                car = new Car();
                car.setFromResultSet(resultSet);
                carList.add(car);
            }
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return carList;
    }

    /**
     * registers a new car to the database.
     *
     * @param car we want to register.
     * @return true if it success, otherwise false.
     */
    @Override
    public boolean addCar(Car car) {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_CAR);
            preparedStatement.setString(1, car.getNumber());
            preparedStatement.setString(2, car.getName());
            preparedStatement.setString(3, car.getColour());
            preparedStatement.executeUpdate();
            return true;
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
        return false;
    }

    /**
     * deletes a car from the database.
     *
     * @param car we want to delete.
     * @return true if it success, otherwise false.
     */
    @Override
    public boolean removeCar(Car car) {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_CAR);
            preparedStatement.setString(1, car.getNumber());
            preparedStatement.executeUpdate();
            return true;
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
        return false;
    }
}
