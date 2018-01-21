package entity;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by DNAPC on 08.12.2017.
 */
public class Order {
    private final static Logger log = Logger.getLogger(Order.class.getClass());
    private int orderId;
    private Client client;
    private Taxi taxi;
    private String orderStatus;
    private String sourceCoordinate;
    private String destinyCoordinate;
    private double price;

    public Order(Client client, Taxi taxi, String sourceCoordinate, String destinyCoordinate, double price) {
        this.client = client;
        this.taxi = taxi;
        this.sourceCoordinate = sourceCoordinate;
        this.destinyCoordinate = destinyCoordinate;
        this.price = price;
    }

    public Order(){
        orderStatus = "processed";
    }

    public int getOrderId() {
        return orderId;
    }

    public Client getClient() {
        return client;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getSourceCoordinate() {
        return sourceCoordinate;
    }

    public String getDestinyCoordinate() {
        return destinyCoordinate;
    }

    public double getPrice() {
        return price;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    private void setSourceCoordinate(String sourceCoordinate) {
        this.sourceCoordinate = sourceCoordinate;
    }

    private void setDestinyCoordinate(String destinyCoordinate) {
        this.destinyCoordinate = destinyCoordinate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFromResultSet(ResultSet resultSet){
        try {
            this.setOrderId(resultSet.getInt(1));
            this.setOrderStatus(resultSet.getString(2));
            this.setSourceCoordinate(resultSet.getString(3));
            this.setDestinyCoordinate(resultSet.getString(4));
            this.setPrice(resultSet.getDouble(5));
            Client client = new Client();
            client.setId(resultSet.getInt(6));
            client.setLogin(resultSet.getString(7));
            client.setFirstName(resultSet.getString(8));
            client.setLastName(resultSet.getString(9));
            this.setClient(client);
            Taxi taxi = new Taxi();
            taxi.setId(resultSet.getInt(10));
            taxi.setLogin(resultSet.getString(11));
            taxi.setFirstName(resultSet.getString(12));
            taxi.setLastName(resultSet.getString(13));
            Car car = new Car();
            car.setNumber(resultSet.getString(14));
            car.setName(resultSet.getString(15));
            car.setColour(resultSet.getString(16));
            taxi.setCar(car);
            this.setTaxi(taxi);
        }catch (SQLException ex){
            log.error(ex.getMessage());
        }
    }
}
