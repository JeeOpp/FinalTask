package entity;

/**
 * Created by DNAPC on 28.11.2017.
 */
public class Client extends User{
    private int bonusPoints;

    public Client(){}

    public Client(String login, String password, String firstName, String lastName) {
        super(login, password, firstName, lastName);
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

}
