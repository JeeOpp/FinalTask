package controller.command.impl;

import controller.command.ControllerCommand;
import entity.entityEnum.PaginationEnum;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Localize pages for a russian or english language.
 * The class implements {@link ControllerCommand}.
 */
public class Localization implements ControllerCommand {
    private final static String LOCALE_PARAMETER = "local";
    private final static String LOCALE_COOKIE = "LOCAL";

    /**
     * Get the page localization parameter.
     * If that method was called for the first time,
     * it writes this value to the cookie and session and redirect request to the page from which was called.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String local = req.getParameter(LOCALE_PARAMETER);
        Cookie getLocal = new Cookie(LOCALE_COOKIE, local);
        resp.addCookie(getLocal);

        req.getSession().setAttribute(LOCALE_PARAMETER, local);
        req.getRequestDispatcher(req.getParameter(PaginationEnum.PAGE.getValue())).forward(req,resp);
    }
}
