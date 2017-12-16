package dao;

import entity.Order;

import java.sql.*;
import java.util.MissingResourceException;
import java.util.Properties;

/**
 * Created by DNAPC on 13.11.2017.
 */
public class WrappedConnector {
    //private static final Logger log = Logger.getLogger(WrapperConnector.class.getClass());

    private Connection connection;

    public WrappedConnector() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Properties properties=new Properties();
            properties.setProperty("user","root");
            properties.setProperty("password","root");
            properties.setProperty("useUnicode","true");
            properties.setProperty("characterEncoding","UTF-8");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taxisystem", properties);
        } catch (MissingResourceException e) {
            //log.error("properties file is missing " + e);
            e.printStackTrace();
        } catch (SQLException e) {
            //log.error("not obtained connection " + e);
            e.printStackTrace();
        }
    }

    public PreparedStatement getRegistrationPreparedStatement() throws SQLException {
        if (connection != null) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO client (login, password, name, surname) VALUES (?,?,?,?);");
            if (preparedStatement != null) {
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }
    public PreparedStatement getAuthRolePreparedStatement() throws SQLException{
        if (connection != null) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT role  FROM client WHERE login=? AND password=? UNION SELECT role  FROM taxi WHERE login=? AND password=?");
            if (preparedStatement != null) {
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }
    public PreparedStatement getAuthClientPreparedStatement() throws SQLException{
        if (connection != null) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT *  FROM client WHERE login=? AND password=?");
            if (preparedStatement != null) {
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }
    public PreparedStatement getAuthTaxiPreparedStatement() throws SQLException{
        if (connection != null) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, login, password, name, surname, availableStatus, banStatus,role,number,car,colour  FROM taxi JOIN car ON taxi.carNumber=car.number WHERE login=? AND password=?;");
            if (preparedStatement != null) {
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }
    public PreparedStatement makeOrderPreparedStatement() throws SQLException{
        if(connection!=null){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO taxisystem.order (client_id, taxi_id,source_coord, destiny_coord, price) VALUES (?,?,?,?,?);");
            if (preparedStatement!=null){
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }
    public PreparedStatement decreaseBonus() throws SQLException{
        if(connection!=null){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE client SET bonusPoints = bonusPoints - ?  WHERE id = ?;");
            if (preparedStatement!=null){
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }
    public PreparedStatement cancelOrder() throws SQLException{
        if(connection!=null){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM taxisystem.order WHERE order_id = ?;");
            if (preparedStatement!=null){
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }
    public PreparedStatement acceptOrder() throws SQLException{
        if(connection!=null){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE taxisystem.`order` SET orderStatus='accepted' WHERE order_id=?;");
            if (preparedStatement!=null){
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }
    public PreparedStatement rejectOrder() throws SQLException{
        if(connection!=null){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE taxisystem.`order` SET orderStatus='rejected' WHERE order_id=?;");
            if (preparedStatement!=null){
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }
    public PreparedStatement setReviewPreparedStatement() throws SQLException{
        if(connection!=null){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO taxisystem.review (client_id, taxi_id, comment) VALUES (?, ?, ?);");
            if (preparedStatement!=null){
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }
    public PreparedStatement getReviewClientPreparedStatement() throws SQLException{
        if(connection!=null){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT taxi.id, taxi.name, taxi.surname, review.`comment` FROM review \n" +
                    "\tJOIN taxi ON review.taxi_id = taxi.id \n" +
                    "\tWHERE review.client_id = ?; ");
            if (preparedStatement!=null){
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }
    public PreparedStatement getReviewTaxiPreparedStatement() throws SQLException{
        if(connection!=null){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT client.id, client.name, client.surname, review.`comment` FROM review\n" +
                    "        JOIN client ON review.client_id = client.id\n" +
                    "        WHERE review.taxi_id = ?;");
            if (preparedStatement!=null){
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }


    public Statement getStatement() throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            if (statement != null) {
                return statement;
            }
        }
        throw new SQLException("connection or statement is null");
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("statement is null " + e);
            }
        }
    }
    public void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                //log.error("Prepared statement is null " + e);
                e.printStackTrace();
            }
        }
    }
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                //log.error(" wrong connection" + e);
                e.printStackTrace();
            }
        }
    }
}
