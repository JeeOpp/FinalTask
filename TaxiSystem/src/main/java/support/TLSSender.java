package support;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * Created by DNAPC on 08.01.2018.
 */
public class TLSSender implements Runnable{
    private final static String USERNAME = "demkoandrey1998@gmail.com";
    private final static String PASSWORD = "Neponime1234";
    private Properties props;
    private String text;
    private String subject;
    private String toEmail;

    public TLSSender(String toEMail, String text, String subject) {
        this.toEmail = toEMail;
        this.text = text;
        this.subject = subject;

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    @Override
    public void run() {
        send(subject,text,USERNAME,toEmail);
    }

    private void send(String subject, String text, String fromEmail, String toEmail) {
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            ;;
        }
    }
}
