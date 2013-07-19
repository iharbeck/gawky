package example.message.parser;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import example.global.Session;
import gawky.message.parser.Parser;
import gawky.message.parser.ParserException;

/**
 * @author Ingo Harbeck
 */

public class MultitypeParser 
{
	private static Log log = LogFactory.getLog(MultitypeParser.class);

	public final static int ERROR_MISSING_HEAD      = 2000;
	
	static {
		ParserException.addError(ERROR_MISSING_HEAD, "MISSING HEADER");
	}
	
	public static void parse(Session session, String request) throws ParserException
	{
		log.info("START"); 
		
		if(!request.startsWith("HEAD"))
			throw new ParserException(ERROR_MISSING_HEAD, request);
		
		String nextpart;
		Parser parser = new Parser();
		
		// HEAD parsen
		parser.parse(request, session.getHead());		
		nextpart = parser.getNext();
		
		// POS parsen
		while(nextpart.startsWith("POS"))
		{
			RequestPos pos = new RequestPos();
			
			parser.parse(nextpart, pos);
			nextpart = parser.getNext();
			
			session.getPositions().add(pos);
		}
		
		// additional message parts
		// ...
		
		// check extra data
		if(nextpart.length() > 0)
			throw new ParserException(ParserException.ERROR_EXTRA_DATA, nextpart);

		log.info("Parser finished");
	}
}
