package service;

import support.CoordinateGenerator;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final AuthorizationService authorizationService = new AuthorizationService();
    private final RegistrationService registrationService = new RegistrationService();
    private final DispatcherService dispatcherService = new DispatcherService();
    private final FeedbackService feedbackService = new FeedbackService();
    private final CoordinateGenerator coordinateGenerator = new CoordinateGenerator();

    private ServiceFactory() {}

    public static ServiceFactory getInstance(){
        return instance;
    }
    public AuthorizationService getAuthorizationService(){
        return authorizationService;
    }
    public RegistrationService getRegistrationService(){
        return registrationService;
    }
    public DispatcherService getDispatcherService(){ return dispatcherService; }
    public FeedbackService getFeedbackService(){
        return feedbackService;
    }
    public CoordinateGenerator getCoordinateGenerator(){
        return coordinateGenerator;
    }
}
