package dao;

import dao.connectionPool.ConnectionPool;
import dao.connectionPool.ConnectionPoolException;
import entity.Client;
import entity.Order;
import entity.Taxi;
import entity.User;
import entity.entityEnum.UserEnum;
import org.apache.log4j.Logger;
import support.MD5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * A DAO class uses to read or change data in database and send it to the modal layer.
 */
public class UserManagerDAO {
    private static final Logger log = Logger.getLogger(UserManagerDAO.class.getClass());
    private static final String SQL_SELECT_ALL_TAXI = "SELECT taxi.id, taxi.login, taxi.name, taxi.surname, taxi.availableStatus, taxi.banStatus, taxi.role, car.number, car.car, car.colour FROM taxisystem.taxi LEFT JOIN car ON taxi.carNumber = car.number;";
    private static final String SQL_SELECT_ALL_CLIENT = "SELECT client.id, client.login, client.name, client.surname, client.mail, client.bonusPoints, client.banStatus, client.role FROM taxisystem.client WHERE client.role = \"client\";";
    private static final String SQL_CHANGE_TAXI_PASS = "UPDATE taxi SET password = ? WHERE id=?;";
    private static final String SQL_CHANGE_CLIENT_PASS = "UPDATE client SET password = ? WHERE id=?;";
    private static final String SQL_GET_TAXI_BAN = "UPDATE taxi SET banStatus = ? WHERE id = ?;";
    private static final String SQL_GET_CLIENT_BAN = "UPDATE client SET banStatus = ? WHERE id = ?;";
    private static final String SQL_DECREASE_BONUS = "UPDATE client SET bonusPoints = bonusPoints - ?  WHERE id = ?;";
    private static final String SQL_CHANGE_BONUS_COUNT = "UPDATE client SET bonusPoints = bonusPoints + ?  WHERE id = ?;";
    private static final String SQL_CHANGE_TAXI_CAR = "UPDATE taxi SET carNumber=? WHERE id=?";
    private static final String SQL_GET_HASH_PASSWORD = "SELECT password FROM taxisystem.client WHERE mail = ?;";
    private static final String SQL_RESTORE_PASSWORD = "UPDATE client SET password=? WHERE mail=? AND password=?";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    UserManagerDAO() {
    }

    /**
     * Changes user's password from the database.
     * @param user we are want to change.
     */
    public void changePassword(User user){
        try {
            connection = connectionPool.takeConnection();
            if (user.getRole().equals(UserEnum.TAXI.getValue())) {
                preparedStatement = connection.prepareStatement(SQL_CHANGE_TAXI_PASS);
            } else {
                preparedStatement = connection.prepareStatement(SQL_CHANGE_CLIENT_PASS);
            }
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
    }

    /**
     * searches all taxi drivers from database and forms it into the list.
     * @return all taxi driver's information formed into the list.
     * @throws SQLException when there are problems with database connection.
     */
    public List<Taxi> getTaxiList() throws SQLException {
        List<Taxi> taxiList = new ArrayList<>();
        Taxi taxi;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_TAXI);
            while (resultSet.next()) {
                taxi = new Taxi();
                taxi.setFromResultSet(resultSet);
                taxiList.add(taxi);
            }
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return taxiList;
    }
    /**
     * searches all clients from database and forms it into the list.
     * @return all client's information formed into the list.
     * @throws SQLException when there are problems with database connection.
     */
    public List<Client> getClientList() throws SQLException{
        List<Client> clientList = new ArrayList<>();
        Client client;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            if((resultSet = statement.executeQuery(SQL_SELECT_ALL_CLIENT))!=null){
                while (resultSet.next()){
                    client = new Client();
                    client.setFromResultSet(resultSet);
                    clientList.add(client);
                }
            }
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection,statement,resultSet);
        }
        return clientList;
    }

    /**
     * change the user's ban status.
     * @param user we are want to change status.
     */
    public void changeBanStatus(User user){
        try {
            connection = connectionPool.takeConnection();
            if(user.getRole().equals(UserEnum.TAXI.getValue())) {
                preparedStatement = connection.prepareStatement(SQL_GET_TAXI_BAN);
            }else{
                preparedStatement = connection.prepareStatement(SQL_GET_CLIENT_BAN);
            }
            preparedStatement.setBoolean(1,!user.getBanStatus());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
    }

    /**
     * Decreases the client's bonus count.
     * @param client whose bonus count we are want to decrease.
     * @param bonus count we are want to decrease.
     * @throws SQLException when there are problems with database connection.
     */
    public void decreaseBonus(Client client, int bonus) throws SQLException{ //bonus - how many client spend
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DECREASE_BONUS);
            preparedStatement.setInt(1,bonus);
            preparedStatement.setInt(2,client.getId());
            preparedStatement.execute();
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
    }

    /**
     * changes user's bonus count stored in the user's object field.
     * @param client we are want to change.
     * @throws SQLException when there are problems with database connection.
     */
    public void changeBonusCount(Client client) throws SQLException{
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_CHANGE_BONUS_COUNT);
            preparedStatement.setInt(1,client.getBonusPoints());
            preparedStatement.setInt(2,client.getId());
            preparedStatement.execute();
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
    }

    /**
     * changes a taxi's driver car to another car.
     * @param taxi stored taxi driver id and car we are want to change.
     * @throws SQLException when there are problems with database connection.
     */
    public void changeTaxiCar(Taxi taxi) throws SQLException{
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_CHANGE_TAXI_CAR);
            preparedStatement.setString(1,taxi.getCar().getNumber());
            preparedStatement.setInt(2,taxi.getId());
            preparedStatement.execute();
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
    }

    /**
     * tries to find the client's password if user with that mail and password are registered.
     * @param email client's e-mail
     * @return hashed password value if it success otherwise null.
     */
    public String getHashPassword(String email){
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_HASH_PASSWORD);
            preparedStatement.setString(1,email.toLowerCase());
            if ((resultSet = preparedStatement.executeQuery()) != null) {
                resultSet.next();
                return resultSet.getString(1);
            }
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection,preparedStatement,resultSet);
        }
        return null;
    }

    /**
     * updates the database and sets a new password for a client.
     * @param mail  client's mail
     * @param hashPassword client's hashed password.
     * @param newPassword client's new password.
     * @return true if it success otherwise false.
     */
    public boolean restorePassword(String mail,String hashPassword,String newPassword){
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_RESTORE_PASSWORD);
            preparedStatement.setString(1, MD5.md5Hash(newPassword));
            preparedStatement.setString(2,mail);
            preparedStatement.setString(3,hashPassword);
            return preparedStatement.executeUpdate()>0;
        }catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        }finally {
            connectionPool.closeConnection(connection,preparedStatement);
        }
        return false;
    }
}
