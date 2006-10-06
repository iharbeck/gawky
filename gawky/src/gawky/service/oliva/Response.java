package gawky.service.oliva;

/**
 * Überschrift: OLIVAData<p>
 * Beschreibung: String generator and Parser for Oliva requests<p>
 * Copyright: Copyright (c) 2001<p>
 * Organisation: Bertelsmann Mediasystems<p>
 * @author Ingo Harbeck
 * @version 1.0
 */


import gawky.message.parser.Parser;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Implements Oliva datastore
 */
public class Response extends Part
{
	// Record definition
	public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("OLIR"),	
			new DescC("00"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 5,  "record_length"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 5,  "checksum"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,  "return_code"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 3,  "reason_code"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 12, "details"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 3,  "build_number"),
			new Reserved(3),
			new DescF(Desc.FMT_A, Desc.CODE_R, 11, "transaction_id"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 16, "sequence_number"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 16, "return_field_1"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 16, "return_field_2"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 64, "optional_text")
		}; 
	}

	
	public Response(String str) throws Exception
    {
 	   Parser parser = new Parser();
	   parser.parse(str, this);		
    }
	
  // Response
  private String record_length;
  private String checksum;
  private String return_code;
  private String reason_code;
  private String details;
  private String build_number;
  private String transaction_id;
  private String sequence_number;
  private String return_field_1;
  private String return_field_2;
  private String optional_text;
  
  public Date   date = null;

  
  
	public String getBuild_number() {
		return build_number;
	}
	
	public void setBuild_number(String build_number) {
		this.build_number = build_number;
	}
	
	public String getChecksum() {
		return checksum;
	}
	
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
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
	
	public String getOptional_text() {
		return optional_text;
	}
	
	public void setOptional_text(String optional_text) {
		this.optional_text = optional_text;
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
}
