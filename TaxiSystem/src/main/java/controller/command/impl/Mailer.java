package controller.command.impl;

import controller.command.ControllerCommand;
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
 * Send a mail for a special situation, for example sending mail to admin.
 */
public class Mailer implements ControllerCommand {
    private final static String SUBJECT_PARAMETER = "subject";
    private final static String MAIL_TEXT_PARAMETER = "text";
    private final static String CLIENT_MAIN_PAGE = "WEB-INF/Client/main.jsp";
    private final static String INDEX_PAGE = "index.jsp";
    private final static String RESTORE_TO_PARAMETER = "mail";
    private final static String TO_ADMIN_MAIL = "demkoandrey2012@yandex.by";
    private final static String LOCALE_PARAMETER = "local";
    private final static String BUNDLE = "localization.local";
    private final static String SUBJECT_LOCALE = "local.restoreEmail.subject";
    private final static String TEXT_LOCALE = "local.restoreEmail.text";
    private final static String START_RESTORE_LINK = "http://localhost:8086/Controller?method=userManager&action=restorePassword&mail=";
    private final static String END_RESTORE_LINK = "&hashPassword=";

    /**
     * Realization of command pattern. Read a action parameter from request and execute special command depending on read parameter.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ACTION);
        MailerAction mailerAction = MailerAction.getConstant(action);
        switch (mailerAction) {
            case SEND_QUESTION:
                sendQuestion(req, resp);
                break;
            case PRE_RESTORE:
                preRestore(req, resp);
                break;
        }
    }

    /**
     * receives a subject mail parameter and body mail parameter.
     * Send a mail to admin with that parameters. Finally redirect request to the main client page.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    private void sendQuestion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String subject = req.getParameter(SUBJECT_PARAMETER);
        String text = req.getParameter(MAIL_TEXT_PARAMETER);

        Thread sendMail = new Thread(new TLSSender(TO_ADMIN_MAIL,text,subject));
        sendMail.start();
        req.getRequestDispatcher(CLIENT_MAIN_PAGE).forward(req,resp);
    }

    /**
     * receives the e-mail we want to send instructions for further recovering the password and sends them to this e-mail.
     * @param req Standard request argument
     * @param resp Standard response argument
     * @throws ServletException Standard exception
     * @throws IOException Standard exception
     */
    private void preRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String mail = req.getParameter(RESTORE_TO_PARAMETER);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserManagerService userManagerService = serviceFactory.getUserManagerService();
        String hashPassword = userManagerService.getHashPassword(mail);

        String locale = (String) req.getSession().getAttribute(LOCALE_PARAMETER);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE,new Locale(locale));
        String restoreSubject = resourceBundle.getString(SUBJECT_LOCALE);
        String restoreText = resourceBundle.getString(TEXT_LOCALE)+"\n"+createRestoreLink(mail,hashPassword);

        Thread sendMail = new Thread(new TLSSender(mail,restoreText,restoreSubject));
        sendMail.start();
        resp.sendRedirect(INDEX_PAGE);
    }
    private String createRestoreLink(String mail, String hashPassword){
        return START_RESTORE_LINK+mail+END_RESTORE_LINK+hashPassword;
    }

    private enum MailerAction{
        SEND_QUESTION("sendQuestion"),
        PRE_RESTORE("preRestore"),

        NONE("none");
        private String value;

        MailerAction(String value){
            this.value = value;
        }
        public String getValue(){
            return value;
        }

        public static MailerAction getConstant(String action){
            for (MailerAction each: MailerAction.values()){
                if(each.getValue().equals(action)){
                    return each;
                }
            }
            return NONE;
        }
    }
}
