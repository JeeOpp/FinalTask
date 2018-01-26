package service.impl;

import dao.DAOFactory;
import dao.TaxisDAO;
import entity.Car;
import org.apache.log4j.Logger;
import service.TaxisService;

import java.sql.SQLException;
import java.util.List;

/**
 *  Delegates action with database to the {@link TaxisDAO}
 */
public class TaxisServiceImpl implements TaxisService{
    private final static Logger log = Logger.getLogger(TaxisServiceImpl.class.getClass());

    /**
     * Delegates to the DAO layer selecting information about all cars.
     *
     * @return list of all cars.
     */
    @Override
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

    /**
     * Delegates adding of a new car to the DAO layer.
     *
     * @param car we want to add.
     * @return true if car successfully added.
     */
    @Override
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

    /**
     * Delegates removing of a chosen car to the DAO layer.
     *
     * @param car we want to remove.
     * @return true if car successfully removed.
     */
    @Override
    public boolean removeCar(Car car) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        TaxisDAO taxisDAO = daoFactory.getTaxisDAO();
        return taxisDAO.removeCar(car);
    }
}
