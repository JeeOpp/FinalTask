package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Bean, contains information about taxi drivers.
 */
public class Taxi extends User {
    private boolean availableStatus;
    private Car car;

    public Taxi(){}

    public Taxi(TaxiBuilder taxiBuilder){
        super(taxiBuilder);
        this.availableStatus = taxiBuilder.availableStatus;
        this.car = taxiBuilder.car;
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
            this.setPassword(resultSet.getString(3));
            this.setFirstName(resultSet.getString(4));
            this.setLastName(resultSet.getString(5));
            this.setAvailableStatus(resultSet.getBoolean(6));
            this.setBanStatus(resultSet.getBoolean(7));
            this.setRole(resultSet.getString(8));
            car = new Car();
            car.setNumber(resultSet.getString(9));
            car.setName(resultSet.getString(10));
            car.setColour(resultSet.getString(11));
            this.setCar(car);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static class TaxiBuilder extends UserBuilder{
        private boolean availableStatus;
        private Car car;

        public TaxiBuilder(){}

        public TaxiBuilder setAvailableStatus(boolean availableStatus) {
            this.availableStatus = availableStatus;
            return this;
        }

        public TaxiBuilder setCar(Car car) {
            this.car = car;
            return this;
        }

        @Override
        public Taxi build(){
            return new Taxi(this);
        }
    }
}