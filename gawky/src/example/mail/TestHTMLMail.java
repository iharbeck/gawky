package example.mail;

import java.util.ArrayList;

import gawky.global.Option;
import gawky.mail.Mail;

public class TestHTMLMail {
 
	public static void main(String[] args) throws Exception {
		
		Option.init();
		
		String username = Option.getProperty("mail.user");
        String password = Option.getProperty("mail.password");
        String host     = Option.getProperty("mail.server");
        
        ArrayList list = new ArrayList();
        
        list.add("strutter.jpg");
        
		Mail.sendMailGeneric(username, password, host,  
				            "ingo.harbeck@bertelsmann.de", "harb05", 
							"ingo.harbeck@bertelsmann.de", "harb05", 
							"Test subject new", "<b>Hello 6ex</b><img src=\"cid:strutter.jpg\"/>",
							 true, null, null, false, null, list);
		System.exit(0);
	}
}
