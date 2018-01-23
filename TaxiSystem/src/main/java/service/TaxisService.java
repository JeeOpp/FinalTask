package service;

import dao.DAOFactory;
import dao.TaxisDAO;
import entity.Car;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class TaxisService {
    private final static Logger log = Logger.getLogger(TaxisService.class.getClass());
    public List<Car> getCarList() {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            TaxisDAO taxisDAO = daoFactory.getTaxisDAO();
            return taxisDAO.getCarList();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return null;
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
            log.error(ex.getMessage());
        }
        return false;
    }
    public boolean removeCar(Car car) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        TaxisDAO taxisDAO = daoFactory.getTaxisDAO();
        return taxisDAO.removeCar(car);
    }
}
