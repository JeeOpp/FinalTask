package service;

import dao.DAOFactory;
import dao.impl.DispatcherDAO;
import entity.Client;
import entity.Order;
import entity.Taxi;
import support.CoordinateGenerator;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by DNAPC on 06.12.2017.
 */
public class DispatcherService {
    private static final double COEFF_PRICE = 40;

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
    public Order makeOrder(Client client, Taxi taxi,String sourceCoordinate, String destinyCoordinate, double price){
        Order order = new Order();
        order.setClient(client);
        order.setTaxi(taxi);
        order.setSourceCoordinate(sourceCoordinate);
        order.setDestinyCoordinate(destinyCoordinate);
        order.setPrice(price);
        return order;
    }
    public void callConfirm(Order order){
        DAOFactory daoFactory = DAOFactory.getInstance();
        DispatcherDAO dispatcherDAO = daoFactory.getDispatcherDAO();
        try {
            dispatcherDAO.orderConfirm(order);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private double calculatePrice(String sourceCoordinate, String destinyCoordinate){
        String[] sourceLatLng = sourceCoordinate.split(",");
        String[] destinyLatLng = destinyCoordinate.split(",");

        Double sourceLat = Double.parseDouble(sourceLatLng[0]);
        Double sourceLng = Double.parseDouble(sourceLatLng[1]);
        Double destinyLat = Double.parseDouble(destinyLatLng[0]);
        Double destinyLng = Double.parseDouble(destinyLatLng[1]);

        Double distance = Math.sqrt(Math.pow(destinyLat-sourceLat,2)+Math.pow(destinyLng-sourceLng,2));
        return distance*COEFF_PRICE;
    }


}
