package gawky.service.bcoscs;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.DescV;

/**
 *
 * @author  Ingo Harbeck
 */
public class RecordEXTI01 extends RecordEXTI00 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("EXTI"),	
			new DescC("01"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "encoding"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 9,   "clientid"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 16,  "recordid"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 1,   "accounttype"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 10,  "accountid"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 10,  "transactionid"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "transactioncode"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "transactionsubcode"),
		    new DescF(Desc.FMT_D, Desc.CODE_R, 8,   "transactiondate"),
		    new DescF(Desc.FMT_D, Desc.CODE_R, 8,   "duedate"),
		    new DescF(Desc.FMT_9, Desc.CODE_O, 14,  "amountacc"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "accountcurrency"),
		    new DescF(Desc.FMT_9, Desc.CODE_R, 14,  "amountbac"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "basecurrency"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 8,   "exchangedate"),
		    new DescF(Desc.FMT_9, Desc.CODE_O, 20,  "exchangerate"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "dc"),
		    new DescF(Desc.FMT_9, Desc.CODE_O, 10,  "referencenumber"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 40,  "referencenumberadd"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "encrypt"),
		    
		    new DescF(Desc.FMT_A, Desc.CODE_O, 48,  "creditcardnumber"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 4,   "creditcardexpiry"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 4,   "issuedate"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 2,   "cardid"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 12,  "bankid"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 80,  "bankaccount"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 80,  "iban"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 11,  "bic"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 2,   "ribcode"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "accountowner"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 6,   "authcode"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 8,   "authdate"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 6,   "authtime"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 2,   "avscode"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 2,   "fiascctype"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 2,   "paymenttype"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 3,   "banknumber"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 12,  "authorizationcode"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 2,   "mandatestatus"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 20,  "mandateid"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 10,  "orderid"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "bookingtext"),
		    new DescF(Desc.FMT_9, Desc.CODE_R, 6,   "addressnumber"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "title"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "company"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "name"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "name2"),
		    
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "street"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "street2"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 10,  "zipcode"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "city"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 16,  "pobox"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "country"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "region"),
		    
		    new DescV(Desc.FMT_9, Desc.CODE_O, 9,   "geocode"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "phonenumber"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "faxnumber"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 255, "email"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline1"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline2"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline3"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline4"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline5"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline6"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline7"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 15,  "taxid"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 1,   "bankaccounttype"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 80,  "smarchantname"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 80,  "smarchantcontact")
		}; 
	}

    private String taxid;
    private String bankaccounttype;
    private String smarchantname;
    private String smarchantcontact;
    
	public String getBankaccounttype() {
		return bankaccounttype;
	}
	public void setBankaccounttype(String bankaccounttype) {
		this.bankaccounttype = bankaccounttype;
	}
	public String getSmarchantcontact() {
		return smarchantcontact;
	}
	public void setSmarchantcontact(String smarchantcontact) {
		this.smarchantcontact = smarchantcontact;
	}
	public String getSmarchantname() {
		return smarchantname;
	}
	public void setSmarchantname(String smarchantname) {
		this.smarchantname = smarchantname;
	}
	public String getTaxid() {
		return taxid;
	}
	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}
}
