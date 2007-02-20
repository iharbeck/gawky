package gawky.bcos;

import java.io.FileOutputStream;

public class XMLGenerator 
{
	public FileOutputStream out;
	public StringBuilder xml = new StringBuilder();
	
	public void init(String filename) throws Exception {
		out = new FileOutputStream(filename, false);
		
		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n".getBytes());
		out.write("<bookings>\n".getBytes());
	}
	
	public void close() throws Exception {
		
		out.write(xml.toString().getBytes());
		
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
		//xml  = new StringBuilder();
		
		xml.append("<booking ");

		//obj.getFirstname().indexOf('\'');
		addAttribut("clientid", obj.getClientid());
		addAttribut("debtor_account", obj.getDebtor_account());
		addAttribut("ref_nr", obj.getRef_nr());
		addAttribut("bookdate", obj.getBookdate());
		addAttribut("bookcode", obj.getBookcode());
		addAttribut("booksubcode", obj.getBooksubcode());
		addAttribut("booknumber", obj.getBooknumber());
		addAttribut("currency", obj.getCurrency());
		addAttribut("amount", obj.getAmount());
		addAttribut("currency_org", obj.getCurrency_org());
		addAttribut("amount_org", obj.getAmount_org());
		addAttribut("dc", obj.getDc());
		addAttribut("ccnumber", obj.getCcnumber());
		addAttribut("ccexpiry", obj.getCcexpiry());
		addAttribut("addressvalcode", obj.getAddressvalcode());
		addAttribut("authcode", obj.getAuthcode());
		addAttribut("authdate", obj.getAuthdate());
		addAttribut("ccid", obj.getCcid());
		addAttribut("banksortcode", obj.getBanksortcode());
		addAttribut("bankaccount", obj.getBankaccount());
		addAttribut("lastname", obj.getLastname());
		addAttribut("firstname", obj.getFirstname());
		addAttribut("street", obj.getStreet());
		addAttribut("zip", obj.getZip());
		addAttribut("city", obj.getCity());
		addAttribut("countrycode", obj.getCountrycode());
		addAttribut("countryname", obj.getCountryname());
		addAttribut("bookingtext", obj.getBookingtext());
		addAttribut("cctype", obj.getCctype());
		addAttribut("paymenttype", obj.getPaymenttype());
		addAttribut("banknumber", obj.getBanknumber());
		addAttribut("authcodelong", obj.getAuthcodelong());
		addAttribut("ribcode", obj.getRibcode());
		addAttribut("referencetext", obj.getReferencetext());
		addAttribut("iban", obj.getIban());
		addAttribut("bic", obj.getBic());
		addAttribut("mandate_status", obj.getMandate_status());
		addAttribut("state", obj.getState());
		addAttribut("mandate_id", obj.getMandate_id());
		addAttribut("street2", obj.getStreet2());
		addAttribut("paymenttype2", obj.getPaymenttype2());
		addAttribut("companyname", obj.getCompanyname());
		addAttribut("accountnowner", obj.getAccountnowner());
		addAttribut("authtime", obj.getAuthtime());

		xml.append("/>\n");
		
		//out.write(xml.toString().getBytes());
	}
}
