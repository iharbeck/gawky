package example.comm.client;

import example.message.parser.RequestHead;
import gawky.comm.Client;
import gawky.global.Constant;
import gawky.global.Option;

import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestClientOneSocket
{
	private static Log log = LogFactory.getLog(TestClientOneSocket.class);

	public static void main(String[] args) throws Exception
	{
		Option.init("properties.xml", "TestServer", args);

		Client client = new Client();

		RequestHead head = new RequestHead();

		head.setId("11");

		head.setSortcode("20090500");
		head.setAccount("7072007");

		head.setBinary("11");
		head.setVlist1("varone");
		head.setVlist2("vartwo");
		head.setDatum("20050805");

		String request = head.toString() +
		        "POS001234" +
		        "POS002234" +
		        "POS003234" + "\n";
		//"ART00abcdef";

		Socket socket = null;

		try
		{
			socket = new Socket(client.host, client.port);

			client.setAll(false);

			for(int i = 0; i < 5; i++)
			{
				String response = client.sendRequest(request, socket, Constant.ENCODE_ISO);
				log.info("##" + response);
			}
			log.info("DONE");
		}
		finally
		{
			socket.shutdownInput();
			socket.shutdownOutput();

			if(socket != null)
			{
				socket.close();
			}
		}
	}
}
