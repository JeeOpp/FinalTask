package dao;

import dao.impl.*;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private final AuthorizationDAO authorizationDAO = new AuthorizationDAOImpl();
    private final RegistrationDAO registrationDAO = new RegistrationDAOImpl();
    private final DispatcherDAO dispatcherDAO = new DispatcherDAO();
    private final FeedbackDAO feedbackDAO = new FeedbackDAO();
    private final ProfileDAO profileDAO = new ProfileDAO();

    private DAOFactory() {}

    public static DAOFactory getInstance(){
        return instance;
    }
    public AuthorizationDAO getAuthorizationDAO(){
        return authorizationDAO;
    }
    public RegistrationDAO getRegistrationDAO(){
        return registrationDAO;
    }
    public DispatcherDAO getDispatcherDAO(){
        return dispatcherDAO;
    }
    public FeedbackDAO getFeedbackDAO(){
        return feedbackDAO;
    }
    public ProfileDAO getProfileDAO(){
        return profileDAO;
    }
}
