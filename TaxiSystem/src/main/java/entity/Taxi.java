package entity;

/**
 * Created by DNAPC on 28.11.2017.
 */
public class Taxi extends User{
    private String carNumber;
    private boolean availableStatus;

    public Taxi() {
    }

    public String getCarNumber() {
        return carNumber;
    }

    public boolean isAvailableStatus() {
        return availableStatus;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setAvailableStatus(boolean availableStatus) {
        this.availableStatus = availableStatus;
    }
}
