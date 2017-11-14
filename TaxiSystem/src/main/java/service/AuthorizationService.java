package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by DNAPC on 12.11.2017.
 */
public interface AuthorizationService {
    Boolean register(String login, String password) throws SQLException;
    Map<String, Boolean> authenticate(String login, String password) throws SQLException;
}
