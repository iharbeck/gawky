package gawky.message.part;

/**
 * Packed Decimal Part
 * 
 * @author Ingo Harbeck
 *
 */
public class DescB extends Desc 
{
	//public PackedDecimal packeddecimal;
	
	public DescB(int len, String name)
	{	
		super(Desc.FMT_BINARY, Desc.CODE_O, len, name);
	}
}
