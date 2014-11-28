package gawky.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class Sftp extends BaseFtp
{
	// SftpHandler.retrieve("ftp://user768:__@ftp.brillenmacher.com:/INBOX.*",
	// "c:/damdam/");

	public static void main(String[] args) throws Exception
	{
		System.out.println("START");
		// Filetransfer.retrieve("sftp://8a90818a14a769670114abe9959400eb:@test.ctpe.net:2222:/INBOX.*#c]/paykey.ppk",
		// "c:/");
		// Filetransfer.retrieve("sftp://arvatomedia_fias:k7569eop@84.17.174.9:ENTW/tes*",
		// "c:/");

		Filetransfer.send("sftp://arvatomedia_fias:k7569eop@84.17.174.9:ENTW/", "c:/test*");

		System.out.println("END");

		System.exit(0);
	}

	ChannelSftp sftpclient;
	Session session = null;

	public Sftp() throws Exception
	{
		this.me = this;
		this.port = 22;
	}

	public Sftp(String server, String user, String pass) throws Exception
	{
		this(server, user, pass, 22);
	}

	public Sftp(String server, String user, String pass, int port) throws Exception
	{
		open(server, user, pass, port, null);
	}

	@Override
	public void open(String server, String user, String pass, int port, String option) throws Exception
	{
		this.port = port;
		JSch jsch = new JSch();
		Channel channel = null;

		if(pass.equals("") || option != null)
		{
			jsch.addIdentity(option.replaceFirst("]", ":"));
		}

		session = jsch.getSession(user, server, port);
		// System.out.println("Session created.");
		session.setPassword(pass);

		UserInfo ui = new StaticUserInfo(pass);
		session.setUserInfo(ui);

		// alle Hosts akzeptierens
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);

		session.connect();
		// System.out.println("Session connected.");

		//
		// Open the SFTP channel
		//

		// System.out.println("Opening Channel.");
		channel = session.openChannel("sftp");
		channel.connect();
		sftpclient = (ChannelSftp)channel;

		this.me = this;
	}

	@Override
	public void mkdir(String path) throws Exception
	{
		sftpclient.mkdir(path);
	}

	@Override
	public void chmod(int right, String path) throws Exception
	{
		sftpclient.chmod(right, path);
	}

	@Override
	public void chown(int owner, String path) throws Exception
	{
		sftpclient.chown(owner, path);
	}

	@Override
	public void chgrp(int owner, String path) throws Exception
	{
		sftpclient.chgrp(owner, path);
	}

	@Override
	public String[] retrieveFiles(String filefilter, boolean simulate) throws Exception
	{
		Vector<LsEntry> vfiles = sftpclient.ls(".");

		filefilter = Tool.regbuilder(filefilter);

		ArrayList<String> files = new ArrayList<String>();

		for(int i = 0; i < vfiles.size(); i++)
		{
			LsEntry lsEntry = vfiles.get(i);

			if(lsEntry.getAttrs().isDir() || filefilter == null)
			{
				continue;
			}

			if(!(lsEntry.getFilename().matches(filefilter) || lsEntry.getFilename().endsWith(filefilter)))
			{
				continue;
			}

			String file = lsEntry.getFilename();

			files.add(file);

			if(simulate == false)
			{
				OutputStream out = new FileOutputStream(localdir + file);
				sftpclient.get(file, out);
				out.close();
			}
		}

		return files.toArray(new String[files.size()]);
	}

	@Override
	public void changeRemoteDir(String path) throws Exception
	{
		// if(path.endsWith("/") && path.length() > 1)
		// path = path.substring(0, path.length()-1);

		sftpclient.cd(path);
	}

	@Override
	public void deleteRemoteFile(String path) throws Exception
	{
		sftpclient.rm(path);
	}

	@Override
	public void renameRemoteFile(String src, String dest) throws Exception
	{
		sftpclient.rename(src, dest);
	}

	@Override
	public void close() throws Exception
	{
		try
		{
			sftpclient.disconnect();
		}
		catch(Exception e)
		{
		}

		try
		{
			sftpclient.quit();
		}
		catch(Exception e)
		{
		}

		try
		{
			session.disconnect();
		}
		catch(Exception e)
		{
		}
	}

	@Override
	public void sendLocalFiles(String filename) throws Exception
	{
		String tmp_prefix = ""; // ".temp";

		ArrayList<String> filesources = Tool.getFiles(localdir + filename);

		Iterator<String> it = filesources.iterator();

		while(it.hasNext())
		{
			String file = it.next();

			File f = new File(file);
			FileInputStream is = new FileInputStream(f);
			sftpclient.put(is, f.getName() + tmp_prefix);
			is.close();

			if(!tmp_prefix.equals(""))
			{
				renameRemoteFile(f.getName() + tmp_prefix, f.getName());
			}
		}
	}

	public static class StaticUserInfo implements UserInfo, UIKeyboardInteractive
	{
		@Override
		public String[] promptKeyboardInteractive(String destination,
		        String name, String instruction, String[] prompt, boolean[] echo)
		{
			return password.split("\n");
		}

		String password;

		public StaticUserInfo(String password)
		{
			this.password = password;
		}

		@Override
		public String getPassphrase()
		{
			return password;
		}

		@Override
		public String getPassword()
		{
			return password;
		}

		@Override
		public boolean promptPassphrase(String arg0)
		{
			return true;
		}

		@Override
		public boolean promptPassword(String arg0)
		{
			return true;
		}

		@Override
		public boolean promptYesNo(String arg0)
		{
			return true; // accept all
		}

		@Override
		public void showMessage(String arg0)
		{
		}
	}
}
