package gawky.logging;

import gawky.global.Option;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggingSample
{
	private Log log = LogFactory.getLog(LoggingSample.class);

	public void writeLog()
	{
		log.error("writeLog Function", new Exception("TEST FEHLER"));
	}

	public static void main(String[] args) throws Exception
	{
		Option.init();
		
		LoggingSample logger = new LoggingSample();
		logger.writeLog();
	}
}