package gawky.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.ftp4che.FTPConnection;
import org.ftp4che.FTPConnectionFactory;
import org.ftp4che.util.ftpfile.FTPFile;

//TODO !!!!

public class Ftps extends BaseFtp {

	// 	SftpHandler.retrieve("ftp://user768:__@ftp.brillenmacher.com:/INBOX.*", "c:/damdam/");

	String remotedir = "";
	
	public void updlaod()
	{
		

	        Properties pt = new Properties();
	        pt.setProperty("connection.host", "172.25.13.187");
	        pt.setProperty("connection.port", "21");
	        pt.setProperty("user.login", "ftpuser");
	        pt.setProperty("user.password", "ftp4che");
	        pt.setProperty("connection.type", "FTP_CONNECTION");
	        pt.setProperty("connection.timeout", "10000");
	        pt.setProperty("connection.passive", "true");

//	        FTPFile fromFile = new FTPFile("/tmp/", "testfile");
//
//	        try {
//	            FTPConnection connection = FTPConnectionFactory.getInstance(pt);
//
//	            try {
//	                connection.connect();
//
//	                FTPFile toFile = new FTPFile("/", "testfile_normal");
//	                connection.uploadFile(fromFile, toFile);
//	                
//	                connection.disconnect();
//	            } catch (NotConnectedException nce) {
//	                log.error(nce);
//	            } catch (IOException ioe) {
//	                log.error(ioe);
//	            } catch (Exception e) {
//	                log.error(e);
//	            }
//
//	            try {
//	                pt.setProperty("connection.type", "IMPLICIT_SSL_FTP_CONNECTION");
//	                pt.setProperty("connection.port", "990");
//	                connection = FTPConnectionFactory.getInstance(pt);
//	                connection.connect();
//	                FTPFile toFile = new FTPFile("/", "testfile_implicit_ssl");
//	                connection.uploadFile(fromFile, toFile);
//
//	                connection.disconnect();
//	            } catch (NotConnectedException nce) {
//	                log.error(nce);
//	            } catch (IOException ioe) {
//	                log.error(ioe);
//	            } catch (Exception e) {
//	                log.error(e);
//	            }
//
//	            try {
//	                pt.setProperty("connection.type", "IMPLICIT_TLS_FTP_CONNECTION");
//	                pt.setProperty("connection.port", "990");
//	                connection = FTPConnectionFactory.getInstance(pt);
//	                connection.connect();
//	                FTPFile toFile = new FTPFile("/", "testfile_implicit_tls");
//	                connection.uploadFile(fromFile, toFile);
//
//	                connection.disconnect();
//	            } catch (NotConnectedException nce) {
//	                log.error(nce);
//	            } catch (IOException ioe) {
//	                log.error(ioe);
//	            } catch (Exception e) {
//	                log.error(e);
//	            }
//
//	            try {
//	                pt.setProperty("connection.port", "21");
//	                pt.setProperty("connection.type", "AUTH_SSL_FTP_CONNECTION");
//	                connection = FTPConnectionFactory.getInstance(pt);
//	                connection.connect();
//
//	                FTPFile toFile = new FTPFile("/", "testfile_ssl");
//	                connection.uploadFile(fromFile, toFile);
//
//	                connection.disconnect();
//	            } catch (NotConnectedException nce) {
//	                log.error(nce);
//	            } catch (IOException ioe) {
//	                log.error(ioe);
//	            } catch (Exception e) {
//	                log.error(e);
//	            }
//
//	            try {
//	                pt.setProperty("connection.type", "AUTH_TLS_FTP_CONNECTION");
//	                connection = FTPConnectionFactory.getInstance(pt);
//	                connection.connect();
//
//	                FTPFile toFile = new FTPFile("/", "testfile_tls");
//	                connection.uploadFile(fromFile, toFile);
//
//	                connection.disconnect();
//	            } catch (NotConnectedException nce) {
//	                log.error(nce);
//	            } catch (IOException ioe) {
//	                log.error(ioe);
//	            } catch (Exception e) {
//	                log.error(e);
//	            }
//	        } catch (ConfigurationException ce) {
//	            log.error(ce);
//	        }
		
	}
	
	
	FTPConnection ftpsclient;

