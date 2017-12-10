package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by DNAPC on 08.12.2017.
 */
public class Order {
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

    public void setSourceCoordinate(String sourceCoordinate) {
        this.sourceCoordinate = sourceCoordinate;
    }

    public void setDestinyCoordinate(String destinyCoordinate) {
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
            this.setClient(client);
            Taxi taxi = new Taxi();
            taxi.setId(resultSet.getInt(8));
            taxi.setLogin(resultSet.getString(9));
            Car car = new Car();
            car.setNumber(resultSet.getString(10));
            car.setName(resultSet.getString(11));
            car.setColour(resultSet.getString(12));
            taxi.setCar(car);
            this.setTaxi(taxi);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
