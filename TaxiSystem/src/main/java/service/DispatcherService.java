package service;

import dao.DAOFactory;
import dao.impl.DispatcherDAO;
import entity.Taxi;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by DNAPC on 06.12.2017.
 */
public class DispatcherService {
    public List<Taxi> getAvailableTaxiList(){
        List<Taxi> availableTaxiList=null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
            availableTaxiList = dispatcherDAO.getAvailableTaxiList();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return availableTaxiList;
    }
}
