package gawky.service.dtaus.dtaus_band;

public class Helper
{

	public static long readNumberBinary(byte[] buffer)
	{
		int len = 4;
		int value = 0;

		for(int i = 0; i < len; i++)
		{
			int shift = (len - 1 - i) * 8;
			value += (buffer[len - 1 - i] & 0x000000FF) << shift;
		}
		return value;
	}

	public static void writeNumberBinary(byte[] buffer, final long number)
	{
		int len = 4;
		int shift = (len - 1) * 8;

		for(int i = 0; i < len; i++, shift -= 8)
		{
			buffer[len - 1 - i] = (byte)((number >> shift) & 0xFFL);
		}
	}

}
