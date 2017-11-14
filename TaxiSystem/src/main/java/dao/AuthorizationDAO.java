package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by DNAPC on 12.11.2017.
 */
public interface AuthorizationDAO {
    Boolean register(String login, String password) throws SQLException;
    Map<String, Boolean> authenticate(String login, String password) throws SQLException;
}
