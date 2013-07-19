package gawky.service.mt940;

import gawky.message.part.Desc;
import gawky.message.part.DescF;
import gawky.message.part.DescL;
import gawky.message.part.DescV;
import gawky.message.part.Part;

/**
 * Salden
 * @author harb05
 *
 */
public class Satz6xSaldo extends Part implements MTRecord
{
	public Desc[] getDesc() {
		return new Desc[] {
			//new DescV(5,  "type", "%:[0-9]{0,2}.?:"),    // 60M&F 62F&M 64 65
			new DescL("type", new String[] {":60M:", ":60F:", ":62F:", ":62M", ":64:", ":65:"}),
			new DescF(1,  "dc"),
			new DescF(6,  "bookingdate"),
			new DescF(3,  "currencycode"),
			new DescV(15, "amount", Desc.LF)
		};
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