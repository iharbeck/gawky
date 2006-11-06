package gawky.service.mt940;

import gawky.message.parser.PatternParser;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class Satz20 extends Part 
{
	public Desc[] getDesc() {
		return new Desc[] {
			new DescC(":20:"),
			new DescV(16,  "transrefnr", Desc.END)
		};
	}
	
	public static void main(String[] args) throws Exception 
	{
		PatternParser parser = new PatternParser();
		
		Satz20 bean = new Satz20();
		
		String str = ":20:121233123";
			   
		bean.parse(parser, str);
		bean.echo();
		
	}
	
	public void echo()
	{
		System.out.println("Transrefnr = " + this.transrefnr);
	}
	
	private String transrefnr;

	public String getTransrefnr() {
		return transrefnr;
	}

	public void setTransrefnr(String transrefnr) {
		this.transrefnr = transrefnr;
	}

	
}