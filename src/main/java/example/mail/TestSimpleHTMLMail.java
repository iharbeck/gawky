package example.mail;

import gawky.global.Option;
import gawky.mail.SimpleMail;
import gawky.mail.SimpleMailSender;

public class TestSimpleHTMLMail
{
	public static void main(String[] args) throws Exception
	{
		Option.init();

		SimpleMail mail = new SimpleMail();

		mail.addTo("ingo.harbeck@iharbeck.de")
		        // .addImage("shit.gif", "shit.gif");
		        .setBody("<b>Hello äöü 7ex</b><img src=\"cid:shit.gif\"/>");

		SimpleMailSender server = SimpleMailSender.create()
		        .setServer("mail.iharbeck.de")
		        .setUsername("ingo.harbeck@iharbeck.de")
		        .setPassword("password");

		server.send(mail);

		System.exit(0);
	}
}
