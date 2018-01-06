package dao;

import controller.command.impl.Taxis;
import dao.impl.*;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private final SignDAO signDAO = new SignDAO();
    private final DispatcherDAO dispatcherDAO = new DispatcherDAO();
    private final FeedbackDAO feedbackDAO = new FeedbackDAO();
    private final UserManagerDAO userManagerDAO = new UserManagerDAO();
    private final TaxisDAO taxisDAO = new TaxisDAO();

    private DAOFactory() {}

    public static DAOFactory getInstance(){
        return instance;
    }
    public SignDAO getSignDAO(){
        return signDAO;
    }
    public DispatcherDAO getDispatcherDAO(){
        return dispatcherDAO;
    }
    public FeedbackDAO getFeedbackDAO(){
        return feedbackDAO;
    }
    public UserManagerDAO getUserManagerDAO(){
        return userManagerDAO;
    }
    public TaxisDAO getTaxisDAO(){
        return taxisDAO;
    }
}
