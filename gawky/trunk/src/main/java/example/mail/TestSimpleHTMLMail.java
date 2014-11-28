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

		mail.addTo("to@domain.com")
		        // .addImage("shit.gif", "shit.gif");
		        .setBody("<b>Hello äöü 7ex</b><img src=\"cid:shit.gif\"/>")
		        .setSubject("Hello")
		        .setFrom("from@domain.com")
		        .setNotify("notify@domain.com");

		SimpleMailSender server = SimpleMailSender.create()
		        .setServer("mail.domain.com")
		        .setUsername("from@domain.com")
		        .setPassword("xxx");

		server.send(mail);

		System.exit(0);
	}
}
