package service;

import dao.DAOFactory;
import dao.impl.DispatcherDAO;
import dao.impl.TaxisDAO;
import entity.Car;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by DNAPC on 03.01.2018.
 */
public class TaxisService {
    public List<Car> getCarList() {
        List<Car> carList = null;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            TaxisDAO taxisDAO = daoFactory.getTaxisDAO();
            carList = taxisDAO.getCarList();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return carList;
    }
    public boolean addCar(Car car) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        TaxisDAO taxisDAO = daoFactory.getTaxisDAO();
        try {
            List<Car> carList = taxisDAO.getCarList();
            for (Car each : carList) {
                if (each.getNumber().equals(car.getNumber())) {
                    return false;
                }
            }
            return taxisDAO.addCar(car);
        } catch (SQLException ex) {
            ///;
        }
        return false;
    }
    public boolean removeCar(Car car) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        TaxisDAO taxisDAO = daoFactory.getTaxisDAO();
        return taxisDAO.removeCar(car);
    }
}
