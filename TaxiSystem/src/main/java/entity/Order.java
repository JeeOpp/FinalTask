package entity;

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
}
