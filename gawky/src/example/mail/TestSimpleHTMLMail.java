package example.mail;

import gawky.global.Option;
import gawky.mail.SimpleMail;

public class TestSimpleHTMLMail {
 
	public static void main(String[] args) throws Exception {
		
		Option.init();
		
		SimpleMail mailer = new SimpleMail();
		
		mailer.setServer("mail.brillenmacher.com");
		mailer.setUsername("user768");
		mailer.setPassword("12345");
		mailer.addTO("ingo.harbeck@brillenmacher.com", "ingo");
		mailer.addImage("shit.gif", "shit.gif");
		mailer.setBody("<b>Hello 7ex</b><img src=\"cid:shit.gif\"/>");
		
		mailer.send();
		
		System.exit(0);
	}
}
