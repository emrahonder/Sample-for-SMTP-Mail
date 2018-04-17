package com.nioya.sample.smtp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	Session session = null;

	public SendMail(String mailSender) {

		this.session = this.getSession(mailSender);
	}

	public void send(String topic, String content, String[] to, String[] cc) {
		this.send(topic, content, true, to, cc, null);
	}

	public void send(String topic, String content, String[] to) {
		this.send(topic, content, true, to, null, null);
	}
	public void send(String topic, String content, String to) {
		String[] tempTo = new String[1];
		tempTo[0] = to;
		this.send(topic, content, true, tempTo, null, null);
	}

	public void sendPlainText(String topic, String content, String[] to, String[] cc) {
		this.send(topic, content, false, to, cc, null);
	}

	public void sendPlainText(String topic, String content, String[] to) {
		this.send(topic, content, false, to, null, null);
	}
	
	

	public void send(String topic, String content, boolean isHTML, String[] to, String[] cc, String[] bcc) {

		if(this.session == null){
			throw new RuntimeException("NULL SESSION, Please Check Provider Name");
		}
		try {

			Message message = new MimeMessage(this.session);
			InternetAddress from = new InternetAddress(Credentials.SENDER_EMAIL);
			try {
				from = new InternetAddress("noreply@raporland.com", Credentials.SENDER_TITLE);
			} catch (UnsupportedEncodingException e1) {
				throw new RuntimeException(e1);
			}

			message.setFrom(from);
			for (String toEmail : to) {
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			}
			if(cc != null){
				for (String ccEmail : cc) {
					message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail));
				}			
			}
			if(bcc != null){
				for (String bccEmail : bcc) {
					message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(bccEmail));
				}			
			}


			message.setSubject(topic);
			if (isHTML) {
				message.setContent(content, "text/html; charset=utf-8");
			} else {
				message.setContent(content, "charset=utf-8");
			}

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	private Session getSession(String mailSender) {
		Session session = null;
		switch (mailSender) {
		case "YANDEX":
			Properties yandexProperties = this.getYandexProperties();
			session = this.generateSession(yandexProperties, Credentials.YANDEX_USERNAME, Credentials.YANDEX_PASSWORD);
			break;
		case "GMAIL":
			Properties gmailProperties = this.getGmailProperties();
			session = this.generateSession(gmailProperties, Credentials.GMAIL_USERNAME, Credentials.GMAIL_PASSWORD);
			break;

		default:
			break;
		}

		return session;

	}

	private Session generateSession(Properties properties, String username, String password) {
		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		return session;

	}

	private Properties getYandexProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.yandex.com.tr");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		return properties;
	}
	
	private Properties getGmailProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		return properties;
	}

}
