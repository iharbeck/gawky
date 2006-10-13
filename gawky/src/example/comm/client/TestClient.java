package example.comm.client;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import example.message.parser.RequestHead;
import gawky.comm.Client;
import gawky.global.Option;

public class TestClient {

	private static Log log = LogFactory.getLog(TestClient.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception 
	{
		Option.init("properties.xml", "TestServer", args);
		
		Client client = new Client();
        
		RequestHead head = new RequestHead();

		head.setId("11");
		
		head.setSortcode("20090500");
		head.setAccount("7072007");
		
		head.setBinary("11");
		head.setVlist1("varone");
		head.setVlist2("vartwo");
		head.setDatum("20050805");
		 
        
        String request = head.toString() +
				         "POS001234" +
				         "POS002234" + 
				         "POS003234" + "\n"; 
        				 //"ART00abcdef";

        
    	String response = client.sendRequestPlain(request);
    	System.out.println(":: " + response);
	}
}
