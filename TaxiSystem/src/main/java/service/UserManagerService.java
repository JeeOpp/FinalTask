package service;

import dao.DAOFactory;
import dao.UserManagerDAO;
import entity.Client;
import entity.Taxi;
import entity.User;
import support.MD5;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DNAPC on 16.12.2017.
 */
public class UserManagerService {
    public Boolean changePassword(User user, String currentPassword, String newPassword){
        if (user.getPassword().equals(MD5.md5Hash(currentPassword))){
            user.setPassword(MD5.md5Hash(newPassword));
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
            userManagerDAO.changePassword(user);
            return true;
        }
        return false;
    }
    public List<Client> getClientList(){
        List<Client> clientList = null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
            clientList = userManagerDAO.getClientList();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return clientList;
    }
    public List<Taxi> getTaxiList(){
        List<Taxi> taxiList = null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
            taxiList = userManagerDAO.getTaxiList();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return taxiList;
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
    public void changeBanStatus(User user){
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
            ex.printStackTrace();
        }
    }
    public void changeTaxiCar(Taxi taxi){
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
            userManagerDAO.changeTaxiCar(taxi);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
