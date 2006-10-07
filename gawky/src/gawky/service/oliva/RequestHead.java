package gawky.service.oliva;

/**
 * @author Ingo Harbeck
 */

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

/**
 * Implements Oliva datastore
 */
public class RequestHead extends Part
{
	// Record definition
	public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("HEAD"),	
			new DescC("00"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 5,  "message_length"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 5,  "checksum"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 2,  "transaction_type"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,  "batch_flag"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 9,  "client_ID"),
			new Reserved(4),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 8,  "client_subcode"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 2,  "language"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 1,  "blacklist_status"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 11, "transaction_ID"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 10, "customer_ID")
		}; 
	}
	

	
  // Request header
  private String record_type       = "HEAD";  // default
  private String version_number    = "00";    // default
  private String message_length;      // automatically
  private String checksum          = "99999"; // unused
  private String transaction_type;
  private String batch_flag;
  private String client_ID;
  private String client_subcode;
  private String language;
  private String blacklist_status;
  private String transaction_ID;
  private String customer_ID;

  // request type
  public final static String TYPE_ECHOREQUEST          = "00";
  public final static String TYPE_VALIDATECARDNUMBER   = "01";
  public final static String TYPE_AUTHORIZEAMOUNT      = "02";
  public final static String TYPE_CANCELLATION         = "09";
  public final static String TYPE_AUTO_CANCELLATION    = "10";

  // batch
  final static String BATCH_NO  = " ";
  final static String BATCH_YES = "1";

  // blacklist
  final static String BLACK_WHITELIST = "0";
  final static String BLACK_BLACKLIST = "1";



	public String getBatch_flag() {
		return batch_flag;
	}
	
	
	public void setBatch_flag(String batch_flag) {
		this.batch_flag = batch_flag;
	}
	
	
	public String getBlacklist_status() {
		return blacklist_status;
	}
	
	
	public void setBlacklist_status(String blacklist_status) {
		this.blacklist_status = blacklist_status;
	}
	
	
	public String getChecksum() {
		return checksum;
	}
	
	
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	
	public String getClient_ID() {
		return client_ID;
	}
	
	
	public void setClient_ID(String client_ID) {
		this.client_ID = client_ID;
	}
	
	
	public String getClient_subcode() {
		return client_subcode;
	}
	
	
	public void setClient_subcode(String client_subcode) {
		this.client_subcode = client_subcode;
	}
	
	
	public String getCustomer_ID() {
		return customer_ID;
	}
	
	
	public void setCustomer_ID(String customer_ID) {
		this.customer_ID = customer_ID;
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
	
	public void setMessage_length(int message_length) {
		this.message_length = Integer.toString(message_length);
	}
	
	
	public String getRecord_type() {
		return record_type;
	}
	
	
	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}
	
	
	public String getTransaction_ID() {
		return transaction_ID;
	}
	
	
	public void setTransaction_ID(String transaction_ID) {
		this.transaction_ID = transaction_ID;
	}
	
	
	public String getTransaction_type() {
		return transaction_type;
	}
	
	
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	
	
	public String getVersion_number() {
		return version_number;
	}
	
	
	public void setVersion_number(String version_number) {
		this.version_number = version_number;
	}
}
