package gawky.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class Ftp extends BaseFtp
{
	private static Log log = LogFactory.getLog(Ftp.class);

	FTPClient ftp = null;
	String localdir;
	int port = 21;

	public Ftp(String server, String user, String pass) throws Exception 
	{
		this(server, user, pass, 21);
	}
	
	public Ftp(String server, String user, String pass, int port) throws Exception 
	{
		log.info("Login to FTP: " + server);
	
		this.port = port;
		ftp = new FTPClient();
		
		//Connect to FTP Server
		ftp.connect(server, port);
		ftp.login(user, pass);
		
		checkReply("FTP server refused connection." + server);

		modeBINARY();
	}

	public void close() throws Exception
	{
		ftp.logout();
		ftp.disconnect();
		log.info("FTP Connection closed");
	}

	public void retrieveFiles() throws Exception
	{
		retrieveFiles(null);
	}
	
	public String[] retrieveFiles(String filefilter) throws Exception
	{
		FTPFile ftpFileList [] = null;
	
		ftpFileList = ftp.listFiles();

		ArrayList files = new ArrayList();
		
		for(int i=0; i < ftpFileList.length; i++)
		{
			if(!ftpFileList[i].isFile() || 
			   (filefilter != null && !ftpFileList[i].getName().endsWith(filefilter)))
				continue;
			
			String file = ftpFileList[i].getName();
		
			files.add(file);
			log.info("downloading: " + file);
			
			FileOutputStream fos = new FileOutputStream(localdir + file); 
			
			ftp.retrieveFile(file, fos);
		}
		
		return (String[])files.toArray(new String[files.size()]);
	}

	public String[] retrieveFilesRegex(String regex) throws Exception
	{
		FTPFile ftpFileList [] = null;
	
		ftpFileList = ftp.listFiles();

		ArrayList files = new ArrayList();
		
		for(int i=0; i < ftpFileList.length; i++)
		{
			if(!ftpFileList[i].isFile() || (regex != null &&
			   !ftpFileList[i].getName().matches(regex)))
				continue;
			
			String file = ftpFileList[i].getName();
		
			files.add(file);
			log.info("downloading: " + file);
			
			FileOutputStream fos = new FileOutputStream(localdir + file); 
			
			ftp.retrieveFile(file, fos);
		}
		
		return (String[])files.toArray(new String[files.size()]);
	}

	public void renameRemoteFile(String src, String dest) throws Exception
	{
		ftp.rename(src, dest);

		checkReply("rename failed:" + src);
	}
	
	public void renameLocaleFile(String src, String dest) throws Exception
	{
		new File(localdir + "/" + src).renameTo(new File(localdir + "/" + dest));
	}
	
	public final void checkReply(String info) throws Exception {
		if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
	        log.error(info);
	        throw new Exception(info);
		}
	}
	
	public void sendLocalFile(String src) throws Exception
	{
		String tmp_prefix = ".temp";
		File f = new File(localdir + src);
		FileInputStream is = new FileInputStream(localdir + src);
		ftp.storeFile(f.getName() + tmp_prefix, is);
		is.close();

		renameRemoteFile(f.getName() + tmp_prefix, f.getName());

		checkReply("send failed: " + f.getName());
	}
	
	public void changeRemoteDir(String path) throws Exception {
		ftp.changeWorkingDirectory(path);
		
		checkReply("set remote dir failed: " + path);
	}

	public void changeLocalDir(String path) throws Exception {
		localdir = path;
		
		if(!localdir.endsWith("/"))
			localdir += "/";
	}
	
	public void modeASCII() throws Exception {
		ftp.setFileType(FTP.ASCII_FILE_TYPE);
	}

	public void modeBINARY() throws Exception {
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
	}
	
	public void deleteRemoteFile(String path) throws Exception {
		ftp.deleteFile(path);
		
		checkReply("delete failed: " + path);
	}
}
