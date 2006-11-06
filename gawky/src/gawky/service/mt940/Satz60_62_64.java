package gawky.service.mt940;

import gawky.message.parser.PatternParser;
import gawky.message.part.Desc;
import gawky.message.part.DescF;
import gawky.message.part.DescL;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class Satz60_62_64 extends Part 
{
	public Desc[] getDesc() {
		return new Desc[] {
			//new DescV(5,  "type", "%:[0-9]{0,2}.?:"),    // 60M&F 62F&M 64
			new DescL("type", new String[] {":60M:", ":60F:", ":62F:", ":62M", ":64:"}),
			new DescF(1,  "dc"),
			new DescF(6,  "bookingdate"),
			new DescF(3,  "currencycode"),
			new DescV(15, "amount", Desc.END)
		};
	}
	
	public static void main(String[] args) throws Exception 
	{
		PatternParser parser = new PatternParser();
		
		Satz60_62_64 bean = new Satz60_62_64();
		
		String str = ":28:61006/23768";
			   
		bean.parse(parser, str);
		bean.echo();
		
	}
	
	public void echo()
	{
		System.out.println("DC = " + this.dc);
		System.out.println("Bookingdate = " + this.bookingdate);
		System.out.println("Currencycode = " + this.currencycode);
		System.out.println("Amount = " + this.amount);
	}
	
	private String type;
	private String dc;
	private String bookingdate;
	private String currencycode;
	private String amount;
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBookingdate() {
		return bookingdate;
	}

	public void setBookingdate(String bookingdate) {
		this.bookingdate = bookingdate;
	}

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}