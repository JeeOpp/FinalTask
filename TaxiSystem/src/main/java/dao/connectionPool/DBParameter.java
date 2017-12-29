package dao.connectionPool;

/**
 * Created by DNAPC on 29.12.2017.
 */
public enum DBParameter {
    DB_DRIVER("db.driver"),
    DB_URL("db.url"),
    DB_USER("db.user"),
    DB_PASSWORD("db.password"),
    DB_POOL_SIZE("db.poolsize");

    private String value;

    DBParameter(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }

}
