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
	//public PackedDecimal packeddecimal;
	
	public DescP(int len, String name, boolean signed)
	{	
		//super(Desc.FMT_9, Desc.CODE_R, packedsize(integer, decimal), name);
		super(Desc.FMT_9, Desc.CODE_R, len, name);
		setPacked(true);
		setUnsigned(!signed);
		//packeddecimal = new PackedDecimal(integer, decimal);
		//packeddecimal = new PackedDecimal(len*2, 0);
	}
	
	public DescP(int len, String name)
	{
		this(len, name, true);
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
}
