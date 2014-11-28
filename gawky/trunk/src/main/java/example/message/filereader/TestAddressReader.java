package example.message.filereader;

import gawky.file.CancelException;
import gawky.file.LineHandler;
import gawky.file.LineReader;
import gawky.file.Locator;
import gawky.global.Option;
import gawky.message.parser.ParserException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestAddressReader implements LineHandler
{
	private static Log log = LogFactory.getLog(TestAddressReader.class);

	@Override
	public void processLine(String line) throws CancelException
	{
		try
		{
			Address address = new Address();
			address.parse(line);

			// do something
			log.info(address.getVorname());
			log.info(address.getNachname());
		}
		catch(ParserException e)
		{
		}
	}

	public static void main(String[] args) throws Exception
	{
		Option.init();

		String filename = Locator.findPath("data.dat", TestAddressReader.class);

		LineReader.processFile(filename, new TestAddressReader());
	}
}