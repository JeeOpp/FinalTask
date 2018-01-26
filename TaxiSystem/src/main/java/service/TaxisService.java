package service;

import entity.Car;

import java.util.List;

public interface TaxisService {
    /**
     * Delegates to the DAO layer selecting information about all cars.
     *
     * @return list of all cars.
     */
    List<Car> getCarList();

    /**
     * Delegates adding of a new car to the DAO layer.
     *
     * @param car we want to add.
     * @return true if car successfully added.
     */
    boolean addCar(Car car);

    /**
     * Delegates removing of a chosen car to the DAO layer.
     *
     * @param car we want to remove.
     * @return true if car successfully removed.
     */
    boolean removeCar(Car car);
}
