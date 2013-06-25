package example.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gawky.file.Ftp;

public class TestFTP {

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
		for(int i=0; i < files.length; i++)
			f.renameRemoteFile(files[i], "/targ/archiv/" + files[i] + ".done");
		

		// File auf Server senden
		f.sendLocalFiles("C:/work/gawky/build.xml");
		
		// File löschen
		//f.deleteRemoteFile("build.xml");
		
		f.close();
	}

}
