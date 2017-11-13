package service.impl;

import dao.AuthorizationDAO;
import dao.DAOFactory;
import dao.impl.AuthorizationDAOImpl;
import service.AuthorizationService;

import java.sql.SQLException;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class AuthorizationServiceImpl implements AuthorizationService {
    @Override
    public Boolean register(String... args) throws SQLException{
        DAOFactory daoFactory = DAOFactory.getInstance();
        AuthorizationDAO authorizationDAO = daoFactory.getAuthorizationDAO();
        if (((AuthorizationDAOImpl)authorizationDAO).isLoginFree(args[0])) {
            return authorizationDAO.register(args);
        }
        else{
            return false;
        }
    }

    @Override
    public String authenticate(String... args) throws SQLException{
        DAOFactory daoFactory = DAOFactory.getInstance();
        AuthorizationDAO authorizationDAO = daoFactory.getAuthorizationDAO();
        return authorizationDAO.authenticate(args);
    }
}
