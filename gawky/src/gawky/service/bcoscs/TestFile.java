package gawky.service.bcoscs;

import gawky.file.LineHandler;
import gawky.file.LineReader;
import gawky.file.Locator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestFile implements LineHandler {
	
	private static Log log = LogFactory.getLog(TestFile.class);
	
	RecordEXTI record = new RecordEXTI();
	
	public void processLine(String line) 
	{
		try {
			if(line.startsWith("EXTI"))
			{
				record.parse(line);
				log.warn( record.toDebugString() );
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public static void main(String[] args) throws Exception 
	{
		String filename = Locator.findPath("bcos.dat", TestFile.class);
		
		LineReader.processFile(filename, new TestFile());
	}
}
