package dao.connectionPool;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class ConnectionPool implements IConnectionPool{
    private static final Logger log = Logger.getLogger(ConnectionPool.class.getClass());
    private static final ConnectionPool instance = new ConnectionPool();
    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> giveAwayConQueue;

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER.getValue());
        this.url = dbResourceManager.getValue(DBParameter.DB_URL.getValue());
        this.user = dbResourceManager.getValue(DBParameter.DB_USER.getValue());
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD.getValue());
        this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE.getValue()));
        try {
            initPoolData();
        } catch (ConnectionPoolException ex) {
            log.error(ex.getMessage());
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    private void initPoolData() throws ConnectionPoolException {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName(driverName);
            giveAwayConQueue = new ArrayBlockingQueue<>(poolSize);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                WrappedConnection wrappedConnection = new WrappedConnection(connection);
                connectionQueue.add(wrappedConnection);
            }
        } catch (SQLException ex) {
            throw new ConnectionPoolException(EX_IN_POOL_MESSAGE, ex);
        } catch (ClassNotFoundException ex) {
            throw new ConnectionPoolException(NO_DRIVER_MASSAGE, ex);
        }
    }

    /**
     * dispose connection pool resources
     */
    @Override
    public void dispose() {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionQueue(giveAwayConQueue);
            closeConnectionQueue(connectionQueue);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * take connection from connection pool
     *
     * @return the connection
     * @throws ConnectionPoolException if there are some problems with connection pool.
     */
    @Override
    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connectionQueue.take();
            giveAwayConQueue.add(connection);
        } catch (InterruptedException ex) {
            throw new ConnectionPoolException(ERROR_TO_DATA_MESSAGE, ex);
        }
        return connection;
    }

    /**
     * releases connection resource.
     *
     * @param con connection
     * @param st  statement
     * @param rs  result set
     */
    @Override
    public void closeConnection(Connection con, Statement st, ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        try {
            st.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        try {
            con.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * releases the connection resources
     *
     * @param con connection
     * @param st  statement
     */
    @Override
    public void closeConnection(Connection con, Statement st) {
        try {
            st.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        try {
            con.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * releases connection resource.
     *
     * @param con connection
     * @param st  prepared statement
     * @param rs  result set
     */
    @Override
    public void closeConnection(Connection con, PreparedStatement st, ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        try {
            st.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        try {
            con.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * releases connection resource.
     *
     * @param con connection
     * @param st  prepared statement
     */
    @Override
    public void closeConnection(Connection con, PreparedStatement st) {
        try {
            st.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        try {
            con.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }


    private void closeConnectionQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((WrappedConnection) connection).reallyClose();
        }
    }

    public BlockingQueue<Connection> getConnectionQueue() {
        return connectionQueue;
    }

    public BlockingQueue<Connection> getGiveAwayConQueue() {
        return giveAwayConQueue;
    }

    /**
     * wrapped connection implement Connection interface.
     */
    public class WrappedConnection implements Connection {
        private Connection connection;

        public WrappedConnection(Connection connection) throws SQLException {
            this.connection = connection;
            this.connection.setAutoCommit(true);
        }

        public void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException(CLOSED_MESSAGE);
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            if (!giveAwayConQueue.remove(this)) {
                throw new SQLException(REMOVE_MESSAGE);
            }
            if (!connectionQueue.offer(this)) {
                throw new SQLException(OFFER_MESSAGE);
            }
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }
    }
}

