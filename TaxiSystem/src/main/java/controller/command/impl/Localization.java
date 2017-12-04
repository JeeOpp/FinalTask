package controller.command.impl;

import controller.command.ControllerCommand;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DNAPC on 03.12.2017.
 */
public class Localization implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String local = req.getParameter("local");
        Cookie getLocal = new Cookie("LOCAL", local);
        resp.addCookie(getLocal);

        req.getSession().setAttribute("local", local);
        req.getRequestDispatcher(req.getParameter("page")).forward(req,resp);
    }
}
