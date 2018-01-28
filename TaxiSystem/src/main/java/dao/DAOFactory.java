package dao;

import dao.impl.*;

/**
 * Factory class contains all the DAO classes objects to encapsulate them.
 */

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public SignDAO getSignDAO() {
        return new SignDAOImpl();
    }

    public DispatcherDAO getDispatcherDAO() {
        return new DispatcherDAOImpl();
    }

    public FeedbackDAO getFeedbackDAO() {
        return new FeedbackDAOImpl();
    }

    public UserManagerDAO getUserManagerDAO() {
        return new UserManagerDAOImpl();
    }

    public TaxisDAO getTaxisDAO() {
        return new TaxisDAOImpl();
    }
}