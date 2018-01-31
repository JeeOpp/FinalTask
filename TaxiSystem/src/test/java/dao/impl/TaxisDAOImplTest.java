package dao.impl;

import dao.DAOFactory;
import dao.TaxisDAO;
import entity.Car;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DNAPC on 31.01.2018.
 */
public class TaxisDAOImplTest {
    private static final DAOFactory daoFactory = DAOFactory.getInstance();
    private static final TaxisDAO taxisDAO = daoFactory.getTaxisDAO();

    @Test
    public void getCarList() throws Exception {
        List<Car> carList = taxisDAO.getCarList();
        assertTrue(carList!=null);
    }

    @Test
    public void addCar() throws Exception {
        Car car = new Car();
        car.setName("test");
        car.setColour("test");
        car.setNumber("test");
        taxisDAO.addCar(car);
        List<Car> carList = taxisDAO.getCarList();
        assertTrue(carList.contains(car));
        taxisDAO.removeCar(car);
    }

    @Test
    public void removeCar() throws Exception {
        Car car = new Car();
        car.setName("test");
        car.setColour("test");
        car.setNumber("test");
        taxisDAO.addCar(car);
        taxisDAO.removeCar(car);
        List<Car> carList = taxisDAO.getCarList();
        assertFalse(carList.contains(car));
    }

}