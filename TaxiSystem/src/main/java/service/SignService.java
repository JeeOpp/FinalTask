package service;

import entity.Client;
import entity.Taxi;
import entity.User;

public interface SignService {
    /**
     * Delegates to the DAO layer the writing of users to the database (only if transferred data passes the validation).
     * @param client we are want to write.
     * @return true if writing is success, otherwise false.
     */
    boolean registerClient(Client client);
    /**
     * Delegates to the DAO layer the writing of users to the database (only if transferred data passes the validation).
     *
     * @param taxi we are want to write/
     * @return true if writing is success, otherwise false.
     */
    boolean registerTaxi(Taxi taxi);

    /**
     * Authorizes user (client, taxi, admin). Delegates to the DAO layer the selecting the authorized user role.
     * @param login of user we want to authorize.
     * @param password of user we want to authorize.
     * @return role of the user if authorization is successful.
     */
    User authorize(String login, String password);

    /**
     * Changes the availability status of the taxi driver.
     *
     * @param taxi we want to change.
     */
    void changeSessionStatus(Taxi taxi);
}
