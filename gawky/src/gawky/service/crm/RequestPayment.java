package gawky.service.crm;


import gawky.message.Formatter;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

import java.lang.reflect.Field;
import java.util.ArrayList;



/**
 *
 * @author  Ingo Harbeck
 */
public class RequestPayment extends Part 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("PAYM"),	
			new DescC("00"),
			new DescF(Desc.FMT_9, Desc.CODE_O, 8,  "date_of_transaction"),
			new DescF(Desc.FMT_9, Desc.CODE_O, 6,  "time_of_transaction"),
			new Reserved(4),   
			new DescF(Desc.FMT_A, Desc.CODE_O, 2,  "payment_type"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3,  "country_code"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3,  "currency_code"),
		    new DescF(Desc.FMT_9, Desc.CODE_R, 14, "amount"),
		    new DescF(Desc.FMT_9, Desc.CODE_R, 2,  "type_of_handling"),
		    
			new DescF(Desc.FMT_A, Desc.CODE_R, 32, "payment_details")
		}; 
	}    

	public  String date_of_transaction = "";
    public  String time_of_transaction = "";
    public  String payment_type       = "";    
    public  String country_code        = "";
    public  String currency_code       = "";
    public  String amount              = "";
    
    private String type_of_handling    = "";
    
    //00
    private String paym_credit_card_number = "";
    private String paym_expiry_date = "";
    private String paym_issue_date = "";
    private String paym_issue_number = "";

    //01        
    private String paym_bank_id = "";
    private String paym_account_number = "";
    private String paym_rib_code = "";
    private String paym_sloppy_flag = "";
    
    //02    
    private String paym_external_invoice_number = "";
    
    //03  
    public String paym_blacklist_reason_01 = "";
    public String paym_blacklist_reason_02 = "";
    public String paym_blacklist_reason_03 = "";
    public String paym_blacklist_reason_04 = "";
    public String paym_blacklist_reason_05 = "";
    public String paym_blacklist_reason_06 = "";
    public String paym_blacklist_reason_07 = "";
    public String paym_blacklist_reason_08 = "";
    

    public void setCreditCard(String credit_card_number, String expiry_dateMMYY)
    {
        setCreditCard(credit_card_number, expiry_dateMMYY, null, null);
    }
    
    public void setCreditCard(String credit_card_number, String expiry_dateMMYY, String issue_dateMMYY, String issue_number)
    {
        type_of_handling = "00";
        paym_credit_card_number = credit_card_number;
        paym_expiry_date        = expiry_dateMMYY;
        paym_issue_date         = issue_dateMMYY == null ? "" : issue_dateMMYY;
        paym_issue_number       = issue_number   == null ? "" : issue_number;
    }
    
    
    public void setBankAccount(String bank_id, String account_number)
    {
        setBankAccount(bank_id, account_number, null, null);
    }
    public void setBankAccount(String bank_id, String account_number, String rib_code, String sloopy_flag)
    {
        type_of_handling = "01";
        paym_bank_id        = bank_id;
        paym_account_number = account_number;
        paym_rib_code       = rib_code == null ? "" : rib_code;
        paym_sloppy_flag    = sloopy_flag == null ? "" : sloopy_flag;
    }

    public void setExternalInvoiceNumber(String external_invoice_number)
    {
        type_of_handling = "02";
        paym_external_invoice_number = external_invoice_number;        
    }

    public void setBlacklistReason(ArrayList blacklist_reason)
    {
        type_of_handling = "03";
             
        for(int i=1; i <= 8; i++)
        {
          try {  
            Field f = this.getClass().getField("paym_blacklist_reason_0" + i);
            f.set(this, (String)blacklist_reason.get(i-1));
          } catch(Exception e) {
          } 
        }        
    }

    public String getPayment_details()
    {
       StringBuffer buff = new StringBuffer(500);
    
    //00 (32)
       if(type_of_handling.equals("00")) {
          buff.append(Formatter.getStringN(19, paym_credit_card_number));
          buff.append(Formatter.getSpacer (3));
          buff.append(Formatter.getStringN(4,  paym_expiry_date));
          buff.append(Formatter.getStringC(4,  paym_issue_date));
          buff.append(Formatter.getStringN(2,  paym_issue_number));
       }
    //01 (32)
       else if(type_of_handling.equals("01")) {
          buff.append(Formatter.getStringC(10, paym_bank_id));
          buff.append(Formatter.getSpacer (3));
          buff.append(Formatter.getStringC(16, paym_account_number));
          buff.append(Formatter.getStringC(2,  paym_rib_code));
          buff.append(Formatter.getStringC(1,  paym_sloppy_flag));
       }
    //02 (32)
       else if(type_of_handling.equals("02")) {
          buff.append(Formatter.getStringC(16, paym_external_invoice_number));
          buff.append(Formatter.getSpacer (16));
       }
    
    //03  (16)
       else if(type_of_handling.equals("03")) {
          buff.append(Formatter.getStringN(2, paym_blacklist_reason_01));
          buff.append(Formatter.getStringN(2, paym_blacklist_reason_02));
          buff.append(Formatter.getStringN(2, paym_blacklist_reason_03));
          buff.append(Formatter.getStringN(2, paym_blacklist_reason_04));
          buff.append(Formatter.getStringN(2, paym_blacklist_reason_05));
          buff.append(Formatter.getStringN(2, paym_blacklist_reason_06));
          buff.append(Formatter.getStringN(2, paym_blacklist_reason_07));
          buff.append(Formatter.getStringN(2, paym_blacklist_reason_08));
       }
     
       return buff.toString();
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

	public String getDate_of_transaction() {
		return date_of_transaction;
	}

	public void setDate_of_transaction(String date_of_transaction) {
		this.date_of_transaction = date_of_transaction;
	}

	public String getTime_of_transaction() {
		return time_of_transaction;
	}

	public void setTime_of_transaction(String time_of_transaction) {
		this.time_of_transaction = time_of_transaction;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getType_of_handling() {
		return type_of_handling;
	}

	public void setType_of_handling(String type_of_handling) {
		this.type_of_handling = type_of_handling;
	}
}
