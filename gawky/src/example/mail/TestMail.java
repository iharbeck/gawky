package example.mail;

import gawky.global.Option;
import gawky.mail.Mail;

public class TestMail {
 
	public static void main(String[] args) throws Exception {
		
		Option.init();
		
		Mail.sendSimpleMail(
				            "ingo.harbeck@bertelsmann.de", "ingo harbeck", 
							"ingo.harbeck@bertelsmann.de", "ingo harbeck", 
							"Test subject", "Hello");
	}
}
