package dao;

import entity.Car;

import java.sql.SQLException;
import java.util.List;

public interface TaxisDAO {
    String SQL_SELECT_ALL_CARS = "SELECT * FROM taxisystem.car;";
    String SQL_INSERT_CAR = "INSERT INTO car (number, car, colour) VALUES (?,?,?);";
    String SQL_DELETE_CAR = "DELETE FROM taxisystem.car WHERE number = ?;";

    /**
     * searches all the taxi car from the database.
     *
     * @return cars formed to the list.
     * @throws SQLException when there are problems with database connection.
     */
    List<Car> getCarList() throws SQLException;

    /**
     * registers a new car to the database.
     *
     * @param car we are want to register.
     * @return true if it success, otherwise false.
     */
    boolean addCar(Car car);

    /**
     * delete a car from the database.
     *
     * @param car we are want to delete.
     * @return true if it success, otherwise false.
     */
    boolean removeCar(Car car);
}
