package service.impl;

import dao.DAOFactory;
import dao.SignDAO;
import entity.Client;
import entity.Taxi;
import entity.User;
import org.apache.log4j.Logger;
import service.SignService;

import java.sql.SQLException;

import static support.constants.UserConstants.ADMIN;
import static support.constants.UserConstants.CLIENT;
import static support.constants.UserConstants.TAXI;

/**
 *  Delegates action with database to the {@link SignDAO}
 */
public class SignServiceImpl implements SignService{
    private final static Logger log = Logger.getLogger(SignServiceImpl.class.getClass());

    /**
     * Delegates to the DAO layer the writing of users to the database (only if transferred data passes the validation).
     * @param client we are want to write.
     * @return true if writing is success, otherwise false.
     */
    @Override
    public boolean registerClient(Client client) {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            SignDAO signDAO = daoFactory.getSignDAO();
            if (signDAO.isLoginFree(client.getLogin()) && signDAO.isMailFree(client.getMail())) {
                return signDAO.registerClient(client);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

    /**
     * Delegates to the DAO layer the writing of users to the database (only if transferred data passes the validation).
     *
     * @param taxi we are want to write/
     * @return true if writing is success, otherwise false.
     */
    @Override
    public boolean registerTaxi(Taxi taxi) {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            SignDAO signDAO = daoFactory.getSignDAO();
            if (signDAO.isLoginFree(taxi.getLogin())) {
                return signDAO.registerTaxi(taxi);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

    /**
     * Authorizes user (client, taxi, admin). Delegates to the DAO layer the selecting the authorized user role.
     * @param login of user we want to authorize.
     * @param password of user we want to authorize.
     * @return role of the user if authorization is successful.
     */
    @Override
    public User authorize(String login, String password) {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            SignDAO signDAO = daoFactory.getSignDAO();
            String role = signDAO.preAuthorize(login, password);
            if (CLIENT.equals(role)) {
                return signDAO.clientAuthorize(login, password);
            }
            if (TAXI.equals(role)) {
                return signDAO.taxiAuthorize(login, password);
            }
            if (ADMIN.equals(role)) {
                return new User.UserBuilder().setRole(ADMIN).build();
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    /**
     * Changes the availability status of the taxi driver.
     *
     * @param taxi we want to change.
     */
    @Override
    public void changeSessionStatus(Taxi taxi){
        DAOFactory daoFactory = DAOFactory.getInstance();
        SignDAO signDAO = daoFactory.getSignDAO();
        try {
            signDAO.changeAvailableStatus(taxi);
        }catch (SQLException ex){
            log.error(ex.getMessage());
        }
    }
}
