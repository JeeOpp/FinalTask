package dao.impl;

import dao.AuthorizationDAO;
import dao.WrappedConnector;
import entity.Client;
import service.MD5;

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
    public Client authorize(Client client) throws SQLException {
        ResultSet resultSet = null;
        Client detected = null;
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connector.getAuthenticationPreparedStatement();
            preparedStatement.setString(1, client.getLogin());
            preparedStatement.setString(2, MD5.md5Hash(client.getPassword()));
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String password = resultSet.getString(3);
                String name = resultSet.getString(4);
                String surname = resultSet.getString(5);
                int bonusPoints = resultSet.getInt(6);
                Boolean banStatus  = resultSet.getBoolean(7);
                detected = new Client(id,login,password,name,surname,bonusPoints,banStatus);
            }
        }catch (SQLException e){
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            connector.closePreparedStatement(preparedStatement);
        }
        return detected;
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