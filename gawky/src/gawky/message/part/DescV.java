package gawky.message.part;


/**
 * Variable length Part
 * 
 * @author HARB05
 *
 */
public class DescV extends Desc 
{
	public DescV(char format, char code, int len, String name, String delimiter)
	{
		super(format, code, len, name, delimiter);
	}
	
	public DescV(char format, char code, int len, String name)
	{	
		super(format, code, len, name, Desc.END);
	}
	
	public DescV(String name)
	{	
		super(Desc.FMT_A, Desc.CODE_O, 0, name, Desc.END);
	}
	public DescV(String name, String delimiter)
	{	
		super(Desc.FMT_A, Desc.CODE_O, 0, name, delimiter);
	}
	
	public DescV(char code, String name, String delimiter)
	{	
		super(code, Desc.CODE_O, 0, name, delimiter);
	}
}
