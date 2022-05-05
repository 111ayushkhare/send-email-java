import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        sendErrorMailToSPOC();
    }

    private static void sendErrorMailToSPOC() {
        final String SENDER_EMAIL = "project.0.dummy.0@gmail.com"; // VM's name
        final String RECEIVER_EMAIL = "ayushkhare111@gmail.com";
        final String HOST = "localhost"; // can be replaced VM's external IP
        final String PORT = "8002"; // Vm's port
        final String PASSWORD = "jaadu123";

        final String SUBJECT = "No Data found";
        final String BODY_TEXT = "The Source repo is not giving any data. Please look into that.";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", HOST);
        properties.setProperty("mail.smtp.port", PORT);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.timeout", "60000");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.user", SENDER_EMAIL);
        properties.setProperty("mail.password", PASSWORD);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, PASSWORD);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(SENDER_EMAIL));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(RECEIVER_EMAIL));

            msg.setSubject(SUBJECT);
            msg.setText(BODY_TEXT);

            Transport.send(msg);

            System.out.println("Mail Sent");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}