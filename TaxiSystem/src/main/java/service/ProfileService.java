package service;

import dao.DAOFactory;
import dao.impl.ProfileDAO;
import entity.User;
import support.MD5;

/**
 * Created by DNAPC on 16.12.2017.
 */
public class ProfileService {
    public Boolean changePassword(User user, String currentPassword, String newPassword){
        if (user.getPassword().equals(MD5.md5Hash(currentPassword))){
            user.setPassword(MD5.md5Hash(newPassword));
            DAOFactory daoFactory = DAOFactory.getInstance();
            ProfileDAO profileDAO = daoFactory.getProfileDAO();
            profileDAO.changePassword(user);
            return true;
        }
        return false;
    }
}
