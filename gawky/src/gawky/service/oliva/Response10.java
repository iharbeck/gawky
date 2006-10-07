package gawky.service.oliva;

/**
 * @author Ingo Harbeck
 */


import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.DescV;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Implements Oliva datastore
 */
public class Response10 extends Part
{
	// Record definition
	public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("OLIR"),	
			new DescC("10"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 5,  "record_length"),
			
			new DescF(Desc.FMT_9, Desc.CODE_R, 9,  "client_id"),
			new Reserved(7),
			
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,  "return_code"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 3,  "reason_code"),
			new DescF(Desc.FMT_9, Desc.CODE_O, 4,  "sub_code"),
			
			new DescF(Desc.FMT_A, Desc.CODE_O, 12, "details"),

			new DescF(Desc.FMT_A, Desc.CODE_R, 16, "transaction_id"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 16, "sequence_number"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 18, "return_field_1"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 12, "return_field_2"),
			new Reserved(2),
			new DescF(Desc.FMT_A, Desc.CODE_O, 16, "return_text1"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 16, "return_text2"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 64, "message")
		}; 
	}

	
	public Response10(String str) throws Exception
    {
 	   super(str);		
    }
	
	// Response
	private String record_length;
	private String return_code;
	private String reason_code;
	private String details;
	private String transaction_id;
	private String sequence_number;
	private String return_field_1;
	private String return_field_2;
	private String return_text1;
	private String return_text2;
	private String message;
	private String client_id;
	private String sub_code;
		
	public Date   date = null;

  
  
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
		
		try {
		      SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddhhmm");
		      date = formatter.parse(details);
	    }
	    catch (Exception e) {
	      //System.out.println(e);
	    }
	}
	
	
	public String getReason_code() {
		return reason_code;
	}
	
	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}
	
	public String getRecord_length() {
		return record_length;
	}
	
	public void setRecord_length(String record_length) {
		this.record_length = record_length;
	}
	
	public String getReturn_code() {
		return return_code;
	}
	
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	
	public String getReturn_field_1() {
		return return_field_1;
	}
	
	public void setReturn_field_1(String return_field_1) {
		this.return_field_1 = return_field_1;
	}
	
	public String getReturn_field_2() {
		return return_field_2;
	}
	
	public void setReturn_field_2(String return_field_2) {
		this.return_field_2 = return_field_2;
	}
	
	public String getSequence_number() {
		return sequence_number;
	}
	
	public void setSequence_number(String sequence_number) {
		this.sequence_number = sequence_number;
	}
	
	public String getTransaction_id() {
		return transaction_id;
	}
	
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}


	public String getClient_id() {
		return client_id;
	}


	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getReturn_text1() {
		return return_text1;
	}


	public void setReturn_text1(String return_text1) {
		this.return_text1 = return_text1;
	}


	public String getReturn_text2() {
		return return_text2;
	}


	public void setReturn_text2(String return_text2) {
		this.return_text2 = return_text2;
	}


	public String getSub_code() {
		return sub_code;
	}


	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
	}
}
