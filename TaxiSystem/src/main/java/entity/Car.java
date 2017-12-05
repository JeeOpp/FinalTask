package entity;

/**
 * Created by DNAPC on 06.12.2017.
 */
public class Car {
    private String number;
    private String name;
    private String colour;
    
    public Car(){}

    public Car(String number, String name, String colour) {
        this.number = number;
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
}
