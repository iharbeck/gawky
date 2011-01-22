package example.mail;

import gawky.global.Option;
import gawky.mail.SimpleMail;
import gawky.mail.SimpleMailSender;

import java.io.FileInputStream;

public class TestMail {
 
	public static void main(String[] args) throws Exception {
		
		Option.init();
		
        SimpleMail mail = new SimpleMail();
        
        mail.addTo("ingo.harbeck@iharbeck.de");
        mail.setSubject("schon bezahlt?");
        mail.setBody("<h1>hello</h1>");
        
        mail.setStream(new FileInputStream("c:/export.txt"), "rechnung.pdf");
        
        SimpleMailSender server = new SimpleMailSender();
        
        server.send(mail);
	}
}
