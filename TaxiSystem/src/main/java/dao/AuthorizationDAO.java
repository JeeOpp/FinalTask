package dao;

import java.sql.SQLException;

/**
 * Created by DNAPC on 12.11.2017.
 */
public interface AuthorizationDAO {
    Boolean register(String ... args) throws SQLException;
    String authenticate(String ... args) throws SQLException;
}
