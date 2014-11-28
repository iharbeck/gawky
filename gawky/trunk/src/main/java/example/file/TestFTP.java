package example.file;

import gawky.file.Ftp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestFTP
{

	private static Log log = LogFactory.getLog(TestFTP.class);

	public static void main(String[] args) throws Exception
	{
		Ftp f = new Ftp("localhost", "root", "root");

		// Verzeichnisse setzen
		f.changeRemoteDir("/targ");
		f.changeLocalDir("c:/ingo/ftp/local");

		// Files holen
		String[] files = f.retrieveFiles(".xml");

		log.info("no files: " + files.length);

		// Files bearbeiten
		for(String file : files)
		{
			f.renameRemoteFile(file, "/targ/archiv/" + file + ".done");
		}

		// File auf Server senden
		f.sendLocalFiles("C:/work/gawky/build.xml");

		// File löschen
		//f.deleteRemoteFile("build.xml");

		f.close();
	}

}
