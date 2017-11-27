package dao;

import entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by DNAPC on 12.11.2017.
 */
public interface AuthorizationDAO {
    Client authorize(Client client) throws SQLException;
}
