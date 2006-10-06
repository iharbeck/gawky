package gawky.net;

import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPFactory {

	String REMOTE_DIR_SOURCE = "";
	String REMOTE_DIR_ARCHIV = "";
	
	String LOCAL_DIR = "";

	
	FTPClient ftp = null;
	
	public FTPFactory(String server, String user, String pass) throws Exception 
	{
		ftp = new FTPClient();

		//Connect to FTP Server
		ftp.connect(server);
		ftp.login(user, pass);
		
		System.out.println(ftp.getStatus());
	}

	public void close() throws Exception
	{
		ftp.logout();
		ftp.disconnect();
	}

	public void list() throws Exception
	{
		ftp.setFileTransferMode(FTP.ASCII_FILE_TYPE);

		FTPFile ftpFileList [] = null;
	
		ftpFileList = ftp.listFiles("/public_html/*.jsp");

		for(int i=0; i < ftpFileList.length; i++)
		{
			String file = ftpFileList[i].getName();
			System.out.println(file);
			
			FileOutputStream fos = new FileOutputStream("c:/dd/" + file); 
			
			ftp.retrieveFile(file, fos);
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		
		/***
		 *  sendFile();
		 *  
		 *  retrieveFile();
		 *  retrieveFiles();
		 *  
		 *  moveFile(); (rename)
		 *  
		 */
		
		FTPFactory f = new FTPFactory("ftp.brillenmacher.com", "user768", "yrrpejqu");
		
	
		f.list();
//		
//		String file = "c:/work/" + ftpFileList[0].getName();
//		
//		FileOutputStream fos = new FileOutputStream( file ); 
//
//		ftp.setFileTransferMode(FTP.ASCII_FILE_TYPE);
//		ftp.retrieveFile( ftpFileList[0].getName(), fos );
//		
//		ftp.rename("", "");
//		
//		ftp.deleteFile(ftpFileList[0].getName());
		
		f.close();
	}
}
