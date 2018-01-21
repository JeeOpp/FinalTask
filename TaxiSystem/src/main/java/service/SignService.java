package service;

import dao.DAOFactory;
import dao.SignDAO;
import entity.Client;
import entity.Taxi;
import entity.User;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by DNAPC on 21.12.2017.
 */
public class SignService {
    private final static Logger log = Logger.getLogger(SignService.class.getClass());
    public Boolean registerClient(Client client) throws SQLException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        SignDAO signDAO = daoFactory.getSignDAO();
        if (signDAO.isLoginFree(client.getLogin()) && signDAO.isMailFree(client.getMail())) {
            return signDAO.registerClient(client);
        } else {
            return false;

        }
    }
    public Boolean registerTaxi(Taxi taxi) throws SQLException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        SignDAO signDAO = daoFactory.getSignDAO();
        if (signDAO.isLoginFree(taxi.getLogin())) {
            return signDAO.registerTaxi(taxi);
        } else {
            return false;
        }
    }

    public User authorize(String login, String password) throws SQLException{
        DAOFactory daoFactory = DAOFactory.getInstance();
        SignDAO signDAO = daoFactory.getSignDAO();
        String role = signDAO.preAuthorize(login, password);
        if ("client".equals(role)){
            return signDAO.clientAuthorize(login, password);
        }
        if("taxi".equals(role)){
            return signDAO.taxiAuthorize(login, password);
        }
        if("admin".equals(role)){
            return new User("admin");
        }
        return null;
    }
    public void changeSessionStatus(Taxi taxi) throws SQLException{
        DAOFactory daoFactory = DAOFactory.getInstance();
        SignDAO signDAO = daoFactory.getSignDAO();
        try {
            signDAO.changeAvailableStatus(taxi);
        }catch (SQLException ex){
            log.error(ex.getMessage());
        }
    }
}
