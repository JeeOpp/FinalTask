package dao.impl;

import dao.UserManagerDAO;
import dao.connectionPool.ConnectionPool;
import dao.connectionPool.ConnectionPoolException;
import entity.Client;
import entity.Taxi;
import entity.User;
import org.apache.log4j.Logger;
import support.MD5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static support.constants.UserConstants.TAXI;

/**
 * A DAO class uses to read or change data in database and send it to the modal layer.
 */
public class UserManagerDAOImpl implements UserManagerDAO {
    private static final Logger log = Logger.getLogger(UserManagerDAOImpl.class.getClass());
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public UserManagerDAOImpl() {
    }

    /**
     * Changes user's password from the database.
     *
     * @param user we are want to change.
     */
    @Override
    public synchronized void changePassword(User user) {
        try {
            connection = connectionPool.takeConnection();
            if (user.getRole().equals(TAXI)) {
                preparedStatement = connection.prepareStatement(SQL_CHANGE_TAXI_PASS);
            } else {
                preparedStatement = connection.prepareStatement(SQL_CHANGE_CLIENT_PASS);
            }
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * searches all taxi drivers from database and forms it into the list.
     *
     * @return all taxi driver's information formed into the list.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public List<Taxi> getTaxiList() throws SQLException {
        List<Taxi> taxiList = null;
        Taxi taxi;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_TAXI);
            taxiList = new ArrayList<>();
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
     *
     * @return all client's information formed into the list.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public List<Client> getClientList() throws SQLException {
        List<Client> clientList = null;
        Client client;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            clientList = new ArrayList<>();
            if ((resultSet = statement.executeQuery(SQL_SELECT_ALL_CLIENT)) != null) {
                while (resultSet.next()) {
                    client = new Client();
                    client.setFromResultSet(resultSet);
                    clientList.add(client);
                }
            }
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return clientList;
    }

    /**
     * change the user's ban status.
     *
     * @param user we are want to change status.
     */
    @Override
    public void changeBanStatus(User user) {
        try {
            connection = connectionPool.takeConnection();
            if (user.getRole().equals(TAXI)) {
                preparedStatement = connection.prepareStatement(SQL_GET_TAXI_BAN);
            } else {
                preparedStatement = connection.prepareStatement(SQL_GET_CLIENT_BAN);
            }
            preparedStatement.setBoolean(1, !user.getBanStatus());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Decreases the client's bonus count.
     *
     * @param client whose bonus count we are want to decrease.
     * @param bonus  count we are want to decrease. (how much clients spend)
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public void decreaseBonus(Client client, int bonus) throws SQLException {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DECREASE_BONUS);
            preparedStatement.setInt(1, bonus);
            preparedStatement.setInt(2, client.getId());
            preparedStatement.execute();
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * changes user's bonus count stored in the user's object field.
     *
     * @param client we are want to change.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public void changeBonusCount(Client client) throws SQLException {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_CHANGE_BONUS_COUNT);
            preparedStatement.setInt(1, client.getBonusPoints());
            preparedStatement.setInt(2, client.getId());
            preparedStatement.execute();
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * changes a taxi's driver car to another car.
     *
     * @param taxi stored taxi driver id and car we are want to change.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public void changeTaxiCar(Taxi taxi) throws SQLException {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_CHANGE_TAXI_CAR);
            preparedStatement.setString(1, taxi.getCar().getNumber());
            preparedStatement.setInt(2, taxi.getId());
            preparedStatement.execute();
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * tries to find the client's password if user with that mail and password are registered.
     *
     * @param email client's e-mail
     * @return hashed password value if it success otherwise null.
     */
    @Override
    public String getHashPassword(String email) {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_HASH_PASSWORD);
            preparedStatement.setString(1, email.toLowerCase());
            if ((resultSet = preparedStatement.executeQuery()) != null) {
                resultSet.next();
                return resultSet.getString(1);
            }
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return null;
    }

    /**
     * updates the database and sets a new password for a client.
     *
     * @param mail         client's mail
     * @param hashPassword client's hashed password.
     * @param newPassword  client's new password.
     * @return true if it success otherwise false.
     */
    @Override
    public boolean restorePassword(String mail, String hashPassword, String newPassword) {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_RESTORE_PASSWORD);
            preparedStatement.setString(1, MD5.md5Hash(newPassword));
            preparedStatement.setString(2, mail);
            preparedStatement.setString(3, hashPassword);
            return preparedStatement.executeUpdate() > 0;
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
        return false;
    }
}
