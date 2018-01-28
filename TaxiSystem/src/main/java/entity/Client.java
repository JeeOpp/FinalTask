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

    public Client(){}

    public Client(ClientBuilder clientBuilder){
        super(clientBuilder);
        this.bonusPoints = clientBuilder.bonusPoints;
        this.mail = clientBuilder.mail;
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
            this.setPassword(resultSet.getString(3));
            this.setFirstName(resultSet.getString(4));
            this.setLastName(resultSet.getString(5));
            this.setMail(resultSet.getString(6));
            this.setBonusPoints(resultSet.getInt(7));
            this.setBanStatus(resultSet.getBoolean(8));
            this.setRole(resultSet.getString(9));
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }
    public static class ClientBuilder extends UserBuilder{
        private int bonusPoints;
        private String mail;

        public ClientBuilder(){}

        public ClientBuilder setBonusPoints(int bonusPoints) {
            this.bonusPoints = bonusPoints;
            return this;
        }

        public ClientBuilder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        @Override
        public Client build(){
            return new Client(this);
        }
    }
}
