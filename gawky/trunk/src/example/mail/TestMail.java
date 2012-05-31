package example.mail;

import gawky.global.Option;
import gawky.mail.SimpleMail;

import java.io.FileInputStream;

public class TestMail {
 
	public static void main(String[] args) throws Exception {
		
		Option.init();
		
        SimpleMail mail = new SimpleMail();
        
        mail.addTo("ingo.harbeck@iharbeck.de")
            .setSubject("schon bezahlt?")
            .setBody("<h1>hello</h1>")
        
            .setStream(new FileInputStream("c:/export.txt"), "rechnung.pdf")
            
            .send();
	}
}
