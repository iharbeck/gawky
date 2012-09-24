package gawky.message.parser;

import gawky.global.Constant;

/**
 * @author Ingo Harbeck
 *
 */
public class EBCDICParser extends ByteParser
{
	String encoding = Constant.ENCODE_ISO;
	
	public EBCDICParser()
	{
		encoding = Constant.ENCODE_EBCDIC;
	}
}
