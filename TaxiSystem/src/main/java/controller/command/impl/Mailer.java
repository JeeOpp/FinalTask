package controller.command.impl;

import controller.command.ControllerCommand;
import controller.command.SwitchConstant;
import service.ServiceFactory;
import service.UserManagerService;
import support.TLSSender;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by DNAPC on 08.01.2018.
 */
public class Mailer implements ControllerCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        SwitchConstant switchConstant = SwitchConstant.getConstant(action);
        switch (switchConstant) {
            case SEND_QUESTION:
                sendQuestion(req, resp);
                break;
            case PRE_RESTORE:
                preRestore(req, resp);
                break;
        }
    }

    private void sendQuestion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String subject = req.getParameter("subject");
        String text = req.getParameter("text");
        String toEmail = "demkoandrey2012@yandex.by";

        Thread sendMail = new Thread(new TLSSender(toEmail,text,subject));
        sendMail.start();
        req.getRequestDispatcher("WEB-INF/Client/main.jsp").forward(req,resp);
    }

    private void preRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String mail = req.getParameter("mail");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserManagerService userManagerService = serviceFactory.getUserManagerService();
        String hashPassword = userManagerService.getHashPassword(mail);

        String locale = (String) req.getSession().getAttribute("local");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("localization.local",new Locale(locale));
        String restoreSubject = resourceBundle.getString("local.restoreEmail.subject");
        String restoreText = resourceBundle.getString("local.restoreEmail.text")+"\n"+createRestoreLink(mail,hashPassword);

        Thread sendMail = new Thread(new TLSSender(mail,restoreText,restoreSubject));
        sendMail.start();
        resp.sendRedirect("index.jsp");
    }
    private String createRestoreLink(String mail, String hashPassword){
        return "http://localhost:8086/Controller?method=userManager&action=restorePassword&mail="+mail+"&hashPassword="+hashPassword;
    }
}