	public Ftps() throws Exception 
	{
		this.me = this; 
		this.port = 21;
	}

	public Ftps(String server, String user, String pass) throws Exception {
		this(server, user, pass, 21);
	}
	
	public Ftps(String server, String user, String pass, int port) throws Exception 
	{
		open(server, user, pass, port, null);
	}
	
	public static void main(String[] args) throws Exception {
		
		Ftps ff = new Ftps("145.228.7.146", "anonymous", "anonymous");
		
		ff.changeLocalDir("c:/");
		ff.changeRemoteDir("/ingo");
		ff.retrieveFiles("*");
		ff.close();
	}
	public void open(String server, String user, String pass, int port, String option) throws Exception 
	{
		this.port = port;
        this.me = this;

        Properties pt = new Properties();
        
        pt.setProperty("connection.host", server);
        pt.setProperty("connection.port", Integer.toString(port));
        pt.setProperty("user.login", user);
        pt.setProperty("user.password", pass);
        pt.setProperty("connection.type", "FTP_CONNECTION");
        //pt.setProperty("connection.type", "AUTH_SSL_FTP_CONNECTION");
        pt.setProperty("connection.port", "21"); //990
        pt.setProperty("connection.timeout", "10000");
        pt.setProperty("connection.passive", "true");
        
        
        ftpsclient = FTPConnectionFactory.getInstance(pt);
        ftpsclient.connect();
	}
	
	
	public void mkdir(String path) throws Exception
	{
		ftpsclient.makeDirectory(path);
	}
	
	public void chmod(int right, String path) throws Exception
	{
	}
	
	public void chown(int owner, String path) throws Exception
	{
	}
	
	public void chgrp(int owner, String path) throws Exception
	{
	}
	
	
	public String[] retrieveFiles(String filefilter) throws Exception
	{
		List<FTPFile> vfiles = ftpsclient.getDirectoryListing();
		
        filefilter = Tool.regbuilder(filefilter);
        
		ArrayList<String> files = new ArrayList<String>();
		
		for(int i=0; i < vfiles.size(); i++)
		{
			FTPFile lsEntry = (FTPFile) vfiles.get(i);
			
			if(lsEntry.isDirectory() || filefilter == null)
				continue;
			   
			if(! (lsEntry.getFile().getName().matches(filefilter) || 
				  lsEntry.getFile().getName().endsWith(filefilter)) )
				continue;
			
			String file = lsEntry.getFile().getName();
		
			files.add(file);
			
            ftpsclient.downloadFile(lsEntry, new FTPFile(localdir, file));
		}
		
		return (String[])files.toArray(new String[files.size()]);
	}	

	public void changeRemoteDir(String path) throws Exception {
		
//		if(path.endsWith("/") && path.length() > 1)
//			path = path.substring(0, path.length()-1);
		
		remotedir = path;
		
		ftpsclient.changeDirectory(path);
	}

	public void deleteRemoteFile(String file) throws Exception {
		//TODO
		ftpsclient.deleteFile(new FTPFile(remotedir, file));
	}

	public void renameRemoteFile(String src, String dest) throws Exception {
		// TODO
		ftpsclient.renameFile(new FTPFile(remotedir, src), new FTPFile(remotedir, dest));
	}

	public void close() throws Exception 
	{
		ftpsclient.disconnect();
	}
	
	public void sendLocalFiles(String filename) throws Exception
	{
		String tmp_prefix = ".temp";
		
		ArrayList<String> filesources = Tool.getFiles(localdir + filename);
		
		Iterator<String> it = filesources.iterator();
		
		while(it.hasNext())
		{
			String file = it.next();
			
			File filelocal = new File(file);
			String filenamelocal = filelocal.getName();
			
			ftpsclient.uploadFile(new FTPFile(filelocal), new FTPFile(remotedir, filenamelocal + tmp_prefix ));
	
			renameRemoteFile(filenamelocal + tmp_prefix, filenamelocal);
		}
	}
}
