package controller.listener;

import entity.Taxi;
import entity.User;
import service.ServiceFactory;
import service.SignService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;

/**
 * Created by DNAPC on 04.01.2018.
 */
public class SessionDestroyedListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("destroyed");
        ServletContext servletContext = httpSessionEvent.getSession().getServletContext();
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SignService signService = serviceFactory.getSignService();

        HttpSession session = httpSessionEvent.getSession();
        User user = (User) session.getAttribute("user");
        try {
            if(user.getRole().equals("taxi")) {
                signService.changeSessionStatus((Taxi)user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
