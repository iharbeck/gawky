/*
 * go.java
 *
 * Created on 6. August 2003, 10:55
 */

package example.service.crm;


import gawky.comm.Client;
import gawky.comm.IO;
import gawky.global.Constant;
import gawky.service.crm.Request;
import gawky.service.crm.RequestAddress;
import gawky.service.crm.RequestHeadPaymentech;
import gawky.service.crm.RequestPayment;
import gawky.service.crm.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;



/**
 *
 * @author  harb05
 */
public class TestCRMObject {
    
    /** Creates a new instance of go */
    
    public static void main(String[] args) throws Exception
    {
       long start = System.currentTimeMillis();
       // REQUEST Object 
       Request req = new Request();
       
       String CHARSET = "U";
       String ENC     =  Constant.ENCODE_ISO;
       String UNIFI   = "A";
       
       // HEAD Information
       RequestHeadPaymentech head = new RequestHeadPaymentech();
       
       head.setTransaction_type("71"); //20 22
       //head.head_client_id        = "700340202";
       //head.head_client_id        = "700343500";
      
       head.setClient_id("700343550"); //"700341502"; // 700341002
       head.setClient_subcode("104080"); // paymentech subcode
       
       
       head.setCustomer_id(""); //UNIFI + "445566";
       
       head.setTransaction_id("JILA123456");
       
       head.setCharacter_encoding(CHARSET);
       head.setBatch_flag("0");
       req.setHead(head);

       		
       // PAYMENT Information
       RequestPayment pay = new RequestPayment();

       pay.setAmount("1");
       
       // CreditCard
       //pay.setCreditCard("4242424242424242", "1204");
   
       // DirectDebit
       pay.setBankAccount("20090500", "7072007");
       
       // Openinvoice
       //pay.setExternalInvoiceNumber("9999999999999991");
       //pay.setExternalInvoiceNumber("1");
       
       
       
       pay.setCountry_code("DE");
       pay.setCurrency_code("EUR");
       
       req.setPaym(pay);
       
       // ADDRESS Information
       RequestAddress adr = new RequestAddress();
       adr.setFe_customer_id(UNIFI + "114455");
       adr.setScore_type("1");
           	
            
       /*adr.addr_name           = "Schmüser";   
       adr.addr_name_2         = "Detlev";
       adr.addr_date_of_birth  = "19690503";
       
       adr.addr_street         = "Herzog Albrecht Weg";
       adr.addr_street_number  = "42";
       adr.addr_city           = "Kirchheim";
       adr.addr_zip            = "85551";
       adr.addr_email_address  = "wer@wer.wer";
       adr.addr_country        = "DE";
       */
       adr.setName(UNIFI + "Gottfried");   
       adr.setName_2(UNIFI + "Müller");
       adr.setDate_of_birth("19800503");
       
       adr.setStreet(UNIFI + "Auf der Reihe");
       adr.setStreet_number("20");
       adr.setCity(UNIFI + "Gütersloh");
       adr.setZip("33311");
       adr.setEmail_address(UNIFI + "@bertelsmann.de");
       adr.setCountry("DE");
      
       adr.setCharacter_set(CHARSET);
       
       req.setAddr(adr);
       
        
       // Plainsocket
       //Comm.setServer("debmu464", 31680);
       //Comm.setServer("debmu417", 31680);
       //Comm.setServer("debmu720", 30680);
       //Comm.setServer("debmu720", 31999);
       //Comm.setServer("lnxcrm01", 31680);
       Client client = new Client("crmtest.bertelsmann.de", 31680);
       
       // SSL
       //Comm.setServer("crm.bertelsmann.de", 31682, "storepath", "passphrase");
       
       //System.out.println(req.toRequestString());
    
       //Client.setAll(false);
       
       String r = "";

       
      // adr.addr_name           = "INGO";   
       r = req.toRequestString(); // + "\n";
//       adr.addr_name           = "Doe2";   
//       r += req.toRequestString() + "\n";
//       adr.addr_name           = "Doe3";   
//       r += req.toRequestString() + "\n";
//       adr.addr_name           = "Doe4";   
//       r += req.toRequestString() + "\n";
//       
       String tmp = client.sendRequestPlain(r, req.getEncoding(), 5000); 
       
       
       System.out.println( r );

       System.out.println(System.currentTimeMillis() - start);


       
       System.out.println("response: " + tmp);
       
        Response response = new Response(tmp);
       
       System.out.println("returncode: " + response.getReturn_code());
       System.out.println("reasoncode: " + response.getReason_code());
       
       
       
       System.exit(0);
       
       Socket socket = null;
       
		try {
	    	socket = new Socket(Client.host, Client.port);

	    	IO.writeLine(socket, r, ENC);
	    	
	    	
	    	BufferedReader is = null;
	    	
	        is = new BufferedReader(new InputStreamReader(socket.getInputStream(), ENC));
	       
	        String str = null;
	        
	        int i = 4;
	        while((str = is.readLine()) != null && i > 1)
	        {
	        	System.out.println("111" + str);
	        	i--;
	        }
	        
	       // IO.writeLine(socket, r, ENC);
	    	
//	        i = 4;
//	        while((str = is.readLine()) != null && i >= 1)
//	        {
//	        	System.out.println("222" + str);
//	        	i--;
//	        }
	        
	        //System.out.println("111" + IO.readLine(socket, Constant.ENCODE_ISO) );
	    	//return sendRequest(req, socket, encode);
		}
		finally {
			if(socket != null)
			{
				socket.shutdownInput();
				socket.shutdownOutput();
			
				socket.close();
			}
		}
       
       //System.out.println("response: " + tmp);
       
       /*
        * 
        Response response = new Response(tmp);
       
       System.out.println("returncode: " + response.cmpr_return_code);
       System.out.println("reasoncode: " + response.cmpr_reason_code);
       */   
       
    }
    
}
