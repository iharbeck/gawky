package gawky.service.mt940;

import gawky.message.parser.PatternParser;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class Satz28 extends Part 
{
	public Desc[] getDesc() {
		return new Desc[] {
			new DescC(":28:"),
			new DescV(5,  "statementnr", "/"),
			new DescV(3,  "pagenr", Desc.LF)
		};
	}
	
	public static void main(String[] args) throws Exception 
	{
		PatternParser parser = new PatternParser();
		
		Satz28 bean = new Satz28();
		
		String str = ":28:61006/23768";
			   
		bean.parse(parser, str);
		bean.echo();
		
	}
	
	public void echo()
	{
		System.out.println("Statementnr = " + this.statementnr);
		System.out.println("Pagenr = " + this.pagenr);
	}
	
	private String statementnr;
	private String pagenr;
	
	
	public String getPagenr() {
		return pagenr;
	}

	public void setPagenr(String pagenr) {
		this.pagenr = pagenr;
	}

	public String getStatementnr() {
		return statementnr;
	}

	public void setStatementnr(String statementnr) {
		this.statementnr = statementnr;
	}

	
}