package dao.impl;

import dao.WrappedConnection;
import entity.Client;
import entity.Taxi;
import entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DNAPC on 16.12.2017.
 */
public class UserManagerDAO {
    private static final String SQL_SELECT_ALL_TAXI = "SELECT taxi.id, taxi.login, taxi.name, taxi.surname, taxi.availableStatus, taxi.banStatus, taxi.role, car.number, car.car, car.colour FROM taxisystem.taxi JOIN car ON taxi.carNumber = car.number;";
    private static final String SQL_SELECT_ALL_CLIENT = "SELECT client.id, client.login, client.name, client.surname, client.bonusPoints, client.banStatus, client.role FROM taxisystem.client WHERE client.role = \"client\";";

    private WrappedConnection wrappedConnection;

    public UserManagerDAO() {
        this.wrappedConnection = new WrappedConnection();
    }

    public void close() {
        wrappedConnection.closeConnection();
    }

    public void changePassword(User user){
        PreparedStatement preparedStatement=null;
        try {
            if(user.getRole().equals("taxi")) {
                preparedStatement = wrappedConnection.getChangeTaxiPassPreparedStatement();
            }else{
                preparedStatement = wrappedConnection.getChangeClientPassPreparedStatement();
            }
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
        }catch (SQLException e){
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            wrappedConnection.closePreparedStatement(preparedStatement);
        }
    }
    public List<Taxi> getTaxiList() throws SQLException {
        ResultSet resultSet;
        Statement statement = null;
        List<Taxi> taxiList = new ArrayList<>();
        Taxi taxi;
        try {
            statement = wrappedConnection.getStatement();
            if((resultSet = statement.executeQuery(SQL_SELECT_ALL_TAXI))!=null){
                while (resultSet.next()){
                    taxi = new Taxi();
                    taxi.setFromResultSet(resultSet);
                    taxiList.add(taxi);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception (request or table failed): " + ex);
        } finally {
            wrappedConnection.closeStatement(statement);
        }
        return taxiList;
    }
    public List<Client> getClientList() throws SQLException{
        ResultSet resultSet;
        Statement statement = null;
        List<Client> clientList = new ArrayList<>();
        Client client;
        try {
            statement = wrappedConnection.getStatement();
            if((resultSet = statement.executeQuery(SQL_SELECT_ALL_CLIENT))!=null){
                while (resultSet.next()){
                    client = new Client();
                    client.setFromResultSet(resultSet);
                    clientList.add(client);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception (request or table failed): " + ex);
        } finally {
            wrappedConnection.closeStatement(statement);
        }
        return clientList;
    }
    public void changeBanStatus(User user){
        PreparedStatement preparedStatement=null;
        try {
            if(user.getRole().equals("taxi")) {
                preparedStatement = wrappedConnection.getTaxiBanPreparedStatement();
            }else{
                preparedStatement = wrappedConnection.getClientBanPreparedStatement();
            }
            preparedStatement.setBoolean(1,!user.getBanStatus());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
        }catch (SQLException e){
            System.err.println("SQL exception (request or table failed): " + e);
        } finally {
            wrappedConnection.closePreparedStatement(preparedStatement);
        }
    }
    public void decreaseBonus(Client client, int bonus) throws SQLException{ //bonus - how many client spend
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = wrappedConnection.decreaseBonus();
            preparedStatement.setInt(1,bonus);
            preparedStatement.setInt(2,client.getId());
            preparedStatement.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            wrappedConnection.closePreparedStatement(preparedStatement);
        }
    }
    public void changeBonusCount(Client client) throws SQLException{
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = wrappedConnection.changeBonusCount();
            preparedStatement.setInt(1,client.getBonusPoints());
            preparedStatement.setInt(2,client.getId());
            preparedStatement.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            wrappedConnection.closePreparedStatement(preparedStatement);
        }
    }
}
