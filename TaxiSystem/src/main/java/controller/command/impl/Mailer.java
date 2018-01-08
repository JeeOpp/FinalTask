package controller.command.impl;

import controller.command.ControllerCommand;
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
        //String subject = req.getParameter("")

        //TLSSender tlsSender = new TLSSender("demkoandrey2012@gmail.com","Neponime1234");
        //tlsSender.send(subject,text,"demkoandrey2012@gmail.com","demkoandrey2012@yandex.by");
    }
}
