package gawky.bcos;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Processor 
{
	int pos = 0; //tag position
	int epos = 0; //tag ende

	int cepos = 0; //child ende
	int depth = 0; // nav depth
	
	String xml = null;

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
		xml = new String(readFile(filename));
		toStart();
	}
	
	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();

		Processor sp = new Processor("c:/test.xml");
	
		BaseObjectI obj = new BaseObject();

		/** produce by Factory !!! **/
		ConsumerI consumer = new Report1060();
		
		consumer.open();
		
		while(sp.toTagEmpty("booking") != -1 )
		{
			obj.setClientid(sp.getAttribut("clientid"));
			obj.setDebtor_account(sp.getAttribut("debtor_account"));
			obj.setRef_nr(sp.getAttribut("ref_nr"));
			obj.setBookdate(sp.getAttribut("bookdate"));
			obj.setBookcode(sp.getAttribut("bookcode"));
			obj.setBooksubcode(sp.getAttribut("booksubcode"));
			obj.setBooknumber(sp.getAttribut("booknumber"));
			obj.setCurrency(sp.getAttribut("currency"));
			obj.setAmount(sp.getAttribut("amount"));
			obj.setCurrency_org(sp.getAttribut("currency_org"));
			obj.setAmount_org(sp.getAttribut("amount_org"));
			obj.setDc(sp.getAttribut("dc"));
			obj.setCcnumber(sp.getAttribut("ccnumber"));
			obj.setCcexpiry(sp.getAttribut("ccexpiry"));
			obj.setAddressvalcode(sp.getAttribut("addressvalcode"));
			obj.setAuthcode(sp.getAttribut("authcode"));
			obj.setAuthdate(sp.getAttribut("authdate"));
			obj.setCcid(sp.getAttribut("ccid"));
			obj.setBanksortcode(sp.getAttribut("banksortcode"));
			obj.setBankaccount(sp.getAttribut("bankaccount"));
			obj.setLastname(sp.getAttribut("lastname"));
			obj.setFirstname(sp.getAttribut("firstname"));
			obj.setStreet(sp.getAttribut("street"));
			obj.setZip(sp.getAttribut("zip"));
			obj.setCity(sp.getAttribut("city"));
			obj.setCountrycode(sp.getAttribut("countrycode"));
			obj.setCountryname(sp.getAttribut("countryname"));
			obj.setBookingtext(sp.getAttribut("bookingtext"));
			obj.setCctype(sp.getAttribut("cctype"));
			obj.setPaymenttype(sp.getAttribut("paymenttype"));
			obj.setBanknumber(sp.getAttribut("banknumber"));
			obj.setAuthcodelong(sp.getAttribut("authcodelong"));
			obj.setRibcode(sp.getAttribut("ribcode"));
			obj.setReferencetext(sp.getAttribut("referencetext"));
			obj.setIban(sp.getAttribut("iban"));
			obj.setBic(sp.getAttribut("bic"));
			obj.setMandate_status(sp.getAttribut("mandate_status"));
			obj.setState(sp.getAttribut("state"));
			obj.setMandate_id(sp.getAttribut("mandate_id"));
			obj.setStreet2(sp.getAttribut("street2"));
			obj.setPaymenttype2(sp.getAttribut("paymenttype2"));
			obj.setCompanyname(sp.getAttribut("companyname"));
			obj.setAccountnowner(sp.getAttribut("accountnowner"));
			obj.setAuthtime(sp.getAttribut("authtime"));
			
			consumer.processline(obj);
		}
		
		consumer.close();

		System.out.println(" ms: " + (System.currentTimeMillis() -start));
		
	}
	
	public final void toStart() {
		pos = 0;
		depth = 0;
	}
	/**
	 * detect EMPTY Tag
	 * @param name
	 * @return
	 */
	public final int toTagEmpty(String name)
	{
		pos  = xml.indexOf("<" + name + " ", pos);
		
		if(pos != -1) 
			pos = pos + name.length() + 1;
		else 
			return -1;
		
		epos = xml.indexOf("/>", pos); 
		// no children
		cepos = -1;
		
		return pos;
	}
	
	public final int toTagFull(String name)
	{
		pos  = xml.indexOf("<" + name + " ", pos);
		
		if(pos != -1) 
			pos = pos + name.length() + 1;
		else 
			return -1;
		
		epos = xml.indexOf(">", pos);
		// children to this pos
		cepos = xml.indexOf("</" + name + ">", pos); 
		
		return pos;
	}
	
	public final int toChildTag(String name) {
		return -1;
	}
	
	public final String getAttribut(String name)
	{
		// Tag String ermitteln
		String tag = xml.substring(pos, epos);
		
		int apos = tag.indexOf(name + "=\"");
		
		if(apos == -1)
			return null;
		
		apos = apos + name.length() + 2;
		
		return tag.substring(apos, tag.indexOf("\"", apos));
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
