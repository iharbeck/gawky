package gawky.message.part;

/**
 * XMLPATH Part
 * 
 * @author Ingo Harbeck
 *
 */
public class DescXML extends Desc
{
	public DescXML(char format, char code, int len, String name, String xmlpath)
	{
		super(format, code, len, name);
		this.xmlpath = xmlpath;
	}

	public DescXML(char format, int len, String name, String xmlpath)
	{
		super(format, Desc.CODE_R, len, name);
		this.xmlpath = xmlpath;
	}

	public DescXML(int len, String name, String xmlpath)
	{
		super(Desc.FMT_A, Desc.CODE_R, len, name);
		this.xmlpath = xmlpath;
	}
}
