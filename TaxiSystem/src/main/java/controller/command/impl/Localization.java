package controller.command.impl;

import controller.command.ControllerCommand;
import entity.entityEnum.PaginationEnum;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Localization implements ControllerCommand {
    private final static String LOCALE_PARAMETER = "local";
    private final static String LOCALE_COOKIE = "LOCAL";
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String local = req.getParameter(LOCALE_PARAMETER);
        Cookie getLocal = new Cookie(LOCALE_COOKIE, local);
        resp.addCookie(getLocal);

        req.getSession().setAttribute(LOCALE_PARAMETER, local);
        req.getRequestDispatcher(req.getParameter(PaginationEnum.PAGE.getValue())).forward(req,resp);
    }
}
