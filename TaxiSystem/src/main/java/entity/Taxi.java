package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Bean, contains information about taxi drivers.
 */
public class Taxi extends User {
    private boolean availableStatus;
    private Car car;

    public Taxi() {
    }

    public Taxi(int id) {
        super(id);
    }

    public Taxi(int id, Car car) {
        this(id);
        this.car = car;
    }

    public Taxi(int id, String name, String surname) {
        super(id, null, null, name, surname);
    }

    public Taxi(String login, String password, String firstName, String lastName, String role, Car car) {
        super(login, password, firstName, lastName, role);
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public boolean isAvailableStatus() {
        return availableStatus;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setAvailableStatus(boolean availableStatus) {
        this.availableStatus = availableStatus;
    }

    public void setFromResultSet(ResultSet resultSet) {
        Car car;
        try {
            this.setId(resultSet.getInt(1));
            this.setLogin(resultSet.getString(2));
            this.setFirstName(resultSet.getString(3));
            this.setLastName(resultSet.getString(4));
            this.setAvailableStatus(resultSet.getBoolean(5));
            this.setBanStatus(resultSet.getBoolean(6));
            this.setRole(resultSet.getString(7));
            car = new Car();
            car.setNumber(resultSet.getString(8));
            car.setName(resultSet.getString(9));
            car.setColour(resultSet.getString(10));
            this.setCar(car);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}