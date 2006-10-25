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
	
	int count = 1;
	
	Parser parser = new Parser();
	String incl = null;
	public void processLine(String line) 
	{
		try 
		{
			// handle incomplete lines
//			if(incl != null) {
//				line = incl + line; 
//				incl = null;
//			}
			
			if(line.startsWith("EXTI00"))
			{
				record = new RecordEXTI00();
				record.parse(parser, line);
				
				// ein paar schöne UTF-8 Kandidaten
				if(record.getAccountcurrency().equals("ILS"))
					log.warn(record.getStreet());
				count++;
			}
		} catch (Exception e) {
//			incl = line;
			//log.error(line, e);
		}
	}
	
	public static void main(String[] args) throws Exception 
	{
		log.warn("start");
		
		String filename = Locator.findPath("BCS_DATEN_20061020_233048", TestFile.class);
		
		TestFile file = new TestFile();
		
		LineReader.processUTF8File(filename, file);
		
		log.warn("done: " + file.count);
	}
}
