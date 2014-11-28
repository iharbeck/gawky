package gawky.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Filetransfer
{

	static Log log = LogFactory.getLog(Filetransfer.class);

	//	public static void main(String[] args) throws Exception {
	//		send("sftp://content_reebok_arvato_test:azer2bist@ftp.minick.net:/import/", "c:/20070606KK.txt");
	//	}

	public static void main(String[] args) throws Exception
	{
		String[] ss = retrieve("sftp://harb05:$Sommer2030@csgpms30.server.arvato-systems.de:/CSGPMS30/PDIS/DBOPMS30/FIAS2/STREAMS/BFS-P-ALL-PAYON-DUMP-TO-BFS/*.sh", "c:/20070606KK.txt", true);

		for(String s : ss)
		{
			System.out.println(s);
		}
	}

	static URLInterface getInstance(String url) throws Exception
	{
		if(url.startsWith("scp://"))
		{
			return new Scp();
		}
		else if(url.startsWith("ftp://"))
		{
			return new Ftp();
		}
		else if(url.startsWith("sftp://"))
		{
			return new Sftp();
		}
		else if(url.startsWith("ftps://"))
		{
			return new Ftps();
		}
		else
		{
			throw new Exception("invalid protocoll: " + url);
		}
	}

	public static void send(String url, String sourcepath) throws Exception
	{
		URLInterface inter = getInstance(url);

		inter.send(url, sourcepath);
	}

	public static String[] retrieve(String url, String targetpath) throws Exception
	{
		return retrieve(url, targetpath, false);
	}

	public static String[] retrieve(String url, String targetpath, boolean simulate) throws Exception
	{
		URLInterface inter = getInstance(url);

		return inter.retrieve(url, targetpath, simulate);
	}
}
