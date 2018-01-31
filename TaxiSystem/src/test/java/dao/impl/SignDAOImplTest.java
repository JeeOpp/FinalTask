package dao.impl;

import dao.DAOFactory;
import dao.SignDAO;
import entity.Taxi;
import org.junit.Test;

import static org.junit.Assert.*;

public class SignDAOImplTest {
    private static final DAOFactory daoFactory = DAOFactory.getInstance();
    private static final SignDAO signDAO = daoFactory.getSignDAO();


    @Test
    public void preAuthorize() throws Exception{
        String login = "root";
        String password = "root";
        String expected = "admin";

        String actual = signDAO.preAuthorize(login,password);
        assertEquals(expected,actual);
    }

    @Test
    public void clientAuthorize() throws Exception {
        String login = "client";
        String password = "root";
        String expected = "client";

        assertEquals(expected,signDAO.clientAuthorize(login,password).getRole());
    }

    @Test
    public void taxiAuthorize() throws Exception {
        String login = "taxi";
        String password = "root";
        String expected = "taxi";

        assertEquals(expected,signDAO.clientAuthorize(login,password).getRole());
    }

    @Test
    public void isLoginFree() throws Exception {
        String login = "root";
        assertFalse(signDAO.isLoginFree(login));
    }

    @Test
    public void isMailFree() throws Exception {
        String mail = "Admin";
        assertFalse(signDAO.isMailFree(mail));
    }

    @Test
    public void changeAvailableStatus() throws Exception {
        int taxiId = 4; //because this taxi is exists
        String login = "taxi";
        String password = "root";

        Taxi taxi = (Taxi) new Taxi.TaxiBuilder().setId(taxiId).build();
        signDAO.changeAvailableStatus(taxi);
        taxi = signDAO.taxiAuthorize(login,password);
        assertTrue(taxi.isAvailableStatus());
        signDAO.changeAvailableStatus(taxi);
    }
}