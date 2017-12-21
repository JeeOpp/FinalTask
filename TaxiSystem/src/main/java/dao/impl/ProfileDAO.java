package dao.impl;

import dao.WrappedConnector;
import entity.User;
import support.MD5;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by DNAPC on 16.12.2017.
 */
public class ProfileDAO {
    private WrappedConnector connector;

    public ProfileDAO() {
        this.connector = new WrappedConnector();
    }

    public ProfileDAO(WrappedConnector connector) {
        this.connector = connector;
    }

    public void close() {
        connector.closeConnection();
    }

    public void changePassword(User user){
        PreparedStatement preparedStatement=null;
        try {
            if(user.getRole().equals("taxi")) {
                preparedStatement = connector.getChangeTaxiPassPreparedStatement();
            }else{
                preparedStatement = connector.getChangeClientPassPreparedStatement();
            }
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
        }catch (SQLException e){
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            connector.closePreparedStatement(preparedStatement);
        }
    }

}
