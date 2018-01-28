package dao.connectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public interface IConnectionPool {
    String EX_IN_POOL_MESSAGE = "SQLException in pool";
    String NO_DRIVER_MASSAGE = "no driver";
    String ERROR_TO_DATA_MESSAGE = "error to the data source";
    String CLOSED_MESSAGE = "Already closed";
    String OFFER_MESSAGE = "offer connection from queue error";
    String REMOVE_MESSAGE = "remove connection from queue error";

    void dispose();
    Connection takeConnection() throws ConnectionPoolException;
    void closeConnection(Connection con, Statement st, ResultSet rs);
    void closeConnection(Connection con, Statement st);
    void closeConnection(Connection con, PreparedStatement st, ResultSet rs);
    void closeConnection(Connection con, PreparedStatement st);
}
