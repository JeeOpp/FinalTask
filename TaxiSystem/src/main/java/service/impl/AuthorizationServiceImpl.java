package service.impl;

import dao.AuthorizationDAO;
import dao.DAOFactory;
import service.AuthorizationService;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class AuthorizationServiceImpl implements AuthorizationService {
    @Override
    public Boolean registration(String... args) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        AuthorizationDAO personDAO = daoFactory.getAuthorizationDAO();
        return personDAO.registration(args);
    }

    @Override
    public String authentication(String... args) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        AuthorizationDAO personDAO = daoFactory.getAuthorizationDAO();
        return personDAO.authentication(args);
    }
}
