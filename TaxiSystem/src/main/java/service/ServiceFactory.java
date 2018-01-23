package service;

public class ServiceFactory <E>{
    private static final ServiceFactory instance = new ServiceFactory();
    private final SignService signService = new SignService();
    private final DispatcherService dispatcherService = new DispatcherService();
    private final FeedbackService feedbackService = new FeedbackService();
    private final UserManagerService userManagerService = new UserManagerService();
    private final PaginationService<E> paginationService = new PaginationService<>();
    private final TaxisService taxisService = new TaxisService();

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
