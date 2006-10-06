/*
 * go.java
 *
 * Created on 6. August 2003, 10:55
 */

package example.service.crm;

import gawky.comm.Client;
import gawky.file.LineHandler;
import gawky.file.LineReader;
import gawky.global.Constant;
import gawky.service.crm.Response;

/**
 *
 * @author  harb05
 */
public class TestCRMFILE2S implements LineHandler {
    
    /** Creates a new instance of go */
    
	static long start;
	static Client client;
	
    public static void main(String[] args) throws Exception
    {
       start = System.currentTimeMillis();
       
       String file = "d:/ss_settlements20060825.ss1";
       
       // Plainsocket
       client = new Client("crmtest.bertelsmann.de", 31680);

       LineReader.processFile(file, new TestCRMFILE2S());
       
       System.exit(0);
    }

	public void processLine(String line) throws Exception {

		if(!line.startsWith("HEAD"))
    		return;

		System.out.println( line );
		
        String tmp = client.sendRequestPlain(line, Constant.ENCODE_ISO, 5000); 
        
        System.out.println(System.currentTimeMillis() - start);

        System.out.println("response: " + tmp);
       
        Response response = new Response(tmp);

        System.out.println("returncode: " + response.getReturn_code());
        System.out.println("reasoncode: " + response.getReason_code());
	}
    
}
