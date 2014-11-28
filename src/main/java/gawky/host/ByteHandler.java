package gawky.host;

public class ByteHandler
{
	// bytes
	public final static byte getLowByte(short ii)
	{
		return((byte)((short)(ii & 0x00ff)));
	}

	public final static byte getHighByte(short ii)
	{
		return(ByteHandler.getLowByte((short)(ii >> 8)));
	}

	// words
	public final static short getLowWord(int ii)
	{
		return((short)(ii & 0x0000ffff));
	}

	public final static short getHighWord(int ii)
	{
		return(ByteHandler.getLowWord(ii >> 16));
	}

	// builder
	public final static byte buildByte(byte l, byte h)
	{
		return (byte)(l | (byte)(h << 4));
	}

	public final static short buildShort(byte l, byte h)
	{
		return (short)(l | (short)(h << 8));
	}

	public final static int buildInt(short l, short h)
	{
		return l | h << 16;
	}

	// nibble
	public final static byte getLowNibble(byte b)
	{
		return (byte)(b & 0xF);
	}

	public final static byte getHighNibble(byte b)
	{
		return getLowNibble((byte)(b >> 4));
	}
};
