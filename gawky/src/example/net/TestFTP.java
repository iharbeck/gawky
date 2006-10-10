package example.net;

import org.apache.log4j.Logger;

import gawky.net.FTPFactory;

public class TestFTP {

	private static Logger log = Logger.getLogger(TestFTP.class);

	public static void main(String[] args) throws Exception 
	{
		FTPFactory f = new FTPFactory("localhost", "root", "root");

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
		f.sendLocalFile("C:/work/gawky/build.xml");
		
		// File l�schen
		//f.deleteRemoteFile("build.xml");
		
		f.close();
	}

}