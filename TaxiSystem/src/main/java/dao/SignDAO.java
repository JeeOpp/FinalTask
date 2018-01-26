package dao;

import entity.Client;
import entity.Taxi;

import java.sql.SQLException;

public interface SignDAO {
    String SQL_SELECT_LOGIN_ALL = "SELECT client.login FROM client UNION SELECT taxi.login FROM taxi;";
    String SQL_SELECT_MAIL_ALL = "SELECT client.mail FROM client;";
    String SQL_GET_AUTH_ROLE = "SELECT role  FROM client WHERE login=? AND password=? UNION SELECT role  FROM taxi WHERE login=? AND password=?";
    String SQL_GET_AUTH_CLIENT = "SELECT *  FROM client WHERE login=? AND password=?";
    String SQL_GET_AUTH_TAXI = "SELECT id, login, password, name, surname, availableStatus, banStatus,role,number,car,colour  FROM taxi JOIN car ON taxi.carNumber=car.number WHERE login=? AND password=?;";
    String SQL_REG_CLIENT = "INSERT INTO client (login, password, name, surname, mail) VALUES (?,?,?,?,?);";
    String SQL_REG_TAXI = "INSERT INTO taxi (login, password, name, surname,carNumber) VALUES (?,?,?,?,?);";
    String SQL_CHANGE_AVAILABLE_STATUS = "UPDATE taxi SET availableStatus = ?  WHERE id = ?;";

    /**
     * Searches for a user with the introduced login and password and returns the user role if it success.
     *
     * @param login    user's login.
     * @param password user's password.
     * @return a founded user's role.
     * @throws SQLException when there are problems with database connection.
     */
    String preAuthorize(String login, String password) throws SQLException;

    /**
     * read all the information about introduced client from database.
     *
     * @param login    client's login.
     * @param password client's password.
     * @return all client's information.
     * @throws SQLException when there are problems with database connection.
     */
    Client clientAuthorize(String login, String password) throws SQLException;

    /**
     * read all the information about introduced taxi driver from database.
     *
     * @param login    taxi driver's login.
     * @param password taxi driver's password.
     * @return all taxi drivers's information.
     * @throws SQLException when there are problems with database connection.
     */
    Taxi taxiAuthorize(String login, String password) throws SQLException;

    /**
     * Register new client to database.
     *
     * @param client contains all information about client.
     * @return true if it success, false if there are some problems.
     * @throws SQLException when there are problems with database connection.
     */
    boolean registerClient(Client client) throws SQLException;

    /**
     * Register new taxi to database.
     *
     * @param taxi contains all information about taxi driver.
     * @return true if it success, false if there are some problems.
     * @throws SQLException when there are problems with database connection.
     */
    boolean registerTaxi(Taxi taxi) throws SQLException;

    /**
     * try to find a login in the database.
     *
     * @param login user's login
     * @return true if the database contains login, otherwise false.
     */
    boolean isLoginFree(String login);

    /**
     * try to find a mail in the database.
     *
     * @param mail client's login
     * @return true if the database contains mail, otherwise false.
     */
    boolean isMailFree(String mail);

    /**
     * Changes taxi status to another from database. (available - unavailable)
     *
     * @param taxi contains taxi driver id.
     * @throws SQLException when there are problems with database connection.
     */
    void changeAvailableStatus(Taxi taxi) throws SQLException;
}