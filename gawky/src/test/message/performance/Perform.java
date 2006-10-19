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
	public static void main(String[] args) throws Exception {

		long start = System.currentTimeMillis();
		
		Parser parser = new Parser();
		
		String line = "12345678901234567890";
		
		Address address = new Address();
		for(int i=0; i < 1000000; i++)
			address.parse(parser, line);

		// do something
		log.info(address.getVorname());
		log.info(address.getNachname());

		log.error("parsed in " + (System.currentTimeMillis() - start) + "ms");
	}

}
