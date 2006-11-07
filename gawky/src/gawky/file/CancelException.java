package gawky.file;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CancelException extends Exception 
{
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(CancelException.class);
	
	public CancelException(String value) {
		
		super(value);
		
		log.error(value);
	}
}
