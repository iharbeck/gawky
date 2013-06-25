package gawky.comm;

import java.net.Socket;


public interface ThreadWorker 
{
	/**
	 * Process
	 * @param s Threaded Client Socket
	 * @return status
	 */
	public void execute(Server.Worker thread, Socket s) throws Exception;
 
}
