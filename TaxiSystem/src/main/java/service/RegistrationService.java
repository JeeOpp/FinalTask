package service;

import dao.DAOFactory;
import dao.RegistrationDAO;
import dao.impl.RegistrationDAOImpl;
import entity.Client;

import java.sql.SQLException;

/**
 * Created by DNAPC on 28.11.2017.
 */
public class RegistrationService {
    public Boolean registerClient(Client client) throws SQLException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        RegistrationDAO registrationDAO = daoFactory.getRegistrationDAO();
        if (((RegistrationDAOImpl) registrationDAO).isLoginFree(client.getLogin())) {
            return registrationDAO.registerClient(client);
        } else {
            return false;

        }
    }
}
