package dao.impl;

import dao.DAOFactory;
import dao.UserManagerDAO;
import entity.Car;
import entity.Client;
import entity.Taxi;
import entity.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserManagerDAOImplTest {
    private static final DAOFactory daoFactory = DAOFactory.getInstance();
    private static final UserManagerDAO userManagerDAO = daoFactory.getUserManagerDAO();
    private static final int DIFF = 10;
    private static final int AVAILABLE_CLIENT_ID = 2;
    private static final String AVAILABLE_MAIL = "demkoandrey2012@yandex.by";
    private static final String HASH_PASS = "63a9f0ea7bb98050796b649e85481845";
    private static final int AVAILABLE_TAXI_ID = 4;
    private static final String AVAILABLE_CAR_NUMBER = "1111AH1";

    @Test
    public void getTaxiList() throws Exception {
        List<Taxi> taxiList = userManagerDAO.getTaxiList();
        assertTrue(taxiList!=null);
    }

    @Test
    public void getClientList() throws Exception {
        List<Client> clientList = userManagerDAO.getClientList();
        assertTrue(clientList!=null);
    }

    @Test
    public void changeBanStatus() throws Exception {
        boolean actual = false;
        User user = new User.UserBuilder().setId(AVAILABLE_CLIENT_ID).setRole("client").setBanStatus(false).build(); //first client in database;
        userManagerDAO.changeBanStatus(user);
        List<Client> clientList = userManagerDAO.getClientList();
        if(clientList!=null){
            for (User each:clientList){
                if (each.getId()==AVAILABLE_CLIENT_ID && each.getBanStatus()){
                    user = each;
                    actual = true;
                }
            }
        }
        userManagerDAO.changeBanStatus(user);
        assertTrue(actual);
    }

    @Test
    public void decreaseBonus() throws Exception {
        int expected = 0;
        int actual = 0;
        Client client = null;
        List<Client> clientList = userManagerDAO.getClientList();
        if(clientList!=null){
            for(Client each:clientList){
                if(each.getId()==AVAILABLE_CLIENT_ID){   //first user
                    client = each;
                    break;
                }
            }
        }
        expected = (client != null) ? client.getBonusPoints()-DIFF : 0;
        userManagerDAO.decreaseBonus(client,DIFF);
        clientList = userManagerDAO.getClientList();
        if(clientList!=null){
            for (Client each:clientList){
                if (each.getId()==AVAILABLE_CLIENT_ID){
                    actual = each.getBonusPoints();
                    break;
                }
            }
        }
        assertEquals(expected,actual);
    }

    @Test
    public void changeBonusCount() throws Exception {
        int expected = 0;
        int actual = 0;
        Client client = new Client();
        List<Client> clientList = userManagerDAO.getClientList();
        if(clientList!=null){
            for(Client each:clientList){
                if(each.getId()==AVAILABLE_CLIENT_ID){   //first user
                    client = each;
                    break;
                }
            }
        }
        expected = client.getBonusPoints()+DIFF;
        client.setBonusPoints(DIFF);
        userManagerDAO.changeBonusCount(client);
        clientList = userManagerDAO.getClientList();
        if(clientList!=null){
            for (Client each:clientList){
                if (each.getId()==AVAILABLE_CLIENT_ID){
                    actual = each.getBonusPoints();
                    break;
                }
            }
        }
        assertEquals(expected,actual);
    }

    @Test
    public void changeTaxiCar() throws Exception {
        String actual = "";
        Taxi taxi = (Taxi) new Taxi.TaxiBuilder().setCar(new Car(AVAILABLE_CAR_NUMBER)).setId(AVAILABLE_TAXI_ID).build();
        userManagerDAO.changeTaxiCar(taxi);
        List<Taxi> taxiList = userManagerDAO.getTaxiList();
        for(Taxi each:taxiList){
            if(each.getId() == AVAILABLE_TAXI_ID){
                actual = each.getCar().getNumber();
            }
        }
        assertEquals(AVAILABLE_CAR_NUMBER,actual);
    }

    @Test
    public void getHashPassword() throws Exception {
        String actual = userManagerDAO.getHashPassword(AVAILABLE_MAIL);
        assertEquals(HASH_PASS,actual);
    }

}