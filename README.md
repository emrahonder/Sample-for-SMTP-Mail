# Sample-for-SMTP-Mail
This code shows how to send  Mail over SMTP on Java. There are 2 different provider option to send SMTP email: Gmail and Yandex.
Custom ones can be added by updating related functions.
And also this code show how to send plain text and html ones over Java. 
I have added some Overloading methods to send mail with CC, BCC and as plaintext or HTML.
Example: 

```
	public static void main(String[] args) {
		SendMail sendMail = new SendMail("GMAIL"); // Create object.
		sendMail.send("test", "test", "emrahonder@gmail.com"); // Send email
		System.out.println("Done");
	}
```
