package dao.impl;

import dao.RegistrationDAO;
import dao.WrappedConnector;
import entity.Client;
import support.MD5;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by DNAPC on 16.11.2017.
 */
public class RegistrationDAOImpl implements RegistrationDAO {

    private static final String SQL_SELECT_LOGIN_ALL = "SELECT client.login FROM client UNION SELECT taxi.login FROM taxi;";

    private WrappedConnector connector;

    public RegistrationDAOImpl() {
        this.connector = new WrappedConnector();
    }

    public RegistrationDAOImpl(WrappedConnector connector) {
        this.connector = connector;
    }

    public void close() {
        connector.closeConnection();
    }

    @Override
    public Boolean registerClient(Client client) throws SQLException {
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connector.getRegistrationPreparedStatement();
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
}
