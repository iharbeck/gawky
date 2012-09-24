package gawky.message.parser;

import gawky.global.Constant;

/**
 * @author Ingo Harbeck
 *
 */
public class EBCDICParser extends ByteParser
{
	public EBCDICParser()
	{
		super(Constant.ENCODE_EBCDIC);
	}
}
