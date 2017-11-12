package service;

import service.impl.AuthorizationServiceImpl;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final AuthorizationService authorizationService = new AuthorizationServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance(){
        return instance;
    }
    public AuthorizationService getAuthorizationService(){
        return authorizationService;
    }
}
