package dao;

import entity.Client;
import entity.Taxi;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by DNAPC on 12.11.2017.
 */
public interface AuthorizationDAO {
    String  preAuthorize(String login, String password) throws SQLException;
    Client clientAuthorize(String login, String password) throws SQLException;
    Taxi taxiAuthorize(String login, String password) throws SQLException;
    void logOut(User user) throws SQLException;
}
