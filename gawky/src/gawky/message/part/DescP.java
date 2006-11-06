package gawky.message.part;

import gawky.host.PackedDecimal;


/**
 * Packed Decimal Part
 * 
 * @author Ingo Harbeck
 *
 */
public class DescP extends Desc 
{
	PackedDecimal packeddecimal;

	public DescP(int integer, int decimal, String name)
	{	
		super(Desc.FMT_9, Desc.CODE_R, packedsize(integer, decimal), name);
		setPacked(true);
		
		packeddecimal = new PackedDecimal(integer, decimal);
	}
	
	private static int packedsize(int integer, int decimal) 
	{
		int size = integer + decimal;
		
		if (size % 2 == 0)
			size = (size / 2) + 1;
		else
			size = (size + 1) / 2;
		
		return size;
	}

	public PackedDecimal getPackeddecimal() {
		return packeddecimal;
	}
}
