package gawky.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Filetransfer {

	static Log log = LogFactory.getLog(Filetransfer.class);
	
	static URLInterface getInstance(String url) throws Exception
	{
		if(url.startsWith("scp://"))
			return new Scp();
		else if(url.startsWith("ftp://"))
			return new Ftp();
		else if(url.startsWith("sftp://"))
			return new Sftp();
		else 
			throw new Exception("invalid protocoll: " + url);
	}
	
	public static void send(String url, String sourcepath) throws Exception 
	{
		URLInterface inter = getInstance(url);

		inter.send(url, sourcepath);
	}
	
	public static void retrieve(String url, String targetpath) throws Exception 
	{
		URLInterface inter = getInstance(url);

		inter.retrieve(url, targetpath);
	}
}
