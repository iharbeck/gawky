/**
 * @author Ingo Harbeck
 * @version
 */

package gawky.comm;


import gawky.global.Option;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Server extends Parameter
{
	private static Log log = LogFactory.getLog(Server.class);

	private static String HOST     = "server.host";
	private static String PORT     = "server.port";
	private static String TIMEOUT  = "server.timeout";
	private static String IGNOREIP = "server.ignoreip";

	private static String SSLMODE = "s";
	
	protected static ThreadGroup threads = new ThreadGroup("Worker");
    
    Class actionclass = null;

    boolean ssl_clientauth = true;
    
    public Server(Class actionclass)
	{
    	this.actionclass = actionclass;
		initServer();
	}
	
    public Server(int _port, Class actionclass)
    {
    	this.actionclass = actionclass;
    	initServer("", _port);
    }
    
	public Server(String _host, int _port, Class actionclass)
    {
		this.actionclass = actionclass;
    	initServer(_host, _port);
    }
    
	public Server(String _host, int _port, String _keystore, String _storepass, String _keypass, Class actionclass)
	{
		this.actionclass = actionclass;
		initServer(_host, _port, _keystore, _storepass, _keypass);
	}
    
    public void start() throws Exception 
    {
        ServerSocket ss = null;
        
        ShutdownHook sh = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(sh);

        try {
	        if(!ssl)
	            ss = new ServerSocket(port);  // Default Socket
	        else
	        {
		        KeyManagerFactory kmf;
		        KeyManager[] km;
		        KeyStore ks;
		        TrustManagerFactory tmf;
		        TrustManager[] tm;
		        SSLContext sslc;
		        
		        ks = KeyStore.getInstance("JKS");
		        
		        ks.load(new FileInputStream(keystore), storepass.toCharArray());
		        
		        kmf = KeyManagerFactory.getInstance("SunX509");
		        kmf.init(ks, keypass.toCharArray());
		        
		        km = kmf.getKeyManagers();
		        
		        tmf = TrustManagerFactory.getInstance("SunX509");
		        tmf.init(ks);
		        
		        tm = tmf.getTrustManagers();
		        
		        sslc = SSLContext.getInstance("TLS");
		        sslc.init(km, tm, null);
		        
		        SSLServerSocketFactory ssf = sslc.getServerSocketFactory();
		        
		        ss = ssf.createServerSocket(port);
		        ((SSLServerSocket)ss).setNeedClientAuth(isSsl_clientauth());
	        }
        } catch (Exception e) {
        	log.fatal(e);
        	throw e;
        }
        log.info("ACCEPTING CONNECTIONS");

        while (true)
        {
            Worker w = new Worker(ss.accept(), actionclass, threads);
            w.start();
        }
    }
    
	public boolean isSsl_clientauth() {
		return ssl_clientauth;
	}

	public void setSsl_clientauth(boolean _ssl_clientauth) {
		ssl_clientauth = _ssl_clientauth;
	}

	public static boolean isValidIP(String addr) 
	{
		String val[] = Option.getProperties(IGNOREIP);
		
		if(val == null)
			return true;

		for(int i=0 ; i< val.length; i++)
		{
			if(val[i].equals(addr))
				return false;
		}

		return true;
	}
	
	public static int getTimeout()
	{
		return Integer.parseInt(Option.getProperty(TIMEOUT, "10000")) * 100;
	}
	
	public static int getPort()
	{
		return Integer.parseInt(Option.getProperty(PORT, "3000"));
	}
	
	public static String getHost()
	{
		return Option.getProperty(HOST);
	}


	
    public class Worker extends Thread
    {
        private Socket socket = null;
        private ThreadWorker action;
        
        private boolean batchmode = false;

        public Worker(Socket socket, Class action, ThreadGroup group) 
        {
        	super(group, "t");
        	
        	this.socket = socket;
        	try {
        		this.action = (ThreadWorker)actionclass.newInstance();
        	} catch(Exception e) {}
        }
        
        
        public void run() 
        {
        	try {
            	// Default timeout setzen
            	//socket.setSoTimeout(Option.getTimeout());
            	//socket.setKeepAlive(false);
            	 
            	if(!Server.isValidIP(socket.getInetAddress().getHostAddress())) 
            		return;
            	
            	log.info("Starting Thread");
            	
            	do {
            		action.execute(this, socket);
            	} while(isBatchmode());  // Keep Connection in Batchmode
            	
            } catch (Exception e) {
                log.error(" IN SERVER" + e);
            } finally {
                try { 
                	// Clean up
                	socket.shutdownInput();
        			socket.shutdownOutput();
                	socket.close(); 
                } catch (Exception e) { }
            }
        }



		public boolean isBatchmode() {
			return batchmode;
		}


		public void setBatchmode(boolean batchmode) {
			this.batchmode = batchmode;
		}
    }

}




class ShutdownHook extends Thread 
{
	private static Log log = LogFactory.getLog(ShutdownHook.class);
	
    public void run() 
    {
    	log.info(" Shutdown hook called");
    	
    	while(false && Server.threads.activeCount() > 0)
    	{
    		Object obj = new Object();
        	try { 
        		log.info(" WAITING FOR: " + Server.threads.activeCount());
        		
        		synchronized(obj)
    			{
    				obj.wait(1000);
    			}
    		} catch(Exception e) {
    			log.error("Shutdown", e);
    		}
    	}
    }
}