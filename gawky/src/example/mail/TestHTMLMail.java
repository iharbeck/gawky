package example.mail;

import gawky.global.Option;
import gawky.mail.Mail;

import java.util.Hashtable;

public class TestHTMLMail {
 
	public static void main(String[] args) throws Exception {
		
		Option.init();
		
		String username = Option.getProperty("mail.user");
        String password = Option.getProperty("mail.password");
        String host     = Option.getProperty("mail.server");
        
        Hashtable list = new Hashtable();
        
        list.put("strutter.jpg", "strutter.jpg");
        
		Mail.sendMailGeneric(username, password, host,  
				            "ingo.harbeck@bertelsmann.de", "harb05", 
							"ingo.harbeck@bertelsmann.de", "harb05", null,
							"Test subject new", "<b>Hello 6ex</b><img src=\"cid:strutter.jpg\"/>",
							 true, null, null, false, null, list);
		System.exit(0);
	}
}
