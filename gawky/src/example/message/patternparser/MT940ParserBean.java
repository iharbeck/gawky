package example.message.patternparser;

import gawky.message.parser.PatternParser;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.DescL;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class MT940ParserBean extends Part 
{
	final String DELIMITER_LETTER    = "#[A-Z]";
	final String DELIMITER_NUMBER    = "#[0-9]";
	final String DELIMITER_NOTNUMBER = "#[^0-9]";
	final String PATTERN_CURRENCY    = "%[0-9]*,[0-9]{0,2}"; // ####,##
	
	public Desc[] getDesc() {
		return new Desc[] {
			new DescC(":61:"),
			new DescF(6,  "valuedate"),
			new DescV(4,  "entrydate", DELIMITER_LETTER),
			new DescL(    "dc", new String[] {"D", "C", "RC", "RD"}),
			new DescF(1,  "fundcode"),
			new DescV(15, "amount", PATTERN_CURRENCY),
			new DescF(1,  "entrymethod"),
			new DescF(3,  "entryreason"),
			new DescV(16, "accountownerreference", "//"),
			new DescV(16, "accountservicereference", "#[/]"),
			new DescV(34, "furtherinfo", Desc.END)
			
		};
	}
	
	public static void main(String[] args) throws Exception 
	{
		PatternParser    parser = new PatternParser();
		
		MT940ParserBean bean = new MT940ParserBean();
		
		//in gruppe0 wird alles bis zum nächsten Tag abgelegt 
		//Pattern.compile(".*:[0-9]{0,2}.?:");
		
		String str = ":61:0405230528DK418,89NCHKNOREF//10002/CTC/026/CHEQUES, PD ENCODED";
		
		bean.parse(parser, str);

	    System.out.println(bean.amount);
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