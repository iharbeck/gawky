package example.mail;

import gawky.global.Option;
import gawky.mail.SimpleMail;

public class TestHTMLMail
{
	public static void main(String[] args) throws Exception
	{
		Option.init();

		SimpleMail mail = new SimpleMail();

		mail.addTo("ingo.harbeck@iharbeck.de")
		        .setSubject("NEW NEW öäü")
		        .setBody("<b>Hello ööää 6ex</b><img src=\"cid:strutter.jpg\"/>")
		        .addImage("strutter.jpg", "strutter.jpg")
		        .send();

		System.exit(0);
	}
}
