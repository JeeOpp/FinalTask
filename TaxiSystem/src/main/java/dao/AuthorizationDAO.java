package dao;

import entity.Client;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by DNAPC on 12.11.2017.
 */
public interface AuthorizationDAO {
    User authorize(User user) throws SQLException;
}
