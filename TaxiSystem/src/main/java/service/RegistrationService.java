package service;

import java.sql.SQLException;

/**
 * Created by DNAPC on 16.11.2017.
 */
public interface RegistrationService {
    Boolean register(String login, String password) throws SQLException;
}
