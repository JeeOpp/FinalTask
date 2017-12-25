package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by DNAPC on 06.12.2017.
 */
public class Car {
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
            ex.printStackTrace();
        }
    }
}
