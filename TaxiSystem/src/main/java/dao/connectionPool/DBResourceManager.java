package dao.connectionPool;

import java.util.ResourceBundle;

/**
 * Created by DNAPC on 29.12.2017.
 */
public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("connectionPool/db");

    public static DBResourceManager getInstance(){
        return instance;
    }

    public String getValue(String key){
        return resourceBundle.getString(key);
    }
}
