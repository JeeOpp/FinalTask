package dao.connectionPool;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

/**
 * Created by DNAPC on 29.01.2018.
 */
public class ConnectionPoolTest {
    @Test
    public void getInstance() throws Exception {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        assertTrue(connectionPool != null);
    }

    @Test
    public void dispose() throws Exception {
        ConnectionPool instance = ConnectionPool.getInstance();
        instance.dispose();
        Queue<Connection> queue = instance.getConnectionQueue();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void takeConnection() throws Exception {
        ConnectionPool instance = ConnectionPool.getInstance();
        Connection connection = instance.takeConnection();
        assertTrue(instance.getGiveAwayConQueue().contains(connection));
    }

    @Test
    public void closeConnection() throws Exception {
        ConnectionPool instance = ConnectionPool.getInstance();
        Connection connection = instance.takeConnection();
        Statement statement = connection.createStatement();
        instance.closeConnection(connection, statement);
        assertTrue(instance.getConnectionQueue().contains(connection));
    }

}