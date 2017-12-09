package service;

import dao.AuthorizationDAO;
import dao.DAOFactory;
import entity.User;

import java.sql.SQLException;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class AuthorizationService {

    public User authorize(String login, String password) throws SQLException{
        User user = null;
        DAOFactory daoFactory = DAOFactory.getInstance();
        AuthorizationDAO authorizationDAO = daoFactory.getAuthorizationDAO();
        String role = authorizationDAO.preAuthorize(login, password);
        if ("client".equals(role)){
             user = authorizationDAO.clientAuthorize(login, password);
        }
        if("taxi".equals(role)){
            user = authorizationDAO.taxiAuthorize(login, password);
        }
        if("admin".equals(role)){
            user = new User("admin");
        }
        return user;
    }


    public void logOut(User user) throws SQLException{
        DAOFactory daoFactory = DAOFactory.getInstance();
        AuthorizationDAO authorizationDAO = daoFactory.getAuthorizationDAO();
        try {
            authorizationDAO.logOut(user);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
