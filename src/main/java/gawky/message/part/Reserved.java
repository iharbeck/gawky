package gawky.message.part;

/**
 * Reserved Part
 *
 * @author Ingo Harbeck
 *
 */
public class Reserved extends Desc
{
	public Reserved(int len)
	{
		super(len);
	}

	public Reserved(String delimiter)
	{
		super(FMT_CONSTANT, CODE_O, 0, "", delimiter);
	}
}
