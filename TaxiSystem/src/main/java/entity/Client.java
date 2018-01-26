package entity;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Bean, contains information about client.
 */
public class Client extends User {
    private final static Logger log = Logger.getLogger(Client.class.getClass());
    private int bonusPoints;
    private String mail;

    public Client() {
    }

    public Client(int id, int bonusPoints) {
        super(id);
        this.bonusPoints = bonusPoints;
    }

    public Client(int id, String name, String surname) {
        super(id, null, null, name, surname);
    }

    public Client(String login, String password, String firstName, String lastName, String mail) {
        super(login, password, firstName, lastName);
        this.mail = mail;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public String getMail() {
        return mail;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setFromResultSet(ResultSet resultSet) {
        try {
            this.setId(resultSet.getInt(1));
            this.setLogin(resultSet.getString(2));
            this.setFirstName(resultSet.getString(3));
            this.setLastName(resultSet.getString(4));
            this.setMail(resultSet.getString(5));
            this.setBonusPoints(resultSet.getInt(6));
            this.setBanStatus(resultSet.getBoolean(7));
            this.setRole(resultSet.getString(8));
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }
}
