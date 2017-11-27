package dao;

import entity.Client;

import java.sql.SQLException;

/**
 * Created by DNAPC on 16.11.2017.
 */
public interface RegistrationDAO {
    Boolean registerClient(Client client) throws SQLException;
}
