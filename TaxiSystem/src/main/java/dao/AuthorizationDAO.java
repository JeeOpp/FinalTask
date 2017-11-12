package dao;

/**
 * Created by DNAPC on 12.11.2017.
 */
public interface AuthorizationDAO {
    Boolean registration(String ... args);
    String authentication(String ... args);
}
