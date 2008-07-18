package example.mail;

import java.io.FileInputStream;

import gawky.global.Option;
import gawky.mail.Mail;

public class TestMail {
 
	public static void main(String[] args) throws Exception {
		
		Option.init();
		
//		Mail.sendSimpleMail(
//				            "ingo.harbeck@bertelsmann.de", "ingo harbeck", 
//							"ingo.harbeck@bertelsmann.de", "ingo harbeck", 
//							"Test subject", "Hello");
		
		Mail.sendMailAttachStream("ingo.harbeck@bertelsmann.de", "ingo harbeck", 
				//"oliver.glowa@bertelsmann.de", "Andreas Thiesen", 
				"ingo.harbeck@bertelsmann.de", "ingo harbeck",
				"Mobile Rechnung", "Schon bezahlt?",
				  new FileInputStream("d:/ticket.pdf"), "rechnung.pdf");
		
	}
}
