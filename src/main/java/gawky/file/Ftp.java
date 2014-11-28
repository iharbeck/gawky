package gawky.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class Ftp extends BaseFtp
{
	//FtpHandler.send("ftp://user768:_@ftp.brillenmacher.com:/", "c:/damdam/*.ddd");

	private static Log log = LogFactory.getLog(Ftp.class);

	FTPClient ftp;
	String localdir;

	public Ftp() throws Exception
	{
		this.me = this;
		this.port = 21;
	}

	public Ftp(String server, String user, String pass) throws Exception
	{
		this(server, user, pass, 21);
	}

	public Ftp(String server, String user, String pass, int port) throws Exception
	{
		open(server, user, pass, port, null);
	}

	public static void mkdirhost(String url) throws Exception
	{
		URLParser uparser = new URLParser(url);

		Ftp ftp = new Ftp(uparser.getServer(), uparser.getUser(), uparser.getPass(), Integer.parseInt(uparser.getPort()));

		try
		{
			ftp.mkdir(uparser.getServerpath());
		}
		finally
		{
			ftp.close();
		}
	}

	@Override
	public void open(String server, String user, String pass, int port, String option) throws Exception
	{
		log.info("Login to FTP: " + server);

		this.port = port;
		ftp = new FTPClient();

		//Connect to FTP Server
		ftp.connect(server, port);
		ftp.login(user, pass);

		checkReply("FTP server refused connection." + server);

		modeBINARY();

		this.me = this;
	}

	@Override
	public void close() throws Exception
	{
		ftp.logout();
		ftp.disconnect();
		log.info("FTP Connection closed");
	}

	@Override
	public void mkdir(String path) throws Exception
	{
		ftp.makeDirectory(path);
	}

	@Override
	public void chmod(int right, String path) throws Exception
	{
	}

	@Override
	public void chown(int owner, String path) throws Exception
	{
	}

	@Override
	public void chgrp(int owner, String path) throws Exception
	{
	}

	@Override
	public void retrieveFiles() throws Exception
	{
		retrieveFiles(null, false);
	}

	@Override
	public String[] retrieveFiles(String filefilter) throws Exception
	{
		return retrieveFiles(filefilter, false);
	}

	@Override
	public String[] retrieveFiles(String filefilter, boolean simulate) throws Exception
	{
		FTPFile ftpFileList[] = ftp.listFiles();

		filefilter = Tool.regbuilder(filefilter);

		ArrayList<String> files = new ArrayList<String>();

		for(int i = 0; i < ftpFileList.length; i++)
		{
			if(!ftpFileList[i].isFile() || filefilter == null)
			{
				continue;
			}

			if(!(ftpFileList[i].getName().matches(filefilter) || ftpFileList[i].getName().endsWith(filefilter)))
			{
				continue;
			}

			String file = ftpFileList[i].getName();

			files.add(file);
			log.info("downloading: " + file);

			FileOutputStream fos = new FileOutputStream(localdir + file);

			if(simulate == false)
			{
				ftp.retrieveFile(file, fos);
			}

			fos.close();
		}

		return files.toArray(new String[files.size()]);
	}

	@Override
	public void renameRemoteFile(String src, String dest) throws Exception
	{
		ftp.rename(src, dest);

		checkReply("rename failed:" + src);
	}

	@Override
	public void renameLocaleFile(String src, String dest) throws Exception
	{
		new File(localdir + "/" + src).renameTo(new File(localdir + "/" + dest));
	}

	public final void checkReply(String info) throws Exception
	{
		if(!FTPReply.isPositiveCompletion(ftp.getReplyCode()))
		{
			log.error(info);
			throw new Exception(info);
		}
	}

	@Override
	public void sendLocalFiles(String filename) throws Exception
	{
		String tmp_prefix = ".temp";
		ArrayList<String> filesources = Tool.getFiles(localdir + filename);

		Iterator<String> it = filesources.iterator();

		while(it.hasNext())
		{
			String file = it.next();

			File f = new File(file);
			FileInputStream is = new FileInputStream(f);
			ftp.storeFile(f.getName() + tmp_prefix, is);
			is.close();

			renameRemoteFile(f.getName() + tmp_prefix, f.getName());

			checkReply("send failed: " + f.getName());
		}
	}

	@Override
	public void changeRemoteDir(String path) throws Exception
	{

		if(path.endsWith("/") && path.length() > 1)
		{
			path = path.substring(0, path.length() - 1);
		}

		ftp.changeWorkingDirectory(path);

		checkReply("set remote dir failed: " + path);
	}

	@Override
	public void changeLocalDir(String path) throws Exception
	{
		localdir = path;

		if(!localdir.endsWith("/"))
		{
			localdir += "/";
		}
	}

	@Override
	public void modeASCII() throws Exception
	{
		ftp.setFileType(FTP.ASCII_FILE_TYPE);
	}

	@Override
	public void modeBINARY() throws Exception
	{
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
	}

	@Override
	public void deleteRemoteFile(String path) throws Exception
	{
		ftp.deleteFile(path);

		checkReply("delete failed: " + path);
	}
}
