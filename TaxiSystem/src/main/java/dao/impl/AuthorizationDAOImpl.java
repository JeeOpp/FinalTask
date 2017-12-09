package dao.impl;

import dao.AuthorizationDAO;
import dao.WrappedConnector;
import entity.Car;
import entity.Client;
import entity.Taxi;
import entity.User;
import support.MD5;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class AuthorizationDAOImpl implements AuthorizationDAO{

    private WrappedConnector connector;

    public AuthorizationDAOImpl() {
        this.connector = new WrappedConnector();
    }

    public AuthorizationDAOImpl(WrappedConnector connector) {
        this.connector = connector;
    }

    public void close() {
        connector.closeConnection();
    }

    @Override
    public String preAuthorize(String login, String password) throws SQLException {
        ResultSet resultSet;
        PreparedStatement preparedStatement = null;
        String role = null;
        try {
            preparedStatement = connector.getAuthRolePreparedStatement();
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, MD5.md5Hash(password));
            preparedStatement.setString(3, login);
            preparedStatement.setString(4, MD5.md5Hash(password));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                 role = resultSet.getString(1);
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception (request or table failed): " + ex);
        } finally {
            connector.closePreparedStatement(preparedStatement);
        }
        return role;
    }

    @Override
    public Client clientAuthorize(String login, String password) throws SQLException {
        ResultSet resultSet;
        PreparedStatement preparedStatement = null;
        Client client = null;
        try {
            preparedStatement = connector.getAuthClientPreparedStatement();
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, MD5.md5Hash(password));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = new Client();
                client.setId(resultSet.getInt(1));
                client.setLogin(resultSet.getString(2));
                client.setPassword(resultSet.getString(3));
                client.setFirstName(resultSet.getString(4));
                client.setLastName(resultSet.getString(5));
                client.setBonusPoints(resultSet.getInt(6));
                client.setBanStatus(resultSet.getBoolean(7));
                client.setRole(resultSet.getString(8));
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception (request or table failed): " + ex);
        } finally {
            connector.closePreparedStatement(preparedStatement);
        }
        return client;
    }

    @Override
    public Taxi taxiAuthorize(String login, String password) throws SQLException {
        ResultSet resultSet;
        PreparedStatement preparedStatement = null;
        Taxi taxi = null;
        try {
            preparedStatement = connector.getAuthTaxiPreparedStatement();
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, MD5.md5Hash(password));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                taxi = new Taxi();
                taxi.setId(resultSet.getInt(1));
                taxi.setLogin(resultSet.getString(2));
                taxi.setPassword(resultSet.getString(3));
                taxi.setFirstName(resultSet.getString(4));
                taxi.setLastName(resultSet.getString(5));
                taxi.setAvailableStatus(resultSet.getBoolean(6));
                taxi.setBanStatus(resultSet.getBoolean(7));
                taxi.setRole(resultSet.getString(8));
                Car car = new Car();
                car.setNumber(resultSet.getString(9));
                car.setName(resultSet.getString(10));
                car.setColour(resultSet.getString(11));
                taxi.setCar(car);
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception (request or table failed): " + ex);
        } finally {
            connector.closePreparedStatement(preparedStatement);
        }
        return taxi;
    }

    @Override
    public void logOut(User user) throws SQLException {
        //TODO нахера?
    }
}