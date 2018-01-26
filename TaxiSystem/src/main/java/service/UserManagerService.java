package service;

import entity.Client;
import entity.Taxi;
import entity.User;

import java.util.List;

public interface UserManagerService {
    /**
     * Changes a password specific user.
     *
     * @param user whose we want to change a pass.
     * @param currentPassword current password of user's account.
     * @param newPassword new password of specific user.
     * @return true is changing password is successfully completed, otherwise false.
     */
    boolean changePassword(User user, String currentPassword, String newPassword);

    /**
     * Selects all information about client.
     * @return client list (if there are no clients in database, returns null).
     */
    List<Client> getClientList();

    /**
     * Selects all information about taxi drivers.
     *
     * @return taxi drivers list (if there are no taxi drivers in database, returns null).
     */
    List<Taxi> getTaxiList();

    /**
     * Selects all information about taxi drivers available at the moment .
     *
     * @return available taxi drivers list.
     */
    List<Taxi> getAvailableTaxiList();

    /**
     * Changes ban status.
     *
     * @param user we want to ban/unban.
     */
    void changeBanStatus(User user);

    /**
     * changes amount of bonuses specific client.
     *
     * @param client whose amount of bonuses we want to change.
     */
    void changeBonusCount(Client client);

    /**
     * Changes taxi driver's car.
     *
     * @param taxi whose car we want change.
     */
    void changeTaxiCar(Taxi taxi);

    /**
     * receives hash password of user according with e-mail.
     *
     * @param mail of client whose hash password we want to receive.
     * @return hash password.
     */
    String getHashPassword(String mail);

    /**
     * Changes user password if hash password and mail of specific user are correct.
     * @param mail of client we want to restore the password.
     * @param hashPassword of client we want to restore the password.
     * @param newPassword we want to set instead of old one.
     * @return true if action is successfully, otherwise false.
     */
    boolean restorePassword(String mail, String hashPassword,String newPassword);
}
