package gawky.xml.vtd;

import java.io.FileOutputStream;


public class XMLGenerator {

	public FileOutputStream out;
	public StringBuilder xml = new StringBuilder();
	
	public void init(String filename) throws Exception {
		out = new FileOutputStream(filename, false);
		
		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n".getBytes());
		out.write("<bookings>\n".getBytes());
	}
	
	public void close() throws Exception {
		out.write("</bookings>\n".getBytes());
		out.close();
	}
	
	public final void addAttribut(String name, String value)
	{
		if(value != null)
			xml.append(name).append("=\"").append(value).append("\" ");
	}
	
	public final void genxml(BaseObjectI obj) throws Exception 
	{
		xml  = new StringBuilder();
		
		xml.append("<booking ");

		//obj.getFirstname().indexOf('\'');
		addAttribut("clientid", 	  obj.getFirstname());
		addAttribut("debtor_account", obj.getLastname());
		addAttribut("ref_nr",    obj.getAmount());
		addAttribut("bookdate", obj.getFirstname());
		addAttribut("bookcode", obj.getLastname());
		addAttribut("booksubcode",    obj.getAmount());
		addAttribut("booknumber", obj.getFirstname());
		addAttribut("currency", obj.getLastname());
		addAttribut("amount",    obj.getAmount());
		addAttribut("currency_org", obj.getFirstname());
		addAttribut("amount_org", obj.getLastname());
		addAttribut("dc",    obj.getAmount());
		addAttribut("ccnumber",    obj.getAmount());
		addAttribut("ccexpiry",    obj.getAmount());
		addAttribut("addressvalcode",    obj.getAmount());
		addAttribut("authcode",    obj.getAmount());
		addAttribut("authdate",    obj.getAmount());
		addAttribut("ccid",    obj.getAmount());
		addAttribut("banksortcode",    obj.getAmount());
		addAttribut("bankaccount",    obj.getAmount());
		addAttribut("lastname",    obj.getAmount());
		addAttribut("firstname",    obj.getAmount());
		addAttribut("street",    obj.getAmount());
		addAttribut("zip",    obj.getAmount());
		addAttribut("city",    obj.getAmount());
		addAttribut("countrycode",    obj.getAmount());
		addAttribut("countryname",    obj.getAmount());
		addAttribut("bookingtext",    obj.getAmount());
		addAttribut("cctype",    obj.getAmount());
		addAttribut("paymenttype",    obj.getAmount());
		addAttribut("banknumber",    obj.getAmount());
		addAttribut("authcodelong",    obj.getAmount());
		addAttribut("ribcode",    obj.getAmount());
		addAttribut("referencetext",    obj.getAmount());
		addAttribut("iban",    obj.getAmount());
		addAttribut("bic",    obj.getAmount());
		addAttribut("mandate_status",    obj.getAmount());
		addAttribut("state",    obj.getAmount());
		addAttribut("mandate_id",    obj.getAmount());
		addAttribut("street2",    obj.getAmount());
		addAttribut("paymenttype2",    obj.getAmount());
		addAttribut("companyname",    obj.getAmount());
		addAttribut("accountnowner",    obj.getAmount());
		addAttribut("authtime",    obj.getAmount());

		xml.append("/>\n");
		
		out.write(xml.toString().getBytes());
	}
}
