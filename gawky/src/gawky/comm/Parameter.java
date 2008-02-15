package gawky.comm;


import gawky.global.Option;

import java.security.Security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Parameter 
{
	private static Log log = LogFactory.getLog(Parameter.class);

	
	public int port    = 0;
	public String host = "";
	
    String keystore  = null;
    String storepass = null;
    String keypass   = null;
 
    boolean ssl = false;
    
    public void initServer(String host, int port)
    {
    	this.host   = host;
    	this.port   = port;
    	this.ssl    = false;

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    }
    
    public void initServer(String host, int port, String keystore, String storepass, String keypass)
    {
    	initServer(host, port);
    	this.keystore  = keystore;
    	this.keypass   = keypass;
    	this.storepass = storepass;
    	this.ssl       = true;
    }
    
    public void initServer()
    {
    	initServer(Option.getProperty("server.host"), Integer.parseInt(Option.getProperty("server.port")));
    	keystore  = Option.getProperty("server.keystore");
    	keypass   = Option.getProperty("server.keypass");
    	storepass = Option.getProperty("server.storepass");
    	ssl       = Option.getProperty("server.type","plain").equals("ssl");
    
    	log.info( "Starting " + (ssl ? "SSL" : "Plain") + " on Port " + port);
    }
}
