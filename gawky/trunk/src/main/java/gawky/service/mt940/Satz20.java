package gawky.service.mt940;

import gawky.message.parser.PatternParser;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescV;
import gawky.message.part.Part;

/**
 * Statement
 * @author harb05
 *
 */
public class Satz20 extends Part implements MTRecord
{
	@Override
	public Desc[] getDesc()
	{
		return new Desc[] {
		        new DescC(":20:"),
		        new DescV(16, "transrefnr", Desc.LF)
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

	private String type = ":20:";
	private String transrefnr;

	public String getTransrefnr()
	{
		return transrefnr;
	}

	public void setTransrefnr(String transrefnr)
	{
		this.transrefnr = transrefnr;
	}

	@Override
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

}