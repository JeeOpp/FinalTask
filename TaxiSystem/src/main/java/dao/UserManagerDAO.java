package dao;

import entity.Client;
import entity.Taxi;
import entity.User;
import java.sql.SQLException;
import java.util.List;

public interface UserManagerDAO {
    String SQL_SELECT_ALL_TAXI = "SELECT taxi.id, taxi.login, taxi.password, taxi.name, taxi.surname, taxi.availableStatus, taxi.banStatus, taxi.role, car.number, car.car, car.colour FROM taxisystem.taxi LEFT JOIN car ON taxi.carNumber = car.number;";
    String SQL_SELECT_ALL_CLIENT = "SELECT client.id, client.login, client.password, client.name, client.surname, client.mail, client.bonusPoints, client.banStatus, client.role FROM taxisystem.client WHERE client.role = \"client\";";
    String SQL_CHANGE_TAXI_PASS = "UPDATE taxi SET password = ? WHERE id=?;";
    String SQL_CHANGE_CLIENT_PASS = "UPDATE client SET password = ? WHERE id=?;";
    String SQL_GET_TAXI_BAN = "UPDATE taxi SET banStatus = ? WHERE id = ?;";
    String SQL_GET_CLIENT_BAN = "UPDATE client SET banStatus = ? WHERE id = ?;";
    String SQL_DECREASE_BONUS = "UPDATE client SET bonusPoints = bonusPoints - ?  WHERE id = ?;";
    String SQL_CHANGE_BONUS_COUNT = "UPDATE client SET bonusPoints = bonusPoints + ?  WHERE id = ?;";
    String SQL_CHANGE_TAXI_CAR = "UPDATE taxi SET carNumber=? WHERE id=?";
    String SQL_GET_HASH_PASSWORD = "SELECT password FROM taxisystem.client WHERE mail = ?;";
    String SQL_RESTORE_PASSWORD = "UPDATE client SET password=? WHERE mail=? AND password=?";


    /**
     * Changes user's password from the database.
     *
     * @param user we are want to change.
     */
    void changePassword(User user);

    /**
     * searches all taxi drivers from database and forms it into the list.
     *
     * @return all taxi driver's information formed into the list.
     * @throws SQLException when there are problems with database connection.
     */
    List<Taxi> getTaxiList() throws SQLException;

    /**
     * searches all clients from database and forms it into the list.
     *
     * @return all client's information formed into the list.
     * @throws SQLException when there are problems with database connection.
     */
    List<Client> getClientList() throws SQLException;

    /**
     * change the user's ban status.
     *
     * @param user we are want to change status.
     */
    void changeBanStatus(User user);

    /**
     * Decreases the client's bonus count.
     *
     * @param client whose bonus count we are want to decrease.
     * @param bonus  count we are want to decrease. (how much clients spend)
     * @throws SQLException when there are problems with database connection.
     */
    void decreaseBonus(Client client, int bonus) throws SQLException;

    /**
     * changes user's bonus count stored in the user's object field.
     *
     * @param client we are want to change.
     * @throws SQLException when there are problems with database connection.
     */
    void changeBonusCount(Client client) throws SQLException;

    /**
     * changes a taxi's driver car to another car.
     *
     * @param taxi stored taxi driver id and car we are want to change.
     * @throws SQLException when there are problems with database connection.
     */
    void changeTaxiCar(Taxi taxi) throws SQLException;

    /**
     * tries to find the client's password if user with that mail and password are registered.
     *
     * @param email client's e-mail
     * @return hashed password value if it success otherwise null.
     */
    String getHashPassword(String email);

    /**
     * updates the database and sets a new password for a client.
     *
     * @param mail         client's mail
     * @param hashPassword client's hashed password.
     * @param newPassword  client's new password.
     * @return true if it success otherwise false.
     */
    boolean restorePassword(String mail, String hashPassword, String newPassword);
}
