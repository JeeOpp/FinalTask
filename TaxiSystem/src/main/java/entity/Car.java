package entity;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Car {
    private final static Logger log = Logger.getLogger(Car.class.getClass());
    private String number;
    private String name;
    private String colour;
    
    public Car(){}

    public Car(String number){
        this.number = number;
    }

    public Car(String number, String name, String colour) {
        this(number);
        this.name = name;
        this.colour = colour;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setFromResultSet(ResultSet resultSet){
        try {
            this.setNumber(resultSet.getString(1));
            this.setName(resultSet.getString(2));
            this.setColour(resultSet.getString(3));
        }catch (SQLException ex){
            log.error(ex.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;

        Car car = (Car) o;

        if (!getNumber().equals(car.getNumber())) return false;
        if (getName() != null ? !getName().equals(car.getName()) : car.getName() != null) return false;
        return getColour() != null ? getColour().equals(car.getColour()) : car.getColour() == null;
    }

    @Override
    public int hashCode() {
        int result = getNumber().hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getColour() != null ? getColour().hashCode() : 0);
        return result;
    }
}
