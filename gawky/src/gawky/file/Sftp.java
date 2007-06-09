package gawky.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class Sftp extends BaseFtp {

	ChannelSftp c = null;
	int port = 22;

	public Sftp(String server, String user, String pass) throws Exception {
		this(server, user, pass, 22);
	}
	
	public Sftp(String server, String user, String pass, int port) throws Exception 
	{
		this.port = port;
		JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;

        
        session = jsch.getSession(user, server, port);
        System.out.println("Session created.");    
        session.setPassword(pass);

        // alle Hosts akzeptierens
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        System.out.println("Session connected.");

        //
        //Open the SFTP channel
        //
        System.out.println("Opening Channel.");
        channel = session.openChannel("sftp");
        channel.connect();
        c = (ChannelSftp)channel;

	}
	
	public String[] retrieveFiles(String filefilter) throws Exception
	{
        Vector vfiles = c.ls(".");

		ArrayList files = new ArrayList();
		
		for(int i=0; i < vfiles.size(); i++)
		{
			LsEntry lsEntry = (LsEntry) vfiles.get(i);
			
			if(lsEntry.getAttrs().isDir() || 
			   (filefilter != null && !lsEntry.getFilename().endsWith(filefilter)))
				continue;
			
			String file = lsEntry.getFilename();
		
			files.add(file);
			
            c.get(file, new FileOutputStream(localdir + file));
		}
		
		return (String[])files.toArray(new String[files.size()]);
	}

	public String[] retrieveFilesRegex(String regex) throws Exception
	{
        Vector vfiles = c.ls(".");

		ArrayList files = new ArrayList();
		
		for(int i=0; i < vfiles.size(); i++)
		{
			LsEntry lsEntry = (LsEntry) vfiles.get(i);
			
			if(lsEntry.getAttrs().isDir() || 
			   (regex != null && !lsEntry.getFilename().matches(regex)))
				continue;
			
			String file = lsEntry.getFilename();
		
			files.add(file);
			
            c.get(file, new FileOutputStream(localdir + file));
		}
		
		return (String[])files.toArray(new String[files.size()]);
	}
	

	public void changeRemoteDir(String path) throws Exception {
		c.cd(path);
	}

	public void deleteRemoteFile(String path) throws Exception {
		c.rm(path);
	}

	public void renameRemoteFile(String src, String dest) throws Exception {
		c.rename(src, dest);
	}

	public void close() throws Exception 
	{
		c.quit();
	}
	
	public void sendLocalFile(String src) throws Exception
	{
		String tmp_prefix = ".temp";
		File f = new File(localdir + src);
		FileInputStream is = new FileInputStream(localdir + src);
		c.put(is, f.getName() + tmp_prefix);
		is.close();

		renameRemoteFile(f.getName() + tmp_prefix, f.getName());
	}
}
