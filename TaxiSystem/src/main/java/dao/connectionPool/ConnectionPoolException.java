package dao.connectionPool;

/**
 * Created by DNAPC on 29.12.2017.
 */
public class ConnectionPoolException extends Exception {
    private static final long serialVersionUID = 1L;

    public ConnectionPoolException (String message, Exception ex){
        super(message,ex);
    }
}
