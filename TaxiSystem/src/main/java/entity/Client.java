package entity;

/**
 * Created by DNAPC on 28.11.2017.
 */
public class Client extends User{
    private int bonusPoints;
    private boolean banStatus;

    public Client(){}

    public Client(String login, String password, String firstName, String lastName) {
        super(login, password, firstName, lastName);
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public boolean hasBanStatus() {
        return banStatus;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public void setBanStatus(boolean banStatus) {
        this.banStatus = banStatus;
    }
}
