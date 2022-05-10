import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws MessagingException, IOException {
        EmailTrigger mail = new EmailTrigger();
        mail.setupServerProperties();
        mail.draftEmail();
        mail.sendEmail();
    }

    static class EmailTrigger {
        Session newSession = null;
        MimeMessage mimeMessage = null;

        private final String SENDER_EMAIL = "project.0.dummy.0@gmail.com";
        private final String SENDER_EMAIL_PASSWD = "jaadu123";

        /**
         * Establishes smtp connection from provided host and email credentials
         * Sends the message to all the recipients provided
         *
         * @throws MessagingException
         */
        private void sendEmail() throws MessagingException {
            String emailHost = "smtp.gmail.com";

            Transport transport = newSession.getTransport("smtp");
            transport.connect(emailHost, SENDER_EMAIL, SENDER_EMAIL_PASSWD);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();

            System.out.println("Email successfully sent !!!");
        }

        /**
         * This method drafts an email with subject, body and
         * adds all the recipients mentioned
         *
         * @throws MessagingException
         */
        private void draftEmail() throws MessagingException {
            String[] emailRecipients = {"ayush.khare@searce.com", "shreyas.patel@searce.com"};
            String emailSubject = "No data found";
            String emailBody = "The source repository is not giving any data. " +
                    "Eventually query results are empty, hence there are no results for the kpi search in the enterprise search portal. " +
                    "Please look into that.";

            mimeMessage = new MimeMessage(newSession);

            for (String emailRecipient : emailRecipients) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipient));
            }

            mimeMessage.setSubject(emailSubject);
            mimeMessage.setText(emailBody);
        }

        /**
         * This method sets-up smtp essential properties
         * Session authentication via email password of the sender
         */
        private void setupServerProperties() {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            newSession = Session.getDefaultInstance(properties,new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, SENDER_EMAIL_PASSWD);
                }
            });
        }
    }
}