package service.impl;

import dao.AuthorizationDAO;
import dao.DAOFactory;
import dao.RegistrationDAO;
import dao.impl.AuthorizationDAOImpl;
import dao.impl.RegistrationDAOImpl;
import service.MD5;
import service.RegistrationService;

import java.sql.SQLException;

/**
 * Created by DNAPC on 16.11.2017.
 */
public class RegistrationServiceImpl implements RegistrationService {
    @Override
    public Boolean register(String login, String password) throws SQLException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        RegistrationDAO registrationDAO = daoFactory.getRegistrationDAO();
        if (((RegistrationDAOImpl)registrationDAO).isLoginFree(login)) {
            return registrationDAO.register(login, MD5.md5Hash(password));
        }
        else{
            return false;
        }
    }
}
