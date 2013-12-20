package gawky.file;

import java.io.File;

public abstract class BaseFtp implements URLInterface 
{
	public BaseFtp me;
	int port;
	String localdir;

	public abstract void open(String server, String user, String pass, int port, String option) throws Exception;

	public void retrieveFiles() throws Exception
	{
		retrieveFiles(null, false);
	}
	
	public abstract String[] retrieveFiles(String filefilter, boolean simulate) throws Exception;

	public abstract void renameRemoteFile(String src, String dest) throws Exception;
	
	public void renameLocaleFile(String src, String dest) throws Exception
	{
		new File(localdir + "/" + src).renameTo(new File(localdir + "/" + dest));
	}
	
	public abstract void sendLocalFiles(String src) throws Exception;
	
	public abstract void changeRemoteDir(String path) throws Exception; 

	public abstract void mkdir(String path) throws Exception;
	
	public abstract void chmod(int right, String path) throws Exception;
	
	public abstract void chown(int owner, String path) throws Exception;
	
	public abstract void chgrp(int owner, String path) throws Exception;
	
	
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

		if(uparser.getPort() != null)
			port = Integer.parseInt(uparser.getPort());
		
		me.open(uparser.getServer(), uparser.getUser(), uparser.getPass(), port, uparser.getOption());
		
		me.changeRemoteDir(targetfolder);
		me.changeLocalDir(sourcefolder);

		me.sendLocalFiles(sourcefilename);

//		if(targetfilename != null)
//			me.renameRemoteFile(sourcefilename, targetfilename);
		
		me.close();
	}

	public String[] retrieve(String url, String targetpath) throws Exception
	{
		return retrieve(url, targetpath, false);
	}
	
	public String[] retrieve(String url, String targetpath, boolean simulate) throws Exception
	{
		if(url == null)
			return null;
		
		URLParser uparser = new URLParser(url);
		
		String sourcepath = uparser.getServerpath();

		String sourcefolder   = Tool.getFolder(sourcepath);
        String sourcefilename = Tool.getFilename(sourcepath);
		
        String targetfolder   = Tool.getFolder(targetpath);
		String targetfilename = Tool.getFilename(targetpath);
		
		if(uparser.getPort() != null)
			port = Integer.parseInt(uparser.getPort());

		me.open(uparser.getServer(), uparser.getUser(), uparser.getPass(), port, uparser.getOption());
		
		me.changeRemoteDir(sourcefolder);
		me.changeLocalDir(targetfolder);

		String files[] = me.retrieveFiles(sourcefilename, simulate);
		
//		if(targetfilename != null)
//			me.renameLocaleFile(sourcefilename, targetfilename);
		
		me.close();
		
		return files;
	}

}
