package service;

import dao.AuthorizationDAO;
import dao.DAOFactory;
import dao.RegistrationDAO;
import dao.impl.SignDAO;
import entity.Client;
import entity.User;

import java.sql.SQLException;

/**
 * Created by DNAPC on 21.12.2017.
 */
public class SignService {
    public Boolean registerClient(User user) throws SQLException {
        Client client = (Client) user;
        DAOFactory daoFactory = DAOFactory.getInstance();
        SignDAO signDAO = daoFactory.getSignDAO();
        if (signDAO.isLoginFree(client.getLogin())) {
            return signDAO.registerClient(client);
        } else {
            return false;

        }
    }
    public User authorize(String login, String password) throws SQLException{
        User user = null;
        DAOFactory daoFactory = DAOFactory.getInstance();
        SignDAO signDAO = daoFactory.getSignDAO();
        String role = signDAO.preAuthorize(login, password);
        if ("client".equals(role)){
            user = signDAO.clientAuthorize(login, password);
        }
        if("taxi".equals(role)){
            user = signDAO.taxiAuthorize(login, password);
        }
        if("admin".equals(role)){
            user = new User("admin");
        }
        return user;
    }


    public void logOut(User user) throws SQLException{
        DAOFactory daoFactory = DAOFactory.getInstance();
        SignDAO signDAO = daoFactory.getSignDAO();
        try {
            signDAO.logOut(user);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
