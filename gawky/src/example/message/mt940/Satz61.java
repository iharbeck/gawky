package example.message.mt940;

import gawky.message.parser.PatternParser;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.DescL;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class Satz61 extends Part 
{
	public Desc[] getDesc() {
		return new Desc[] {
			new DescC(":61:"),
			new DescF(6,  "valuedate"),
			new DescV(4,  "entrydate", PatternParser.DELIMITER_LETTER),
			new DescL(    "dc",        new String[] {"D", "C", "RC", "RD"}),
			new DescV(1,  "fundcode",  PatternParser.DELIMITER_NUMBER),
			new DescV(15, "amount",    PatternParser.PATTERN_CURRENCY),
			new DescF(1,  "entrymethod"),
			new DescF(3,  "entryreason"),
			new DescV(16, "accountownerreference", "//"),
			new DescV(16, "accountservicereference", "\r\n"),
			new DescV(34, "furtherinfo", Desc.END)
			
		};
	}
	
	public static void main(String[] args) throws Exception 
	{
		PatternParser parser = new PatternParser();
		
		Satz61 bean = new Satz61();
		
		String str = ":61:061006C10104929,71NMSCIA100265687460//00000000000";
			   str = ":61:0405230528DK418,86NCHKNOREF//10002\r\n" +
				     "/CTC/026/CHEQUES, PD ENCODED";
			   
		bean.parse(parser, str);
		bean.echo();
		
	}
	
	public void echo()
	{
		System.out.println("Value Date = " + valuedate);
		System.out.println("Entry Date = " + entrydate);
		System.out.println("Credit / Debit Indicator = " + dc);
		System.out.println("Fund Code = " + fundcode);
		System.out.println("Amount = " + amount);
	    System.out.println("Entry Method = " + entrymethod);
	    System.out.println("Account Owner’s Reference = " + accountownerreference);
	    System.out.println("Account Servicer’s Reference = " + accountservicereference);
	    System.out.println("Further Information = " + furtherinfo);
	}
	
	private String valuedate;
	private String entrydate;
	private String dc;
	private String fundcode;
	private String amount;

	
	private String entrymethod;
	private String entryreason;
	private String accountownerreference;
	private String accountservicereference;
	private String furtherinfo;
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}

	public String getFundcode() {
		return fundcode;
	}

	public void setFundcode(String fundcode) {
		this.fundcode = fundcode;
	}


	public String getValuedate() {
		return valuedate;
	}

	public void setValuedate(String valuedate) {
		this.valuedate = valuedate;
	}

	public String getAccountownerreference() {
		return accountownerreference;
	}

	public void setAccountownerreference(String accountownerreference) {
		this.accountownerreference = accountownerreference;
	}

	public String getAccountservicereference() {
		return accountservicereference;
	}

	public void setAccountservicereference(String accountservicereference) {
		this.accountservicereference = accountservicereference;
	}

	public String getEntrymethod() {
		return entrymethod;
	}

	public void setEntrymethod(String entrymethod) {
		this.entrymethod = entrymethod;
	}

	public String getEntryreason() {
		return entryreason;
	}

	public void setEntryreason(String entryreason) {
		this.entryreason = entryreason;
	}

	public String getFurtherinfo() {
		return furtherinfo;
	}

	public void setFurtherinfo(String furtherinfo) {
		this.furtherinfo = furtherinfo;
	}
	
	
	
}