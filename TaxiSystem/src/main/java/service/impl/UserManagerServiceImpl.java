package service.impl;

import dao.DAOFactory;
import dao.UserManagerDAO;
import entity.Client;
import entity.Taxi;
import entity.User;
import org.apache.log4j.Logger;
import service.UserManagerService;
import support.MD5;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 *  Delegates action with database to the {@link UserManagerDAO}
 */
public class UserManagerServiceImpl implements UserManagerService {
    private final static Logger log = Logger.getLogger(UserManagerServiceImpl.class.getClass());

    /**
     * Changes a password specific user.
     *
     * @param user whose we want to change a pass.
     * @param currentPassword current password of user's account.
     * @param newPassword new password of specific user.
     * @return true is changing password is successfully completed, otherwise false.
     */
    @Override
    public boolean changePassword(User user, String currentPassword, String newPassword) {
        if (user.getPassword().equals(MD5.md5Hash(currentPassword))) {
            user.setPassword(MD5.md5Hash(newPassword));
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
            userManagerDAO.changePassword(user);
            return true;
        }
        return false;
    }

    /**
     * Selects all information about client.
     * @return client list (if there are no clients in database, returns null).
     */
    @Override
    public List<Client> getClientList() {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
            return userManagerDAO.getClientList();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    /**
     * Selects all information about taxi drivers.
     *
     * @return taxi drivers list (if there are no taxi drivers in database, returns null).
     */
    @Override
    public List<Taxi> getTaxiList() {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
            return userManagerDAO.getTaxiList();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    /**
     * Selects all information about taxi drivers available at the moment .
     *
     * @return available taxi drivers list.
     */
    @Override
    public List<Taxi> getAvailableTaxiList() {
        List<Taxi> taxiList = getTaxiList();
        Iterator<Taxi> taxiIterator = taxiList.listIterator();
        while (taxiIterator.hasNext()) {
            if (!taxiIterator.next().isAvailableStatus()) {
                taxiIterator.remove();
            }
        }
        return taxiList;
    }

    /**
     * Changes ban status.
     *
     * @param user we want to ban/unban.
     */
    @Override
    public void changeBanStatus(User user) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
        userManagerDAO.changeBanStatus(user);
    }

    /**
     * changes amount of bonuses specific client.
     *
     * @param client whose amount of bonuses we want to change.
     */
    @Override
    public void changeBonusCount(Client client) {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
            userManagerDAO.changeBonusCount(client);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Changes taxi driver's car.
     *
     * @param taxi whose car we want change.
     */
    @Override
    public void changeTaxiCar(Taxi taxi) {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
            userManagerDAO.changeTaxiCar(taxi);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * receives hash password of user according with e-mail.
     *
     * @param mail of client whose hash password we want to receive.
     * @return hash password.
     */
    @Override
    public String getHashPassword(String mail) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
        return userManagerDAO.getHashPassword(mail);
    }

    /**
     * Changes user password if hash password and mail of specific user are correct.
     * @param mail of client we want to restore the password.
     * @param hashPassword of client we want to restore the password.
     * @param newPassword we want to set instead of old one.
     * @return true if action is successfully, otherwise false.
     */
    @Override
    public boolean restorePassword(String mail, String hashPassword,String newPassword) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
        return userManagerDAO.restorePassword(mail, hashPassword, newPassword);
    }
}
