package controller.command.impl;

import controller.command.ControllerCommand;
import dao.AuthorizationDAO;
import dao.DAOFactory;
import entity.User;
import service.AuthorizationService;
import service.ServiceFactory;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by DNAPC on 05.12.2017.
 */
public class LogOut implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        session.invalidate();
        resp.sendRedirect("index.jsp");
    }
}
