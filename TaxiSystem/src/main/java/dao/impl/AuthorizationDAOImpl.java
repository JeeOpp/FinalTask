package dao.impl;

import dao.AuthorizationDAO;
import dao.WrappedConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class AuthorizationDAOImpl implements AuthorizationDAO {
    public static final String SQL_SELECT_LOGIN_USERS = "SELECT login FROM users";

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
    public Boolean register(String... args) throws SQLException {
        PreparedStatement preparedStatement=null;
        String login = args[0];
        String password = args[1];
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

    @Override
    public String authenticate(String... args) throws SQLException {

        return "admin";
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



/*
public List<User> readAll() {
        List<User> users = new ArrayList<>();
        Statement st = null;
        try {
            st = connector.getStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPass(resultSet.getString(3));
                users.add(user);
            }
        }catch (SQLException e) {
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            connector.closeStatement(st);
        }
        return users;
    }
    public void writeAll(){
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connector.getPreparedStatement();
            for (int i = 4; i < 10; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, String.valueOf(i));
                preparedStatement.setString(3, "p" + String.valueOf(i));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }catch (SQLException e){
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            connector.closePreparedStatement(preparedStatement);
        }
    }
 */