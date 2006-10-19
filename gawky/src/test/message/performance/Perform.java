package test.message.performance;

import example.message.filereader.Address;
import gawky.message.parser.Parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Perform {

	static Log log = LogFactory.getLog(Perform.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception 
	{
		Parser parser = new Parser();
		
		String line = "123456789012345678901234567890123456789012345678901234567890";
		
		Address address = new Address();

		long start = System.currentTimeMillis();

		for(int i=0; i < 1000000; i++)
		{
			address.setVorname(line.substring(0, 9));
			address.setNachname(line.substring(9, 19));
			address.setInfo1(line.substring(19, 29));
			address.setInfo2(line.substring(29, 39));
		}

		
		log.error("Referenz: parsed 1 million lines in " + (System.currentTimeMillis() - start) + "ms");

		start = System.currentTimeMillis();
		
		for(int i=0; i < 1000000; i++)
		{
			address.parse(parser, line);
		}
		// do something
		log.error(address.getVorname());
		log.error(address.getNachname());

		
		
		log.error("Parser: parsed 1 million lines in " + (System.currentTimeMillis() - start) + "ms");

	
	}

}
