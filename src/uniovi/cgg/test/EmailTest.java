package uniovi.cgg.test;

import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import uniovi.cgg.util.SendEmails;

public class EmailTest {

	/**
	 * Para facilitar el ingresar datos de prueba
	 */
	private static String smtpServer = "smtp.gmail.com";
	private static String from = "@hotmail.com";
	private static String userAccount = "@gmail.com";
	private static String password = "";
	private static String emailsTo = "@gmail.com, @hotmail.com";
	private static String cc = "@hotmail.com";
	private static String bcc = "@uniovi.es";	
	private static String title = "Title here!!!!";
	private static String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla euismod, sapien finibus commodo semper, sapien velit sodales ipsum, at tristique mauris sapien id tortor. Duis iaculis velit in lectus hendrerit, a cursus mauris vehicula. Nam ac viverra sem, in laoreet quam. Pellentesque sagittis, orci non facilisis tincidunt, nisi diam malesuada ante, sed ultrices justo erat non diam. Suspendisse rhoncus luctus eros at blandit. Aliquam eleifend fringilla velit, at consectetur ipsum tempus nec. Fusce in aliquam lacus. Proin aliquet dignissim porta.";

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("SMTPServer:");
			smtpServer = scanner.nextLine();
			System.out.println("From:");
			from = scanner.nextLine();
			System.out.println("User Account:");
			userAccount = scanner.nextLine();
			System.out.println("Password:");
			password = scanner.nextLine();
			System.out.println("To (a, b, c, ...):");
			emailsTo = scanner.nextLine();
			System.out.println("CC:");
			cc = scanner.nextLine();
			System.out.println("Title:");
			title = scanner.nextLine();
			System.out.println("Text:");
			text = scanner.nextLine();
		} finally {
			scanner.close();
		}		
		
		try {
			new SendEmails(smtpServer, from, userAccount, password, emailsTo, cc, bcc, title, text);
		} catch (AddressException e) {
			System.out.println(e.getMessage());
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
	}

}
