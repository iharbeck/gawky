package example.file;

import gawky.file.LineHandler;
import gawky.file.LineReader;
import gawky.file.Locator;

public class TestFile implements LineHandler {
	
	public void processLine(String line) {
		System.out.println(line);
	}
	
	public static void main(String[] args) throws Exception 
	{
		String filename = Locator.findPath("properties.xml");
		
		LineReader.processFile(filename, new TestFile());
	}
}
