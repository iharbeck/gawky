package gawky.comm;


import gawky.global.Option;

import java.security.Security;


import org.apache.log4j.Logger;


public class Parameter 
{
	static final Logger log = Logger.getLogger(Parameter.class);

	
	public static int port    = 0;
	public static String host = "";
	
    static String keystore  = null;
    static String storepass = null;
    static String keypass   = null;
 
    static boolean ssl = false;
    
    public void initServer(String _host, int _port)
    {
    	host   = _host;
        port   = _port;
        ssl    = false;

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    }
    
    public void initServer(String _host, int _port, String _keystore, String _storepass, String _keypass)
    {
    	initServer(_host, _port);
    	keystore  = _keystore;
    	keypass   = _keypass;
    	storepass = _storepass;
    	ssl       = true;
    }
    
    public void initServer()
    {
    	initServer(Option.getHost(), Option.getPort());
    	keystore  = Option.getProperty("keystore");
    	keypass   = Option.getProperty("keypass");
    	storepass = Option.getProperty("storepass");
    	ssl       = Option.hasProperty("s");
    
    	log.info( "Starting " + (ssl ? "SSL" : "Plain") + " on Port " + port);
    }
}
