package gawky.comm;


import gawky.global.Constant;

import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Ingo Harbeck
 *
 */


/**
 * Implements plain socket communication. The request is send to a tcp socket and
 * the server response is returned.
 *
 * @param server dnsname or ip of server
 * @param port portnumer
 * @param request request string
 * @return server response
 */

public class Client extends Parameter
{
	private static Log log = LogFactory.getLog(Client.class);
	
	boolean all = true;
	
	//System.setProperty("javax.net.ssl.trustStore", Comm.keystore);
    //System.setProperty("javax.net.ssl.trustStorePassword", Comm.storepass);
    
	public Client()
	{
		initServer();
	}
	
	public Client(String _host, int _port)
    {
    	initServer(_host, _port);
    }
    
	public Client(String _host, int _port, String _keystore, String _storepass, String _keypass)
	{
		initServer(_host, _port, _keystore, _storepass, _keypass);
	}
   
	
	public String sendRequestPlain(String req) throws Exception
    {
    	return sendRequestPlain(req, Constant.ENCODE_ISO, 0);
    }
	
    public String sendRequestPlain(String req, int timeout) throws Exception
    {
    	return sendRequestPlain(req, Constant.ENCODE_ISO, timeout);
    }
    
    public String sendRequestPlain(String req, String encode, int timeout) throws Exception
    {
        Socket socket = null;
        
		try {
	    	socket = new Socket(host, port);
	    	socket.setSoTimeout(timeout);
	    	return sendRequest(req, socket, encode);
		}
		finally {
			if(socket != null)
			{
				socket.shutdownInput();
				socket.shutdownOutput();
			
				socket.close();
			}
		}
    }
    
    public String sendRequestSSL(String req) throws Exception
    {
    	return sendRequestSSL(req, Constant.ENCODE_ISO);
    }
    
    public String sendRequestSSL(String req, String encode) throws Exception
    {
    	SSLSocket socket = null;
    	
    	try 
    	{
    	   log.info("Factory erstellen...");
    	   SSLSocketFactory factory = null;
    	   SSLContext ctx;
		
    	   KeyManagerFactory kmf;
    	   KeyStore ks;

    	   ctx = SSLContext.getInstance("TLS");
    	   kmf = KeyManagerFactory.getInstance("SunX509");
    	   ks  = KeyStore.getInstance("JKS");
    	   
    	   ks.load(new FileInputStream(keystore), storepass.toCharArray());
    	   kmf.init(ks, keypass.toCharArray());
    	   
    	   TrustManagerFactory tmf;
    	   TrustManager[] tm;
    	    
    	   tmf = TrustManagerFactory.getInstance("SunX509");
    	   tmf.init(ks);
    	   tm = tmf.getTrustManagers();
    	      
    	   ctx.init(kmf.getKeyManagers(), tm, null);

    	   factory = ctx.getSocketFactory();
	    
           log.info("Factory wurde erstellt.");
           log.info("Verbindung aufbauen...");
	   
           socket = (SSLSocket)factory.createSocket(host, port);
           
           log.info("Verbindung Handshake...");
           
           socket.startHandshake();
           log.info("Verbindung wurde hergestellt");

	       return sendRequest(req, socket, encode);
		}
		finally {
	          if(socket != null) socket.close();
		}
    }
    
    
    public String sendRequest(String req, Socket socket, String encode) throws Exception
    {
    	IO.write(socket, req, encode);
            
        // Lese Antwort
	    String response = null;
	    
	    if(isAll())
	    	response = IO.readLineAll(socket, encode);
	    else
	    	response = IO.readLine(socket, encode);
	    	
        return response;
	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}
    
}


