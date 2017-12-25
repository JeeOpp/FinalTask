package dao.impl;

import dao.WrappedConnector;
import entity.Car;
import entity.Client;
import entity.Taxi;
import entity.User;
import support.MD5;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by DNAPC on 18.12.2017.
 */
public class SignDAO {
    private static final String SQL_SELECT_LOGIN_ALL = "SELECT client.login FROM client UNION SELECT taxi.login FROM taxi;";

    private WrappedConnector connector;

    public SignDAO() {
        this.connector = new WrappedConnector();
    }

    public SignDAO(WrappedConnector connector) {
        this.connector = connector;
    }

    public void close() {
        connector.closeConnection();
    }

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

    public Boolean registerClient(Client client) throws SQLException {
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connector.getClientRegistrationPreparedStatement();
            preparedStatement.setString(1, client.getLogin());
            preparedStatement.setString(2, MD5.md5Hash(client.getPassword()));
            preparedStatement.setString(3, client.getFirstName());
            preparedStatement.setString(4, client.getLastName());
            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            connector.closePreparedStatement(preparedStatement);
        }
        return false;
    }

    public Boolean registerTaxi(Taxi taxi) throws SQLException {
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connector.getTaxiRegistrationPreparedStatement();
            preparedStatement.setString(1, taxi.getLogin());
            preparedStatement.setString(2, MD5.md5Hash(taxi.getPassword()));
            preparedStatement.setString(3, taxi.getFirstName());
            preparedStatement.setString(4, taxi.getLastName());
            preparedStatement.setString(5, taxi.getCar().getNumber());
            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            connector.closePreparedStatement(preparedStatement);
        }
        return false;
    }

    public Boolean isLoginFree(String login){
        Statement statement = null;
        try{
            statement = connector.getStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_LOGIN_ALL);
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(login)){
                    return false;
                }
            }
            return true;
        }catch (SQLException e) {
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            connector.closeStatement(statement);
        }
        return false;
    }

    public void changeAvailableStatus(Taxi taxi) throws SQLException {
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connector.changeAvailableStatusPreparedStatement();
            preparedStatement.setBoolean(1, !taxi.isAvailableStatus());
            preparedStatement.setInt(2, taxi.getId());
            preparedStatement.execute();
        }catch (SQLException e){
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            connector.closePreparedStatement(preparedStatement);
        }
    }
}
