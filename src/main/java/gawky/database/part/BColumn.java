package gawky.database.part;

import gawky.message.Formatter;
import gawky.message.part.Desc;

public class BColumn extends Desc
{

	public static byte[] fill(int size, long val)
	{
		return Formatter.bpad(size, Formatter.convertNum2Bytes(val));
	}

	public static long read(byte[] val)
	{
		return Formatter.convertBytes2Num(val);
	}

	public BColumn(String name, int size)
	{
		super(Desc.FMT_1, Desc.CODE_O, size, name, Desc.ENDCOLON);
	}
}
