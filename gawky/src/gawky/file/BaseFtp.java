package gawky.file;

import java.io.File;

public abstract class BaseFtp 
{
	String localdir;
	
	public abstract void close() throws Exception;

	public void retrieveFiles() throws Exception
	{
		retrieveFiles(null);
	}
	
	public abstract String[] retrieveFiles(String filefilter) throws Exception;

	public abstract String[] retrieveFilesRegex(String regex) throws Exception;

	public abstract void renameRemoteFile(String src, String dest) throws Exception;
	
	public void renameLocaleFile(String src, String dest) throws Exception
	{
		new File(localdir + "/" + src).renameTo(new File(localdir + "/" + dest));
	}
	
	public abstract void sendLocalFile(String src) throws Exception;
	
	public abstract void changeRemoteDir(String path) throws Exception; 

	public void changeLocalDir(String path) throws Exception {
		localdir = path;
		
		if(!localdir.endsWith("/"))
			localdir += "/";
	}
	
	public void modeASCII() throws Exception { }
	public void modeBINARY() throws Exception { }
	
	public abstract void deleteRemoteFile(String path) throws Exception;
}
