package example.mail;

import gawky.global.Option;
import gawky.mail.SimpleMail;

import java.io.FileInputStream;

public class TestMail
{
	public static void main(String[] args) throws Exception
	{
		Option.init();

		SimpleMail.create()
		        .addTo("ingo.harbeck@iharbeck.de")
		        .setSubject("schon bezahlt?")
		        .setBody("<h1>hello</h1>")

		        .addStream(new FileInputStream("c:/export.txt"), "rechnung.pdf")

		        .send();
	}
}
