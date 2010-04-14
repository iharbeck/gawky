package example.mail;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import gawky.global.Option;
import gawky.mail.Mail;

public class TestMail {
 
    private static String DEFAULT_USER     = "mail.user";
    private static String DEFAULT_PASSWORD = "mail.password";
    private static String DEFAULT_SERVER   = "mail.server";

    
	public static void main(String[] args) throws Exception {
		
		Option.init();
		
//		Mail.sendSimpleMail(
//				            "ingo.harbeck@bertelsmann.de", "ingo harbeck", 
//							"ingo.harbeck@bertelsmann.de", "ingo harbeck", 
//							"Test subject", "Hello");
		
        String username = Option.getProperty(DEFAULT_USER);
        String password = Option.getProperty(DEFAULT_PASSWORD);
        String server   = Option.getProperty(DEFAULT_SERVER);
        
        Mail.sendMailGeneric(username, password, server, 
        		"ingo.harbeck@bertelsmann.de", "ingo harbeck", 
				"ingo.harbeck@iharbeck.de", "ingo harbeck", null, "Schon öäp bezahlt?", "<h1>hello</h1>", 
                           true, new FileInputStream("c:/export.txt"), "rechnung.pdf", false, null, null);

		
	}
}
