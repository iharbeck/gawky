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
		
		String line = "1234567890abcdefghij1234567890123456789012345678901234567890";
		
		
		//Address address = new Address();

		long start = System.currentTimeMillis();

		Address address = null;
		for(int i=0; i < 1000000; i++)
		{
			address = new Address();
			address.setVorname(line.substring(0, 10));
			address.setNachname(line.substring(10, 20));
			address.setInfo1(line.substring(20, 30));
			address.setInfo2(line.substring(30, 40));
		}

		
		// do something
		log.error(address.getVorname());
		log.error(address.getNachname());
		
		log.error("Referenz: parsed 1 million lines in " + (System.currentTimeMillis() - start) + "ms");

		
		char[] intern = line.toCharArray();

		start = System.currentTimeMillis();
		
		
		for(int i=0; i < 1000000; i++)
		{
			address = new Address();
			address.setVorname(new String(intern, 0, 10));
			address.setNachname(new String(intern, 10, 20));
			address.setInfo1(new String(intern, 20, 30));
//			address.setInfo2(new String(intern, 30, 40));
		}
		
		
		log.error("Parser: parsed 1 million lines in " + (System.currentTimeMillis() - start) + "ms");

		// do something
		log.error(address.getVorname());
		log.error(address.getNachname());

		
		
		start = System.currentTimeMillis();
		
		for(int i=0; i < 1000000; i++)
		{
			address = new Address();
			address.parse(parser, line);
			//address.toString();
		}
		
		
		log.error("Parser: parsed 1 million lines in " + (System.currentTimeMillis() - start) + "ms");

		// do something
		log.error(address.getVorname());
		log.error(address.getNachname());

	}

}
