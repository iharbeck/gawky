package example.file;

import gawky.file.LineHandler;
import gawky.file.LineReader;
import gawky.file.Locator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestFile implements LineHandler
{
	private static Log log = LogFactory.getLog(TestFile.class);

	@Override
	public void processLine(String line)
	{
		System.out.println(line);
	}

	public static void main(String[] args) throws Exception
	{
		log.info("start");
		String filename = Locator.findPath("properties.xml");

		LineReader.processFile(filename, new TestFile());
	}
}
