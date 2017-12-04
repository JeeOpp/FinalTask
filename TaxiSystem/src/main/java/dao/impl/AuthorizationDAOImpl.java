package dao.impl;

import dao.AuthorizationDAO;
import dao.WrappedConnector;
import entity.Client;
import entity.User;
import service.MD5;

import javax.jws.soap.SOAPBinding;
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
    public User authorize(User user) throws SQLException {
        ResultSet resultSet;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connector.getAuthenticationPreparedStatement();
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, MD5.md5Hash(user.getPassword()));
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, MD5.md5Hash(user.getPassword()));
            resultSet = preparedStatement.executeQuery();

            user = null;

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String password = resultSet.getString(3);
                String name = resultSet.getString(4);
                String surname = resultSet.getString(5);
                boolean banStatus = resultSet.getBoolean(6);
                String role = resultSet.getString(7);
                user = new User(id, login, password, name, surname, banStatus, role);
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception (request or table failed): " + ex);
        } finally {
            connector.closePreparedStatement(preparedStatement);
        }
        return user;
    }


}