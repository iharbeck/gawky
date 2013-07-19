package gawky.message.part;

import gawky.message.Formatter;

import java.math.BigInteger;

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
	
	public static String binary(int len, int value)
	{
		StringBuffer buf = new StringBuffer();
		
		String tmp = Integer.toHexString(value);
		
		tmp = Formatter.getStringN(8, tmp);
		
		for(int i=0; i < len; i++)
			buf.append((char)Integer.parseInt(tmp.substring(2*i, 2*i+2), 16));
		
		return buf.toString();
	}
	
	public static long binaryconvert(byte[] bytes)
	{
		BigInteger big = new BigInteger(bytes);  
		return big.longValue();  	
	}
}
