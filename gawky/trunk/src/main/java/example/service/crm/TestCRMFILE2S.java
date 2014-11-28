/*
 * go.java
 *
 * Created on 6. August 2003, 10:55
 */

package example.service.crm;

import gawky.comm.Client;
import gawky.file.CancelException;
import gawky.file.LineHandler;
import gawky.file.LineReader;
import gawky.global.Constant;
import gawky.service.crm.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author  Ingo Harbeck
 */
public class TestCRMFILE2S implements LineHandler
{

	private static final Log log = LogFactory.getLog(TestCRMFILE2S.class);

	static Client client;

	public static void main(String[] args) throws Exception
	{
		String file = "d:/ss_settlements20060825.ss1";

		// Plainsocket
		client = new Client("crmtest.bertelsmann.de", 31680);

		LineReader.processFile(file, new TestCRMFILE2S());

		System.exit(0);
	}

	@Override
	public void processLine(String line) throws CancelException
	{
		log.info(line);

		if(!line.startsWith("HEAD"))
		{
			return;
		}

		try
		{
			String tmp = client.sendRequestPlain(line, Constant.ENCODE_ISO, 5000);
			log.info("response: " + tmp);

			Response response = new Response(tmp);

			log.info("returncode: " + response.getReturn_code());
			log.info("reasoncode: " + response.getReason_code());
		}
		catch(Exception e)
		{
		}
	}

}
