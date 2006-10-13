package example.comm.server;


import example.global.Session;
import example.message.parser.MultitypeParser;
import example.message.parser.ResponseRes;
import example.service.oliva.ActionOliva;
import gawky.comm.IO;
import gawky.comm.Server;
import gawky.comm.ThreadWorker;
import gawky.global.Constant;
import gawky.global.Option;
import gawky.message.parser.ParserException;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestServer implements ThreadWorker 
{
	private static Log log = LogFactory.getLog(TestServer.class);

	
	public void execute(Server.Worker thread, Socket s) throws Exception
	{
		log.info("## IN WORKER");
		
		thread.setBatchmode(true);
		//s.setSoTimeout(10000);
	    
		Session session = new Session();
		
		try {
			// Read from Socket \n terminated line!
			String line = IO.readLine(s, Constant.ENCODE_ISO);
			
			
			// Sample Parsing
			MultitypeParser.parse(session, line);

			// Sample Olivarequest
			ActionOliva.doSettlement(session);
			
			// Do some Action
			session.setResponse("0000");
			session.setDetails("bank <" + session.getHead().getSortcode() + ":" + session.getHead().getAccount() + ">");
	    }
		catch(SocketTimeoutException e) {
			log.error(e);
	    }
		catch(IOException e) {
			thread.setBatchmode(false);
			log.info("Batch terminated unexpected");
	    }
		catch (ParserException e) {
			log.error("response_code: " + e.getRetcode());
			session.setResponse(Integer.toString(e.getRetcode()));
			session.setDetails("PARSING ERROR ");
		} 
		finally {	
			// sendResponse
			ResponseRes res = new ResponseRes();
			res.setCode(session.getResponse());
			res.setDetail(session.getDetails());
			
			String response = res.toString();
			IO.writeLine(s, response);
		}
	}
	
	
	public static void main(String[] args) throws Exception 
	{
		Option.init("properties.xml", "TestServer", args);
		
		// External configuration File
		Server server = new Server(TestServer.class);
		
		// Secure Socket Server
	    //new Server(port, keystore, storepass, keypass);
		
	    // Plain Socket Server
	    //new Server(Option.getPort());
		
    	server.start();
	}
}