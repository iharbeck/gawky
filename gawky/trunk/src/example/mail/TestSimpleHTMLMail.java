package example.mail;

import gawky.global.Option;
import gawky.mail.SimpleMail;
import gawky.mail.SimpleMailSender;

public class TestSimpleHTMLMail {
 
	public static void main(String[] args) throws Exception {
		
		Option.init();
		
		SimpleMailSender server = new SimpleMailSender();
		SimpleMail mail = new SimpleMail();
		
		server.setServer("mail.iharbeck.de");
		server.setUsername("ingo.harbeck@iharbeck.de");
		server.setPassword("login1996");
		mail.addTo("ingo.harbeck@iharbeck.de");
		//mailer.addImage("shit.gif", "shit.gif");
		mail.setBody("<b>Hello äöü 7ex</b><img src=\"cid:shit.gif\"/>");
		
		server.send(mail);
		
		System.exit(0);
	}
}
