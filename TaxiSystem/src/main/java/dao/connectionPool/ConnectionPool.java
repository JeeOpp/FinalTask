package dao.connectionPool;

import java.sql.*;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

/**
 * Created by DNAPC on 26.12.2017.
 */
public class ConnectionPool {
    private BlockingQueue <Connection> connectionQueue;
    private BlockingQueue <Connection> giveAwayConQueue;

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private ConnectionPool(){
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER.getValue());
        this.url = dbResourceManager.getValue(DBParameter.DB_URL.getValue());
        this.user = dbResourceManager.getValue(DBParameter.DB_USER.getValue());
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD.getValue());
        this.poolSize = Integer.parseInt(DBParameter.DB_POOL_SIZE.getValue());
    }

    public void initPoolData() throws ConnectionPoolException{
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName(driverName);
            giveAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
            connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
            for(int i=0;i<poolSize;i++){
                Connection connection = DriverManager.getConnection(url,user,password);
                WrappedConnection wrappedConnection = new WrappedConnection(connection);
                connectionQueue.add(wrappedConnection);
            }
        }catch (SQLException ex){
            throw new ConnectionPoolException("SQLException in pool", ex);
        }catch (ClassNotFoundException ex){
            throw new ConnectionPoolException("no driver", ex);
        }
    }
    public void dispose(){
        clearConnectionQueue();
    }
    private void clearConnectionQueue(){
        try{
            closeConnectionQueue(giveAwayConQueue);
            closeConnectionQueue(connectionQueue);
        }catch (SQLException ex){
            ;;;
        }
    }
    public Connection takeConnection() throws ConnectionPoolException{
        Connection connection = null;
        try{
            connection = connectionQueue.take();
            giveAwayConQueue.add(connection);
        }catch (InterruptedException ex){
            throw new ConnectionPoolException("error to the data source", ex);
        }
        return connection;
    }

    public void closeConnection(Connection con, Statement st, ResultSet rs){
        try {
            con.close();
        }catch (SQLException ex) {
            ;
        }
        try {
            rs.close();
        }catch (SQLException ex){

        }
        try {
            st.close();
        }catch (SQLException ex){

        }
    }
    public void closeConnection(Connection con, Statement st) {
        try {
            con.close();
        } catch (SQLException ex) {
            ;
        }
        try {
            st.close();
        } catch (SQLException ex) {
            ;
        }
    }
    private void closeConnectionQueue(BlockingQueue<Connection> queue) throws SQLException{
        Connection connection;
        while ((connection = queue.poll()) != null){
            if(!connection.getAutoCommit()){
                connection.commit();
            }
            ((WrappedConnection) connection).closeConnection();
        }
    }

    public class WrappedConnection implements Connection {
        private Connection connection;

        public WrappedConnection(Connection connection) throws SQLException {
            this.connection = connection;
            this.connection.setAutoCommit(true);
        }

        public PreparedStatement getClientRegistrationPreparedStatement() throws SQLException {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO client (login, password, name, surname) VALUES (?,?,?,?);");
                if (preparedStatement != null) {
                    return preparedStatement;
                }
            }
            throw new SQLException("connection or PreparedStatement is null");
        }
        public PreparedStatement getTaxiRegistrationPreparedStatement() throws SQLException {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO taxi (login, password, name, surname,carNumber) VALUES (?,?,?,?,?);");
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

        public PreparedStatement getClientBanPreparedStatement() throws SQLException{
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE client SET banStatus = ? WHERE id = ?;");
                if (preparedStatement != null) {
                    return preparedStatement;
                }
            }
            throw new SQLException("connection or PreparedStatement is null");
        }

        public PreparedStatement getTaxiBanPreparedStatement() throws SQLException{
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE taxi SET banStatus = ? WHERE id = ?;");
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
        public PreparedStatement changeBonusCount() throws SQLException{
            if(connection!=null){
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE client SET bonusPoints = bonusPoints + ?  WHERE id = ?;");
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
        public PreparedStatement payOrder() throws SQLException{
            if(connection!=null){
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE taxisystem.`order` SET orderStatus='completed' WHERE order_id=?;");
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

        public PreparedStatement getChangeClientPassPreparedStatement() throws SQLException{
            if(connection!=null){
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE client " +
                        "SET password = ? "+
                        "WHERE id=?;");
                if (preparedStatement!=null){
                    return preparedStatement;
                }
            }
            throw new SQLException("connection or PreparedStatement is null");
        }
        public PreparedStatement getChangeTaxiPassPreparedStatement() throws SQLException{
            if(connection!=null){
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE taxi " +
                        "SET password = ? "+
                        "WHERE id=?;");
                if (preparedStatement!=null){
                    return preparedStatement;
                }
            }
            throw new SQLException("connection or PreparedStatement is null");
        }

        public PreparedStatement changeAvailableStatusPreparedStatement() throws SQLException{
            if(connection!=null){
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE taxi SET availableStatus = ?  WHERE id = ?;");
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
        public void closeConnection() throws SQLException {
            connection.close();
        }

        @Override
        public Statement createStatement() throws SQLException {
            return  connection.createStatement();
        }
        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return  connection.prepareStatement(sql);
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
            if (connection.isClosed()){
                throw new SQLException("already closed");
            }
            if (connection.isReadOnly()){
                connection.setReadOnly(false);
            }
            if(!giveAwayConQueue.remove(this)){
                throw new SQLException("error");
            }
            if(!connectionQueue.offer(this)){
                throw new SQLException("error");
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
            return connection.createStruct(typeName,attributes);
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
            connection.setNetworkTimeout(executor,milliseconds);
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

