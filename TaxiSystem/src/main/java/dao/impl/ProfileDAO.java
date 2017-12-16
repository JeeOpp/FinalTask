package dao.impl;

import dao.WrappedConnector;

/**
 * Created by DNAPC on 16.12.2017.
 */
public class ProfileDAO {
    private WrappedConnector connector;

    public ProfileDAO() {
        this.connector = new WrappedConnector();
    }

    public ProfileDAO(WrappedConnector connector) {
        this.connector = connector;
    }

    public void close() {
        connector.closeConnection();
    }
}
