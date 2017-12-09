package dao.impl;

import dao.WrappedConnector;
import entity.Order;
import entity.Taxi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DNAPC on 06.12.2017.
 */
public class DispatcherDAO {
    private static final String SQL_SELECT_AVAILABLE_TAXI="SELECT taxi.id, taxi.login, taxi.name, taxi.surname, car.number, car.car, car.colour FROM taxisystem.taxi JOIN car ON taxi.carNumber = car.number WHERE taxi.availableStatus = true;";

    private WrappedConnector connector;

    public DispatcherDAO() {
        this.connector = new WrappedConnector();
    }

    public DispatcherDAO(WrappedConnector connector) {
        this.connector = connector;
    }

    public void close() {
        connector.closeConnection();
    }

    public List<Taxi> getAvailableTaxiList() throws SQLException {
        ResultSet resultSet;
        Statement statement = null;
        List<Taxi> taxiList = new ArrayList<>();
        Taxi taxi;
        try {
            statement = connector.getStatement();
            if((resultSet = statement.executeQuery(SQL_SELECT_AVAILABLE_TAXI))!=null){
                while (resultSet.next()){
                    taxi = new Taxi();
                    taxi.setFromResultSet(resultSet);
                    taxiList.add(taxi);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception (request or table failed): " + ex);
        } finally {
            connector.closeStatement(statement);
        }
        return taxiList;
    }

    public void orderConfirm(Order order) throws SQLException{
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connector.makeOrderPreparedStatement();
            preparedStatement.setInt(1, order.getClient().getId());
            preparedStatement.setInt(2, order.getTaxi().getId());
            preparedStatement.setString(3, order.getSourceCoordinate());
            preparedStatement.setString(4, order.getDestinyCoordinate());
            preparedStatement.setDouble(5, order.getPrice());
            preparedStatement.execute();
        }catch (SQLException ex){
            System.err.println("SQL exception (request or table failed): " + ex);
        }finally {
            connector.closePreparedStatement(preparedStatement);
        }
    }

}
