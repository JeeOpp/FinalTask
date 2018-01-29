package dao;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DNAPC on 29.01.2018.
 */
public class DAOFactoryTest {
    private static final DAOFactory daoFactory = DAOFactory.getInstance();

    @Test
    public void getInstance() throws Exception {
        DAOFactory daoFactory = DAOFactory.getInstance();
        assertTrue(daoFactory != null);
    }

    @Test
    public void getSignDAO() throws Exception {
        assertTrue(daoFactory.getSignDAO()!= null);
    }

    @Test
    public void getDispatcherDAO() throws Exception {
        assertTrue(daoFactory.getDispatcherDAO()!=null);
    }

    @Test
    public void getFeedbackDAO() throws Exception {
        assertTrue(daoFactory.getFeedbackDAO()!=null);
    }

    @Test
    public void getUserManagerDAO() throws Exception {
        assertTrue(daoFactory.getUserManagerDAO()!=null);
    }

    @Test
    public void getTaxisDAO() throws Exception {
        assertTrue(daoFactory.getTaxisDAO()!=null);
    }
}