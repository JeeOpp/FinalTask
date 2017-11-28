package service;

import dao.DAOFactory;
import dao.RegistrationDAO;
import dao.impl.RegistrationDAOImpl;
import entity.Client;
import entity.User;

import java.sql.SQLException;

/**
 * Created by DNAPC on 28.11.2017.
 */
public class RegistrationService {
    public Boolean registerClient(User user) throws SQLException {
        Client client = (Client) user;
        DAOFactory daoFactory = DAOFactory.getInstance();
        RegistrationDAO registrationDAO = daoFactory.getRegistrationDAO();
        if (((RegistrationDAOImpl) registrationDAO).isLoginFree(client.getLogin())) {
            return registrationDAO.registerClient(client);
        } else {
            return false;

        }
    }
}
