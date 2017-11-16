package dao.impl;

import dao.AuthorizationDAO;
import dao.WrappedConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class AuthorizationDAOImpl implements AuthorizationDAO {

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
    public Map<String, Boolean> authorize(String login, String password) throws SQLException {
        ResultSet resultSet = null;
        Map<String, Boolean> resultMap = new HashMap<>();;
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connector.getAuthenticationPreparedStatement();
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String role = resultSet.getString(1);
                Boolean status = resultSet.getBoolean(2);
                resultMap.put(role, status);
            }
        }catch (SQLException e){
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            connector.closePreparedStatement(preparedStatement);
        }
        return resultMap;
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