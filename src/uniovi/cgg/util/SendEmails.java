package uniovi.cgg.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendEmails {

	private final static int PORT = 587;

	public SendEmails(String SMTPServer, String from, String userAccount, String password, String toEmails, String ccEmails, String bccEmails, String title,
			String text) throws AddressException, MessagingException {
		// Más propiedades: https://javaee.github.io/javamail/docs/api/com/sun/mail/smtp/package-summary.html
		Properties properties = new Properties();
		properties.put("mail.smtp.host", SMTPServer);
		properties.put("mail.smtp.port", PORT);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", true);

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userAccount, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmails));
		message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmails));
		message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccEmails));
		message.setSubject(title);
		message.setText(text);
		message.setSentDate(new java.util.Date());

		Transport.send(message);

		System.out.println("Done");

	}

}
