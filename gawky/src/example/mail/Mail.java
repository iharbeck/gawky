package example.mail;

import gawky.global.Option;

public class Mail {
 
	public static void main(String[] args) throws Exception {
		
		Option.init();
		
		gawky.mail.Mail.sendSimpleMail(
				            "ingo.harbeck@bertelsmann.de", "ingo harbeck", 
							"ingo.harbeck@bertelsmann.de", "ingo harbeck", 
							"Test subject", "Hello");
	}
}
