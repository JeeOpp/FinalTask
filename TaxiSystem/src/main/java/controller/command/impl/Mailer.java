package controller.command.impl;

import controller.command.ControllerCommand;
import service.SignService;
import support.TLSSender;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DNAPC on 08.01.2018.
 */
public class Mailer implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subject = req.getParameter("subject");
        String text = req.getParameter("text");

        Thread sendMail = new Thread(new TLSSender(text,subject));
        sendMail.start();
        req.getRequestDispatcher("WEB-INF/Client/main.jsp").forward(req,resp);
    }
}
