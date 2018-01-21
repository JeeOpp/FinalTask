package service;

import dao.DAOFactory;
import dao.UserManagerDAO;
import entity.Client;
import entity.Taxi;
import entity.User;
import org.apache.log4j.Logger;
import support.MD5;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DNAPC on 16.12.2017.
 */
public class UserManagerService {
    private final static Logger log = Logger.getLogger(UserManagerService.class.getClass());
    public Boolean changePassword(User user, String currentPassword, String newPassword) {
        if (user.getPassword().equals(MD5.md5Hash(currentPassword))) {
            user.setPassword(MD5.md5Hash(newPassword));
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
            userManagerDAO.changePassword(user);
            return true;
        }
        return false;
    }

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

    public void changeBanStatus(User user) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
        userManagerDAO.changeBanStatus(user);
    }

    public void changeBonusCount(Client client) {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
            userManagerDAO.changeBonusCount(client);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    public void changeTaxiCar(Taxi taxi) {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
            userManagerDAO.changeTaxiCar(taxi);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    public String getHashPassword(String mail) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
        return userManagerDAO.getHashPassword(mail);
    }

    public boolean restorePassword(String mail, String hashPassword,String newPassword) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
        return userManagerDAO.restorePassword(mail, hashPassword, newPassword);
    }
}
