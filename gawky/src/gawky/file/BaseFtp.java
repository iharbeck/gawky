package gawky.file;

import java.io.File;

public abstract class BaseFtp 
{
	public BaseFtp me;
	int port;
	String localdir;

	public abstract void open(String server, String user, String pass, int port) throws Exception;

	public void retrieveFiles() throws Exception
	{
		retrieveFiles(null);
	}
	
	public abstract String[] retrieveFiles(String filefilter) throws Exception;

	public abstract void renameRemoteFile(String src, String dest) throws Exception;
	
	public void renameLocaleFile(String src, String dest) throws Exception
	{
		new File(localdir + "/" + src).renameTo(new File(localdir + "/" + dest));
	}
	
	public abstract void sendLocalFiles(String src) throws Exception;
	
	public abstract void changeRemoteDir(String path) throws Exception; 

	public void changeLocalDir(String path) throws Exception {
		localdir = path;
		
		if(!localdir.endsWith("/"))
			localdir += "/";
	}
	
	public void modeASCII() throws Exception { }
	public void modeBINARY() throws Exception { }
	
	public abstract void deleteRemoteFile(String path) throws Exception;

	public abstract void close() throws Exception;


	public void send(String url, String sourcepath) throws Exception
	{
		if(url == null)
			return;

		URLParser uparser = new URLParser(url);
		
		String targetpath = uparser.getServerpath();
		
		String targetfolder   = Tool.getFolder(targetpath);
		String targetfilename = Tool.getFilename(targetpath);

		String sourcefolder   = Tool.getFolder(sourcepath);
		String sourcefilename = Tool.getFilename(sourcepath);

		me.open(uparser.getServer(), uparser.getUser(), uparser.getPass(), port);
		
		me.changeRemoteDir(targetfolder);
		me.changeLocalDir(sourcefolder);

		me.sendLocalFiles(sourcefilename);

		if(targetfilename != null)
			me.renameRemoteFile(sourcefilename, targetfilename);
		
		me.close();
	}

	public void retrieve(String url, String targetpath) throws Exception
	{
		if(url == null)
			return;
		
		URLParser uparser = new URLParser(url);
		
		String sourcepath = uparser.getServerpath();

		String sourcefolder   = Tool.getFolder(sourcepath);
        String sourcefilename = Tool.getFilename(sourcepath);
		
        String targetfolder   = Tool.getFolder(targetpath);
		String targetfilename = Tool.getFilename(targetpath);

		me.open(uparser.getServer(), uparser.getUser(), uparser.getPass(), port);
		
		me.changeRemoteDir(sourcefolder);
		me.changeLocalDir(targetfolder);

		me.retrieveFiles(sourcefilename);
		
		if(targetfilename != null)
			me.renameLocaleFile(sourcefilename, targetfilename);
		
		me.close();
	}

}
