package entity;

import java.sql.ResultSet;

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

    public void setCarNumber(Car carNumber) {
        this.car = car;
    }

    public void setAvailableStatus(boolean availableStatus) {
        this.availableStatus = availableStatus;
    }

    public void setFromResultSet(ResultSet resultSet){

    }
}
