package dao.impl;

import dao.RegistrationDAO;
import dao.WrappedConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by DNAPC on 16.11.2017.
 */
public class RegistrationDAOImpl implements RegistrationDAO {

    private static final String SQL_SELECT_LOGIN_USERS = "SELECT login FROM users";

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
    public Boolean register(String login, String password) throws SQLException {
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connector.getRegistrationPreparedStatement();
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
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
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_LOGIN_USERS);
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
