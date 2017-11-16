package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by DNAPC on 12.11.2017.
 */
public interface AuthorizationDAO {
    Map<String, Boolean> authorize(String login, String password) throws SQLException;
}
