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
    public Map<String, Boolean> authorize(String login, String password) throws SQLException{
        DAOFactory daoFactory = DAOFactory.getInstance();
        AuthorizationDAO authorizationDAO = daoFactory.getAuthorizationDAO();
        return authorizationDAO.authorize(login, MD5.md5Hash(password));
    }
}
