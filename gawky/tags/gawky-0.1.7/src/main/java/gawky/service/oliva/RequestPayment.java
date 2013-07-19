package gawky.service.oliva;

/**
 * @author Ingo Harbeck
 */

import gawky.message.Formatter;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

/**
 * Implements Oliva datastore
 */
public class RequestPayment extends Part
{
	// Record definition
	public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("PAYM"),	
			new DescC("00"),
		    new DescF(Desc.FMT_9, Desc.CODE_R, 8, "date"),
		    new DescF(Desc.FMT_9, Desc.CODE_R, 6, "time"),
		    new Reserved(6),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3, "country_code"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3, "currency_code"),
		    new DescF(Desc.FMT_9, Desc.CODE_R, 14, "amount"),
		    new DescF(Desc.FMT_9, Desc.CODE_R, 2,  "type_of_handling"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 32, "paymentdetails")
		}; 
	}

	
	
    // Payment part_head
	private String record_type       = "PAYM";  // default
	private String version           = "00";    // default
	private String date;                        // default is system date
	private String time;                        // default is system time
	private String country_code		 = "DE";
	private String currency_code     = "EUR";
	private String amount            = "1";
  
  //Payment type/details is wrapped by setFunction
   String type_of_handling;
    //Payment part_00 (type_of_handling)
    String TH00_credit_card_number;
    String TH00_expiry_date;
    String TH00_issue_date;
    String TH00_issue_number;
    //Payment part_01 (type_of_handling)
    String TH01_bank_ID;
    String TH01_account_number;
    String TH01_RIB_code;
    String TH01_sloppy_flag;

  // type of handling (PAYM)
    final static String TOH_CREDITCARD   = "00";
    final static String TOH_DIRECTDEBIT  = "01";

  /**
   * generates payment part of Oliva string
   * @return Oliva string
   */

  public String getPaymentdetails()
  {
	  StringBuilder buff = new StringBuilder();
	  

      if(TOH_CREDITCARD.equals(type_of_handling)) // 00 credit card
      {
        buff.append(Formatter.getStringC(19, TH00_credit_card_number));
        buff.append(Formatter.getSpacer (3));
        buff.append(Formatter.getStringN(4,  TH00_expiry_date));
        buff.append(Formatter.getStringC(4,  TH00_issue_date));
        buff.append(Formatter.getStringC(2,  TH00_issue_number));
      }
      else if(TOH_DIRECTDEBIT.equals(type_of_handling)) // 01 direct debit
      {
        buff.append(Formatter.getStringC(10, TH01_bank_ID));
        buff.append(Formatter.getSpacer (3));
        buff.append(Formatter.getStringC(16, TH01_account_number));
        buff.append(Formatter.getStringC(2,  TH01_RIB_code));
        buff.append(Formatter.getStringC(1,  TH01_sloppy_flag));
      }
           
      return buff.toString();
  }
  
  public final void setBankInformation(String bank_sortcode, String bank_accountnumber)
  {
    setBankInformation(bank_sortcode, bank_accountnumber, null, null);
  }

  public final void setBankInformation(String bank_sortcode, String bank_accountnumber, String rib_code, String sloppy_flag)
  {
    this.type_of_handling  = RequestPayment.TOH_DIRECTDEBIT;
    this.TH01_bank_ID        = bank_sortcode;
    this.TH01_account_number = bank_accountnumber;
    this.TH01_RIB_code       = rib_code;
    this.TH01_sloppy_flag    = sloppy_flag;
  }

  public final void setCreditCardInformation(String credit_card_number, String expiry_date, String issue_date, String issue_number)
  {
    this.type_of_handling      = RequestPayment.TOH_CREDITCARD;
    this.TH00_credit_card_number = credit_card_number;
    this.TH00_expiry_date        = expiry_date;
    this.TH00_issue_date         = issue_date;
    this.TH00_issue_number       = issue_number;
  }

  public final void setCreditCardInformation(String credit_card_number, String expiry_date)
  {
    setCreditCardInformation(credit_card_number, expiry_date, null, null);
  }



  
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getCountry_code() {
		return country_code;
	}
	
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	
	public String getCurrency_code() {
		return currency_code;
	}
	
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getRecord_type() {
		return record_type;
	}
	
	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getType_of_handling() {
		return type_of_handling;
	}
	
	public void setType_of_handling(String type_of_handling) {
		this.type_of_handling = type_of_handling;
	}
    
	public void setPaymentdetails(String paymentdetails) {
	}
  
}
