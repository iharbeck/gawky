package gawky.service.bcoscs;

import gawky.file.LineHandler;
import gawky.file.LineReader;
import gawky.file.Locator;
import gawky.message.parser.Parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestFile implements LineHandler {
	
	private static Log log = LogFactory.getLog(TestFile.class);
	
	RecordEXTI00 record = new RecordEXTI00();
	
	int count = 0;
	Parser parser = new Parser();

	public void processLine(String line) 
	{
		try 
		{
			log.error(line);
			
			if(line.startsWith("EXTI00"))
			{
//				record = new RecordEXTI00();
//				record.parse(parser, line);
//				
//				log.warn(record.getStreet());
				count++;
			}
		} catch (Exception e) {
			log.error(line, e);
		}
	}
	
	public static void main(String[] args) throws Exception 
	{
		log.warn("start");
		
		String filename = Locator.findPath("bcos.dat", TestFile.class);
		
		TestFile file = new TestFile();
		
		LineReader.processUTF8File(filename, file, 
						new String[] {"EXTI00", "EXTI01", "HEAD"} );
		
		log.warn("done: " + file.count);
	}
}
