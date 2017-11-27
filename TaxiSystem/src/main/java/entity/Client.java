package entity;

/**
 * Created by DNAPC on 28.11.2017.
 */
public class Client {
    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private int bonusPoints;
    private boolean banStatus;

    public Client(){}

    public Client(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Client(String login, String password, String firstName, String lastName) {
        this(login,password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Client(int id, String login, String password, String firstName, String lastName, int bonusPoints, boolean banStatus) {
        this(login,password,firstName,lastName);
        this.id = id;
        this.bonusPoints = bonusPoints;
        this.banStatus = banStatus;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public boolean hasBanStatus() {
        return banStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public void setBanStatus(boolean banStatus) {
        this.banStatus = banStatus;
    }
}
