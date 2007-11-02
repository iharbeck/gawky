package gawky.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class Sftp extends BaseFtp {

	// 	SftpHandler.retrieve("ftp://user768:__@ftp.brillenmacher.com:/INBOX.*", "c:/damdam/");

	ChannelSftp sftpclient;

	public Sftp() throws Exception 
	{
		this.me = this; 
		this.port = 22;
	}

	public Sftp(String server, String user, String pass) throws Exception {
		this(server, user, pass, 22);
	}
	
	public Sftp(String server, String user, String pass, int port) throws Exception 
	{
		open(server, user, pass, port);
	}
	
	public void open(String server, String user, String pass, int port) throws Exception 
	{
		this.port = port;
		JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;

        
        session = jsch.getSession(user, server, port);
        //System.out.println("Session created.");    
        session.setPassword(pass);

        UserInfo ui = new StaticUserInfo(pass);
		session.setUserInfo(ui);
		
        // alle Hosts akzeptierens
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        //System.out.println("Session connected.");

        //
        //Open the SFTP channel
        //
        
        //System.out.println("Opening Channel.");
        channel = session.openChannel("sftp");
        channel.connect();
        sftpclient = (ChannelSftp)channel;
        
        this.me = this;
	}
	
	public String[] retrieveFiles(String filefilter) throws Exception
	{
        Vector vfiles = sftpclient.ls(".");

        filefilter = Tool.regbuilder(filefilter);
        
		ArrayList files = new ArrayList();
		
		for(int i=0; i < vfiles.size(); i++)
		{
			LsEntry lsEntry = (LsEntry) vfiles.get(i);
			
			if(lsEntry.getAttrs().isDir() || filefilter == null)
				continue;
			   
			if(! (lsEntry.getFilename().matches(filefilter) || 
				  lsEntry.getFilename().endsWith(filefilter)) )
				continue;
			
			String file = lsEntry.getFilename();
		
			files.add(file);
			
            sftpclient.get(file, new FileOutputStream(localdir + file));
		}
		
		return (String[])files.toArray(new String[files.size()]);
	}	

	public void changeRemoteDir(String path) throws Exception {
		
//		if(path.endsWith("/") && path.length() > 1)
//			path = path.substring(0, path.length()-1);
		
		sftpclient.cd(path);
	}

	public void deleteRemoteFile(String path) throws Exception {
		sftpclient.rm(path);
	}

	public void renameRemoteFile(String src, String dest) throws Exception {
		sftpclient.rename(src, dest);
	}

	public void close() throws Exception 
	{
		sftpclient.quit();
	}
	
	public void sendLocalFiles(String filename) throws Exception
	{
		String tmp_prefix = ".temp";
		
		ArrayList filesources = Tool.getFiles(localdir + filename);
		
		Iterator it = filesources.iterator();
		
		while(it.hasNext())
		{
			String file = (String)it.next();

			File f = new File(file);
			FileInputStream is = new FileInputStream(f);
			sftpclient.put(is, f.getName() + tmp_prefix);
			is.close();
	
			renameRemoteFile(f.getName() + tmp_prefix, f.getName());
		}
	}
	
	public static class StaticUserInfo implements UserInfo, UIKeyboardInteractive  {

		public String[] promptKeyboardInteractive(String destination,
				String name, String instruction, String[] prompt, boolean[] echo) {
			return new String [] { password };
		}

		String password;
		
		public StaticUserInfo(String password) {
			this.password = password;
		}
		
		public String getPassphrase() {
			return password;
		}

		public String getPassword() {
			return password;
		}

		public boolean promptPassphrase(String arg0) {
			return true;
		}

		public boolean promptPassword(String arg0) {
			return true;
		}

		public boolean promptYesNo(String arg0) {
			return true;  // accept all
		}

		public void showMessage(String arg0) {
		}
	}
}
