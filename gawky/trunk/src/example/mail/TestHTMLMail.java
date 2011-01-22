package example.mail;

import gawky.global.Option;
import gawky.mail.SimpleMail;
import gawky.mail.SimpleMailSender;

public class TestHTMLMail {
 
	public static void main(String[] args) throws Exception {
		
		Option.init();
		
        SimpleMail mail = new SimpleMail();
        
        mail.addTo("ingo.harbeck@iharbeck.de");
        mail.setSubject("NEW NEW öäü");
        mail.setBody("<b>Hello ööää 6ex</b><img src=\"cid:strutter.jpg\"/>");
        mail.addImage("strutter.jpg", "strutter.jpg");
        
        SimpleMailSender server = new SimpleMailSender();
        
        server.send(mail);
		
		System.exit(0);
	}
}
