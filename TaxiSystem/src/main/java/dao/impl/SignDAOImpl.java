package dao.impl;

import dao.SignDAO;
import dao.connectionPool.ConnectionPool;
import dao.connectionPool.ConnectionPoolException;
import entity.Car;
import entity.Client;
import entity.Taxi;
import org.apache.log4j.Logger;
import support.MD5;

import java.sql.*;
/**
 * A DAO class uses to read or change data in database and send it to the modal layer.
 */
public class SignDAOImpl implements SignDAO {
    private static final Logger log = Logger.getLogger(SignDAOImpl.class.getClass());
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public SignDAOImpl() {
    }

    /**
     * Searches for a user with the introduced login and password and returns the user role if it success.
     *
     * @param login    user's login.
     * @param password user's password.
     * @return a founded user's role.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public String preAuthorize(String login, String password) throws SQLException {
        String role = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_AUTH_ROLE);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, MD5.md5Hash(password));
            preparedStatement.setString(3, login);
            preparedStatement.setString(4, MD5.md5Hash(password));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                role = resultSet.getString(1);
            }
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return role;
    }

    /**
     * read all the information about introduced client from database.
     *
     * @param login    client's login.
     * @param password client's password.
     * @return all client's information.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public Client clientAuthorize(String login, String password) throws SQLException {
        Client client = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_AUTH_CLIENT);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, MD5.md5Hash(password));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = new Client.ClientBuilder().build();
                client.setFromResultSet(resultSet);
            }
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return client;
    }

    /**
     * read all the information about introduced taxi driver from database.
     *
     * @param login    taxi driver's login.
     * @param password taxi driver's password.
     * @return all taxi drivers's information.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public Taxi taxiAuthorize(String login, String password) throws SQLException {
        Taxi taxi = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_AUTH_TAXI);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, MD5.md5Hash(password));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                taxi = new Taxi();
                taxi.setFromResultSet(resultSet);
            }
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return taxi;
    }

    /**
     * Register new client to database.
     *
     * @param client contains all information about client.
     * @return true if it success, false if there are some problems.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public boolean registerClient(Client client) throws SQLException {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_REG_CLIENT);
            preparedStatement.setString(1, client.getLogin());
            preparedStatement.setString(2, MD5.md5Hash(client.getPassword()));
            preparedStatement.setString(3, client.getFirstName());
            preparedStatement.setString(4, client.getLastName());
            preparedStatement.setString(5, client.getMail());
            preparedStatement.executeUpdate();
            return true;
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
        return false;
    }

    /**
     * Register new taxi to database.
     *
     * @param taxi contains all information about taxi driver.
     * @return true if it success, false if there are some problems.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public boolean registerTaxi(Taxi taxi) throws SQLException {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_REG_TAXI);
            preparedStatement.setString(1, taxi.getLogin());
            preparedStatement.setString(2, MD5.md5Hash(taxi.getPassword()));
            preparedStatement.setString(3, taxi.getFirstName());
            preparedStatement.setString(4, taxi.getLastName());
            preparedStatement.setString(5, taxi.getCar().getNumber());
            preparedStatement.executeUpdate();
            return true;
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
        return false;
    }

    /**
     * try to find a login in the database.
     *
     * @param login user's login
     * @return true if the database contains login, otherwise false.
     */
    @Override
    public boolean isLoginFree(String login) {
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_LOGIN_ALL);
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(login)) {
                    return false;
                }
            }
            return true;
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return false;
    }

    /**
     * try to find a mail in the database.
     *
     * @param mail client's login
     * @return true if the database contains mail, otherwise false.
     */
    @Override
    public boolean isMailFree(String mail) {
        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_MAIL_ALL);
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(mail)) {
                    return false;
                }
            }
            return true;
        } catch (SQLException | ConnectionPoolException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return false;
    }

    /**
     * Changes taxi status to another from database. (available/unavailable)
     *
     * @param taxi contains taxi driver id.
     * @throws SQLException when there are problems with database connection.
     */
    @Override
    public void changeAvailableStatus(Taxi taxi) throws SQLException {
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_CHANGE_AVAILABLE_STATUS);
            preparedStatement.setBoolean(1, !taxi.isAvailableStatus());
            preparedStatement.setInt(2, taxi.getId());
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }
}
