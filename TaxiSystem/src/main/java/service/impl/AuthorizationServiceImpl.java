package service.impl;

import dao.AuthorizationDAO;
import dao.DAOFactory;
import dao.impl.AuthorizationDAOImpl;
import service.AuthorizationService;
import service.MD5;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class AuthorizationServiceImpl implements AuthorizationService {
    @Override
    public Boolean register(String login, String password) throws SQLException{
        DAOFactory daoFactory = DAOFactory.getInstance();
        AuthorizationDAO authorizationDAO = daoFactory.getAuthorizationDAO();
        if (((AuthorizationDAOImpl)authorizationDAO).isLoginFree(login)) {
            return authorizationDAO.register(login, MD5.md5Hash(password));
        }
        else{
            return false;
        }
    }

    @Override
    public Map<String, Boolean> authenticate(String login, String password) throws SQLException{
        DAOFactory daoFactory = DAOFactory.getInstance();
        AuthorizationDAO authorizationDAO = daoFactory.getAuthorizationDAO();
        return authorizationDAO.authenticate(login, MD5.md5Hash(password));
    }
}
