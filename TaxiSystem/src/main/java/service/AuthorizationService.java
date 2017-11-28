package service;

import dao.AuthorizationDAO;
import dao.DAOFactory;
import entity.Client;
import entity.User;
import service.MD5;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class AuthorizationService {

    public User authorize(User user) throws SQLException{
        DAOFactory daoFactory = DAOFactory.getInstance();
        AuthorizationDAO authorizationDAO = daoFactory.getAuthorizationDAO();
        return authorizationDAO.authorize(user);
    }
}
