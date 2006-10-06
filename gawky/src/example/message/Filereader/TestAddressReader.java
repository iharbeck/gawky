package example.message.Filereader;


import gawky.file.LineHandler;
import gawky.file.LineReader;
import gawky.file.Locator;

import org.apache.log4j.Logger;


public class TestAddressReader implements LineHandler 
{
	static final Logger log = Logger.getLogger(TestAddressReader.class);
	
	public void processLine(String line) throws Exception 
	{
		Address address = new Address();
		address.parse(line);
		
		// do something
		log.info(address.getVorname());
		log.info(address.getNachname());
	}

	public static void main(String[] args) throws Exception 
	{
		String filename = Locator.findPath("data.dat", TestAddressReader.class);
	
		LineReader.processFile(filename, new TestAddressReader());
	}
}