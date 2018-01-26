package service;

import service.impl.*;

/**
 * Factory class contains all the service classes objects to encapsulate them.
 */
public class ServiceFactory <E>{
    private static final ServiceFactory instance = new ServiceFactory();
    private final SignService signService = new SignServiceImpl();
    private final DispatcherService dispatcherService = new DispatcherServiceImpl();
    private final FeedbackService feedbackService = new FeedbackServiceImpl();
    private final UserManagerService userManagerService = new UserManagerServiceImpl();
    private final PaginationService<E> paginationService = new PaginationServiceImpl<>();
    private final TaxisService taxisService = new TaxisServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance(){
        return instance;
    }
    public SignService getSignService(){
        return signService;
    }
    public DispatcherService getDispatcherService(){ return dispatcherService; }
    public FeedbackService getFeedbackService(){
        return feedbackService;
    }
    public UserManagerService getUserManagerService(){
        return userManagerService;
    }
    public PaginationService getPaginationService(){
        return paginationService;
    }
    public TaxisService getTaxisService(){
        return taxisService;
    }
}
