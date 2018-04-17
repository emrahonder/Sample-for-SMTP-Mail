package com.nioya.sample.smtp;


public class Test {

	public static void main(String[] args) {
		SendMail sendMail = new SendMail("GMAIL2");
		sendMail.send("test", "test", "emrahonder@gmail.com");
		System.out.println("Done");
	}
	/*
	public void sendError(String email, String content) {
		sendHTML(email, "Error", content);
	}
	
	public void sendInfo(String email, String content) {
		sendHTML(email, "Info", content);
	}

	public void sendPlainText(String email, String topic, String content) {
		this.send(email,topic,content,false);
	}
	
	public void sendHTML(String email, String topic, String content) {
		this.send(email,topic,content,true);
		
	}*/

}
