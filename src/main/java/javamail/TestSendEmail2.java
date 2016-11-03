package javamail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class TestSendEmail2 {
	public static void main(String[] args) {
		try {
			String userName = "zhlc_test@aerozhonghuan.com";
			String password = "Xianzhlc";
			String smtp_server = "smtp.ym.163.com";
			String from_mail_address = userName;
			String to_mail_address = "jans_test@163.com";
			Authenticator auth = new PopupAuthenticator(userName, password);
			Properties mailProps = new Properties();
			mailProps.put("mail.smtp.host", smtp_server);
			mailProps.put("mail.smtp.auth", "true");
			mailProps.put("username", userName);
			mailProps.put("password", password);

			Session mailSession = Session.getDefaultInstance(mailProps, auth);
			mailSession.setDebug(false);
			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(from_mail_address));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					to_mail_address));
			message.setSubject("Mail Testw");

			MimeMultipart multi = new MimeMultipart();
			BodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText("电子邮件测试内容w");

			multi.addBodyPart(textBodyPart);
			message.setContent(multi);
			message.saveChanges();
			Transport.send(message);
		} catch (Exception ex) {
			System.err.println("邮件发送失败的原因是：" + ex.getMessage());
			System.err.println("具体的错误原因");
			ex.printStackTrace(System.err);
		}
	}
}

class PopupAuthenticator extends Authenticator {
	private String username;
	private String password;

	public PopupAuthenticator(String username, String pwd) {
		this.username = username;
		this.password = pwd;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.username, this.password);
	}
}