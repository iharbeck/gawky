package example.comm.client;

import gawky.comm.Client;

public class TestCRMClient
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		String server = "crmtest.bertelsmann.de";
		int port = 31682;

		String keystore = "D:/work/sslclient/otpcrmtest2/psptst3_crmtest_keystore";
		String storepass = "otp123";
		String keypass = "otp123";

		if(args.length == 3)
		{
			keystore = args[0];
			storepass = args[1];
			keypass = args[2];
		}

		Client client = new Client(server, port, keystore, storepass, keypass);

		client.sendRequestSSL("TES00");
	}

}
