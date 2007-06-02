package gawky.bcos;

import gawky.global.Constant;
import gawky.xml.strict.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Processor 
{
	static Parser ps;
	
	public Processor() {
	}
	
	public Processor(String filename) throws Exception {
		initFile(filename);
	}
	
	public byte[] readFile(String filename) throws Exception
	{
		File f = new File( filename ); 
    	byte[] buffer = new byte[ (int) f.length() ]; 
    	InputStream in = new FileInputStream( f ); 
    	in.read( buffer ); 
    	in.close();
    	
    	return buffer;
	}

	public void initFile(String filename) throws Exception
	{
		ps = new Parser(readFile(filename), Constant.ENCODE_UTF8);
	}
	
	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();

		Processor sp = new Processor("c:/test.xml");
	
		BaseObjectI obj = new BaseObject();

		/** produce by Factory !!! **/
		ConsumerI consumer = new Report1060();
		
		consumer.open();
		
		while(ps.toTagEmpty("booking") != -1 )
		{
			obj.setClientid(ps.getAttribut("clientid"));
			obj.setDebtor_account(ps.getAttribut("debtor_account"));
			obj.setRef_nr(ps.getAttribut("ref_nr"));
			obj.setBookdate(ps.getAttribut("bookdate"));
			obj.setBookcode(ps.getAttribut("bookcode"));
			obj.setBooksubcode(ps.getAttribut("booksubcode"));
			obj.setBooknumber(ps.getAttribut("booknumber"));
			obj.setCurrency(ps.getAttribut("currency"));
			obj.setAmount(ps.getAttribut("amount"));
			obj.setCurrency_org(ps.getAttribut("currency_org"));
			obj.setAmount_org(ps.getAttribut("amount_org"));
			obj.setDc(ps.getAttribut("dc"));
			obj.setCcnumber(ps.getAttribut("ccnumber"));
			obj.setCcexpiry(ps.getAttribut("ccexpiry"));
			obj.setAddressvalcode(ps.getAttribut("addressvalcode"));
			obj.setAuthcode(ps.getAttribut("authcode"));
			obj.setAuthdate(ps.getAttribut("authdate"));
			obj.setCcid(ps.getAttribut("ccid"));
			obj.setBanksortcode(ps.getAttribut("banksortcode"));
			obj.setBankaccount(ps.getAttribut("bankaccount"));
			obj.setLastname(ps.getAttribut("lastname"));
			obj.setFirstname(ps.getAttribut("firstname"));
			obj.setStreet(ps.getAttribut("street"));
			obj.setZip(ps.getAttribut("zip"));
			obj.setCity(ps.getAttribut("city"));
			obj.setCountrycode(ps.getAttribut("countrycode"));
			obj.setCountryname(ps.getAttribut("countryname"));
			obj.setBookingtext(ps.getAttribut("bookingtext"));
			obj.setCctype(ps.getAttribut("cctype"));
			obj.setPaymenttype(ps.getAttribut("paymenttype"));
			obj.setBanknumber(ps.getAttribut("banknumber"));
			obj.setAuthcodelong(ps.getAttribut("authcodelong"));
			obj.setRibcode(ps.getAttribut("ribcode"));
			obj.setReferencetext(ps.getAttribut("referencetext"));
			obj.setIban(ps.getAttribut("iban"));
			obj.setBic(ps.getAttribut("bic"));
			obj.setMandate_status(ps.getAttribut("mandate_status"));
			obj.setState(ps.getAttribut("state"));
			obj.setMandate_id(ps.getAttribut("mandate_id"));
			obj.setStreet2(ps.getAttribut("street2"));
			obj.setPaymenttype2(ps.getAttribut("paymenttype2"));
			obj.setCompanyname(ps.getAttribut("companyname"));
			obj.setAccountnowner(ps.getAttribut("accountnowner"));
			obj.setAuthtime(ps.getAttribut("authtime"));
			
			consumer.processline(obj);
		}
		
		consumer.close();

		System.out.println(" ms: " + (System.currentTimeMillis() -start));
		
	}
	
	public void cleanup () 
	{
//		char[] val = xml.toCharArray();
		
//		int len = val.length;
//	
//		char[] target = new char[len];
//		int l = 0;
//		
//		boolean prevblank = false;
//		
//		for(int i=0; i < len; i++)
//		{
//			if(val[i] == ' ' && prevblank) 
//				continue;
//			else if(val[i] == ' ') 
//				prevblank = true;
//			else 
//				prevblank = false;
//			
//			target[l++] = val[i]; 
//		}
		
//		System.out.println(target);
	}
}
