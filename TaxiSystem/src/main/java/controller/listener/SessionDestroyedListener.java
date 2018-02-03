package controller.listener;

import entity.Taxi;
import entity.User;
import service.ServiceFactory;
import service.SignService;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import static support.constants.UserConstants.TAXI;
import static support.constants.UserConstants.USER;

/**
 * Uses to invalidate taxi's session if a taxi driver forgot to do this.
 */
public class SessionDestroyedListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    /**
     * Receives notification that a session is about to be invalidated.
     *
     * @param httpSessionEvent the HttpSessionEvent containing the session
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SignService signService = serviceFactory.getSignService();

        HttpSession session = httpSessionEvent.getSession();
        User user = (User) session.getAttribute(USER);
        if (user.getRole().equals(TAXI)) {
            signService.changeSessionStatus((Taxi) user);
        }
    }
}
