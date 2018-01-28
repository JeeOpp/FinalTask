package service;

import service.impl.*;

/**
 * Factory class contains all the service classes objects to encapsulate them.
 */
public class ServiceFactory <E> {
    private static final ServiceFactory instance = new ServiceFactory();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public SignService getSignService() {
        return new SignServiceImpl();
    }

    public DispatcherService getDispatcherService() {
        return new DispatcherServiceImpl();
    }

    public FeedbackService getFeedbackService() {
        return new FeedbackServiceImpl();
    }

    public UserManagerService getUserManagerService() {
        return new UserManagerServiceImpl();
    }

    public PaginationService getPaginationService() {
        return new PaginationServiceImpl<E>();
    }

    public TaxisService getTaxisService() {
        return new TaxisServiceImpl();
    }
}
