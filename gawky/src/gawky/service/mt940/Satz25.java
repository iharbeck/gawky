package gawky.service.mt940;

import gawky.message.parser.PatternParser;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescV;
import gawky.message.part.Part;

/**
 * Accountnumber
 * @author harb05
 *
 */
public class Satz25 extends Part implements MTRecord
{
	public Desc[] getDesc() {
		return new Desc[] {
			new DescC(":25:"),
			new DescV(35,  "accountident", Desc.LF)
		};
	}
	
	public static void main(String[] args) throws Exception 
	{
		PatternParser parser = new PatternParser();
		
		Satz25 bean = new Satz25();
		
		String str = ":25:444444";
			   
		bean.parse(parser, str);
		bean.echo();
		
	}
	
	public void echo()
	{
		System.out.println("Accountident = " + this.accountident);
	}
	
	private String type=":25:";
	private String accountident;

	public String getAccountident() {
		return accountident;
	}

	public void setAccountident(String transrefnr) {
		this.accountident = transrefnr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}