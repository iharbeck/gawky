package gawky.service.mt940;

import gawky.message.parser.PatternParser;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class Satz21 extends Part 
{
	public Desc[] getDesc() {
		return new Desc[] {
			new DescC(":21:"),
			new DescV(16,  "relatedrefnr", Desc.END)
		};
	}
	
	public static void main(String[] args) throws Exception 
	{
		PatternParser parser = new PatternParser();
		
		Satz21 bean = new Satz21();
		
		String str = ":21:333333";
			   
		bean.parse(parser, str);
		bean.echo();
		
	}
	
	public void echo()
	{
		System.out.println("Relatedrefnr = " + this.relatedrefnr);
	}
	
	private String relatedrefnr;

	public String getRelatedrefnr() {
		return relatedrefnr;
	}

	public void setRelatedrefnr(String transrefnr) {
		this.relatedrefnr = transrefnr;
	}
	
}