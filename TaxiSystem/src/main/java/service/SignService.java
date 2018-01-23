package service;

import dao.DAOFactory;
import dao.SignDAO;
import entity.Client;
import entity.Taxi;
import entity.User;
import entity.entityEnum.UserEnum;
import org.apache.log4j.Logger;

import java.sql.SQLException;

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
        if (UserEnum.CLIENT.getValue().equals(role)){
            return signDAO.clientAuthorize(login, password);
        }
        if(UserEnum.TAXI.getValue().equals(role)){
            return signDAO.taxiAuthorize(login, password);
        }
        if(UserEnum.ADMIN.getValue().equals(role)){
            return new User(UserEnum.ADMIN.getValue());
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
