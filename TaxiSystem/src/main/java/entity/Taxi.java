package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by DNAPC on 28.11.2017.
 */
public class Taxi extends User{
    private boolean availableStatus;
    private Car car;

    public Taxi() {
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

    public void setFromResultSet(ResultSet resultSet){
        Car car;
        try {
            this.setId(resultSet.getInt(1));
            this.setLogin(resultSet.getString(2));
            this.setFirstName(resultSet.getString(3));
            this.setLastName(resultSet.getString(4));
            car = new Car();
            car.setNumber(resultSet.getString(5));
            car.setName(resultSet.getString(6));
            car.setColour(resultSet.getString(7));
            this.setCar(car);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
