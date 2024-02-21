package mailtest;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	public static void main(String[] args) {
		try {
			Properties property = new Properties();
			property.put("mail.smtp.host", "smtp.gmail.com");
			property.put("mail.smtp.auth", "true");
			property.put("mail.smtp.starttls.enable", "true");
			property.put("mail.smtp.host", "smtp.gmail.com");
			property.put("mail.smtp.port", "587");
			property.put("mail.smtp.debug", "true");

			property.put("mail.smtp.ssl.protocols", "TLSv1.2"); //??
			
			
			Session session = Session.getInstance(property, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("okachimachi0522", "aewu xpag fjje ylhf");
				}
			});
			
			MimeMessage mimeMessage = new MimeMessage(session);
			InternetAddress toAddress = new InternetAddress("okachimachi0522@gmail.com", "受信者名");
			mimeMessage.setRecipient(Message.RecipientType.TO, toAddress);
			InternetAddress fromAddress = new InternetAddress("okachimachi0522@gmail.com", "送信者名");
			mimeMessage.setFrom(fromAddress);
			mimeMessage.setSubject("メール送信テストです。", "ISO-2022-JP");
			mimeMessage.setText("testeststsets", "ISO-2022-JP");
			Transport.send(mimeMessage);
			System.out.println("メール送信が完了しました。");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}