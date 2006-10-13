package example.service.oliva;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import example.global.Session;
import gawky.comm.Client;
import gawky.service.oliva.Request;
import gawky.service.oliva.RequestHead;
import gawky.service.oliva.RequestPayment;
import gawky.service.oliva.Response;

public class ActionOliva 
{
	private static final Log log = LogFactory.getLog(ActionOliva.class);

	public static void doSettlement(Session session)
	{
	  // REQUEST Object 
       Request req = new Request();
       
       // HEAD Information
       RequestHead head = new RequestHead();
       head.setTransaction_type(RequestHead.TYPE_VALIDATECARDNUMBER); //typ_AUTHORIZEAMOUNT;
       head.setClient_ID("700340013");
 
       req.setHead(head);
       		
       // PAYMENT Information
       RequestPayment pay = new RequestPayment();
       pay.setCountry_code("DE"); 
       pay.setCurrency_code("EUR"); 

       //Credit card Example 
       pay.setCreditCardInformation("4908040072016539", "1202");
       pay.setAmount("1");

       //Direct Debit Example
       //pay.setBankInformation("070116", "19351630");

       req.setPaym(pay);
       
       Client client = new Client("olivatest.bertelsmann.de", 32666);

       String request = req.toRequestString();
       log.info("Request send to Oliva: " + request);
       
       try 
       {
    	   if(false)
		   {
			   String tmp = client.sendRequestPlain(req.toRequestString()); 
			   log.info("response: " + tmp);
			   Response response = new Response(tmp);
		   }
		} catch (Exception e) {
		}
	}
}
