package service;

import service.impl.*;

/**
 * Factory class contains all the service classes objects to encapsulate them.
 */
public class ServiceFactory <E> {
    private static final ServiceFactory instance = new ServiceFactory();
    private SignService signService = new SignServiceImpl();
    private DispatcherService dispatcherService = new DispatcherServiceImpl();
    private FeedbackService feedbackService = new FeedbackServiceImpl();
    private UserManagerService userManagerService = new UserManagerServiceImpl();
    private PaginationService<E> paginationService = new PaginationServiceImpl<E>();
    private TaxisService taxisService = new TaxisServiceImpl();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public SignService getSignService() {
        return signService;
    }

    public DispatcherService getDispatcherService() {
        return dispatcherService;
    }

    public FeedbackService getFeedbackService() {
        return feedbackService;
    }

    public UserManagerService getUserManagerService() {
        return userManagerService;
    }

    public PaginationService<E> getPaginationService() {
        return paginationService;
    }

    public TaxisService getTaxisService() {
        return taxisService;
    }
}
