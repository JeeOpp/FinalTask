package support;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * Created by DNAPC on 08.01.2018.
 */
public class TLSSender implements Runnable{
    private final static String USERNAME = "demkoandrey2012@gmail.com";
    private final static String TO_EMAIL = "demkoandrey2012@yandex.by";
    private final static String PASSWORD = "12345";
    private Properties props;
    private String text;
    private String subject;

    public TLSSender(String text, String subject) {
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
        send(subject,text,USERNAME,TO_EMAIL);
    }

    private void send(String subject, String text, String fromEmail, String toEmail) {
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            ;;
        }
    }
}
