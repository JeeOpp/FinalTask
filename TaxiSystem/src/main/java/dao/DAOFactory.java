package dao;

import dao.impl.*;

/**
 * Factory class contains all the DAO classes objects to encapsulate them.
 */

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private final SignDAO signDAO = new SignDAOImpl();
    private final DispatcherDAO dispatcherDAO = new DispatcherDAOImpl();
    private final FeedbackDAO feedbackDAO = new FeedbackDAOImpl();
    private final UserManagerDAO userManagerDAO = new UserManagerDAOImpl();
    private final TaxisDAO taxisDAO = new TaxisDAOImpl();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public SignDAO getSignDAO() {
        return signDAO;
    }

    public DispatcherDAO getDispatcherDAO() {
        return dispatcherDAO;
    }

    public FeedbackDAO getFeedbackDAO() {
        return feedbackDAO;
    }

    public UserManagerDAO getUserManagerDAO() {
        return userManagerDAO;
    }

    public TaxisDAO getTaxisDAO() {
        return taxisDAO;
    }
}