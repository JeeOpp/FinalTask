package dao.impl;

import dao.AuthorizationDAO;

/**
 * Created by DNAPC on 12.11.2017.
 */
public class AuthorizationDAOImpl implements AuthorizationDAO {
    @Override
    public Boolean registration(String... args) {
        return null;
    }

    @Override
    public String authentication(String... args) {
        return "admin";
    }
}
