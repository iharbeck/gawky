/*
 * RequestHead.java
 *
 * Created on 6. August 2003, 11:27
 */

package gawky.service.crm;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

/**
 *
 * @author  Ingo Harbeck
 */
public class RequestHead extends Part 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("HEAD"),	
			new DescC("00"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 5,  "message_length"),          
			new DescF(Desc.FMT_9, Desc.CODE_R, 5,  "checksum"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 2,  "transaction_type"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,  "batch_flag"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 9,  "client_id"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 4,  "client_subcode"),
		    new Reserved(19),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 2,  "language"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 1,  "character_encoding"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 16, "transaction_id"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 10, "customer_id")
		}; 
	}

	private String message_length     = "";
    private String checksum           = "99999";
    private String transaction_type   = "";
    private String batch_flag         = "";
    private String client_id          = "";
    private String client_subcode     = "";
    private String language           = "";
    private String character_encoding = "";
    private String transaction_id     = "";
    private String customer_id        = "";

    public String getBatch_flag() {
		return batch_flag;
	}

	public void setBatch_flag(String batch_flag) {
		this.batch_flag = batch_flag;
	}

	public String getCharacter_encoding() {
		return character_encoding;
	}

	public void setCharacter_encoding(String character_encoding) {
		this.character_encoding = character_encoding;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_subcode() {
		return client_subcode;
	}

	public void setClient_subcode(String client_subcode) {
		this.client_subcode = client_subcode;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMessage_length() {
		return message_length;
	}

	public void setMessage_length(String message_length) {
		this.message_length = message_length;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	} 
}
