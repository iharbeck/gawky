package gawky.service.mt940;

import gawky.message.parser.PatternParser;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescV;
import gawky.message.part.Part;

/**
 * Referenznumber
 * @author harb05
 *
 */
public class Satz21 extends Part implements MTRecord
{
	@Override
	public Desc[] getDesc()
	{
		return new Desc[] {
		        new DescC(":21:"),
		        new DescV(16, "relatedrefnr", Desc.LF)
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

	private String type = ":21:";
	private String relatedrefnr;

	public String getRelatedrefnr()
	{
		return relatedrefnr;
	}

	public void setRelatedrefnr(String transrefnr)
	{
		this.relatedrefnr = transrefnr;
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