package dao;

import java.sql.*;
import java.util.MissingResourceException;

/**
 * Created by DNAPC on 13.11.2017.
 */
public class WrappedConnector {
    //private static final Logger log = Logger.getLogger(WrapperConnector.class.getClass());

    private Connection connection;

    public WrappedConnector() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finaltask", "root", "root");
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
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `finaltask`.`users` (`login`, `password`) VALUES (?,?);");
            if (preparedStatement != null) {
                return preparedStatement;
            }
        }
        throw new SQLException("connection or PreparedStatement is null");
    }

    public PreparedStatement getAuthenticationPreparedStatement() throws SQLException{
        if (connection != null) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT role, status FROM users WHERE login=? and password=?;");
            if (preparedStatement != null) {
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
