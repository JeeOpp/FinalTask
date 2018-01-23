package controller.listener;

import entity.Taxi;
import entity.User;
import entity.entityEnum.UserEnum;
import org.apache.log4j.Logger;
import service.ServiceFactory;
import service.SignService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;

public class SessionDestroyedListener implements HttpSessionListener {
    private static final Logger log = Logger.getLogger(SessionDestroyedListener.class.getClass());
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SignService signService = serviceFactory.getSignService();

        HttpSession session = httpSessionEvent.getSession();
        User user = (User) session.getAttribute(UserEnum.USER.getValue());
        try {
            if(user.getRole().equals(UserEnum.TAXI.getValue())) {
                signService.changeSessionStatus((Taxi)user);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }
}
