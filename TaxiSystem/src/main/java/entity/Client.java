package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by DNAPC on 28.11.2017.
 */
public class Client extends User{
    private int bonusPoints;

    public Client(){}

    public Client(int id, int bonusPoints){
        super(id);
        this.bonusPoints = bonusPoints;
    }

    public Client(int id, String name, String surname){
        super(id,null,null,name,surname);
    }

    public Client(String login, String password, String firstName, String lastName) {
        super(login, password, firstName, lastName);
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public void setFromResultSet(ResultSet resultSet){
        try {
            this.setId(resultSet.getInt(1));
            this.setLogin(resultSet.getString(2));
            this.setFirstName(resultSet.getString(3));
            this.setLastName(resultSet.getString(4));
            this.setBonusPoints(resultSet.getInt(5));
            this.setBanStatus(resultSet.getBoolean(6));
            this.setRole(resultSet.getString(7));
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
