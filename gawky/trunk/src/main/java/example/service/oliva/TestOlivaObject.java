/*
 * go.java
 *
 * Created on 6. August 2003, 10:55
 */

package example.service.oliva;

import gawky.comm.Client;
import gawky.service.oliva.Request;
import gawky.service.oliva.RequestAddress;
import gawky.service.oliva.RequestHead;
import gawky.service.oliva.RequestPayment10;
import gawky.service.oliva.Response10;

/**
 *
 * @author  Ingo Harbeck
 */
public class TestOlivaObject
{

	/** Creates a new instance of go */

	public static void main(String[] args) throws Exception
	{
		// REQUEST Object 
		Request req = new Request();

		// HEAD Information
		RequestHead head = new RequestHead();

		head.setTransaction_type(RequestHead.TYPE_AUTHORIZEAMOUNT);
		head.setClient_ID("700347001");

		req.setHead(head);

		// PAYMENT Information
		RequestPayment10 pay = new RequestPayment10();

		pay.setCountry_code("DE");
		pay.setCurrency_code("EUR");
		pay.setAmount("1");
		//Direct Debit Example
		pay.setBankInformation("20090500", "7072007");

		req.setPaym(pay);

		RequestAddress addr = new RequestAddress();
		req.setAddr(addr);

		//Init Comm
		//Client client = new Client("olivatest.bertelsmann.de", 32555, "D:/work/olivalib/gawky/etc/java.test.keystore", "hfg683jdf", "thgrz67d");
		Client client = new Client("olivatest.bertelsmann.de", 32666);

		System.out.println(req.toRequestString());

		for(int i = 0; i < 1; i++)
		{
			String response_str = client.sendRequestPlain(req.toRequestString());
			//String response_str = client.sendRequestSSL(req.toRequestString()); 

			System.out.println("response: " + response_str);

			//Response response = new Response();
			Response10 response = new Response10(response_str);

			System.out.println("transaction: " + response.getTransaction_id());
			System.out.println("returncode:  " + response.getReturn_code());
			System.out.println("reasoncode:  " + response.getReason_code());
		}
	}

}
