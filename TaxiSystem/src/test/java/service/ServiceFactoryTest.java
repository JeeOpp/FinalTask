package service;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DNAPC on 30.01.2018.
 */
public class ServiceFactoryTest {
    private static final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    @Test
    public void getInstance() throws Exception {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        assertTrue(serviceFactory != null);
    }

    @Test
    public void getSignService() throws Exception {
        assertTrue(serviceFactory.getSignService()!=null);
    }

    @Test
    public void getDispatcherService() throws Exception {
        assertTrue(serviceFactory.getDispatcherService()!=null);
    }

    @Test
    public void getFeedbackService() throws Exception {
        assertTrue(serviceFactory.getFeedbackService()!=null);
    }

    @Test
    public void getUserManagerService() throws Exception {
        assertTrue(serviceFactory.getUserManagerService()!=null);
    }

    @Test
    public void getPaginationService() throws Exception {
        assertTrue(serviceFactory.getPaginationService()!=null);
    }

    @Test
    public void getTaxisService() throws Exception {
        assertTrue(serviceFactory.getTaxisService()!=null);
    }

}