package gawky.host;

import gawky.global.Constant;

import java.io.UnsupportedEncodingException;

public class Ebcdic
{
	public final static byte[] toEbcdic(String unicode) throws UnsupportedEncodingException
	{
		String encoding = Constant.ENCODE_EBCDIC;
		return unicode.getBytes(encoding);
	}

	public final static String toUnicode(byte[] ebcdic) throws UnsupportedEncodingException
	{
		String encoding = Constant.ENCODE_EBCDIC;
		//Integer.toBinaryString(13);
		return new String(ebcdic, encoding);
	}

	public static void main(String[] args) throws Exception
	{

		//		String src = "12345";

		//		PackedDecimal pp = new PackedDecimal(5, 0);
		//		
		//		byte[] bytes = pp.pack(src);
		//		
		//		System.out.println("orginal " + src);
		//		System.out.println("  pack  " + new String(bytes));
		//		System.out.println("unpack  " + pp.unpack(bytes));

		System.out.println(new String(toEbcdic("te     st")));
		System.out.println(toUnicode(toEbcdic("te     st")));

		PackedDecimal.writeNumberPacked(3, 12345, true);
		//		System.out.println(PackedDecimal.readNumberPacked(bytes, 3, true));
	}

	public static void convertEbcdic() throws UnsupportedEncodingException
	{
		// System 390 EBCDIC
		//		String encoding = "Cp1047";

		// microsoft proprietary USA
		// encoding = "Cp037";

		// IBM PC OEM DOS
		// encoding = "Cp437";

		//		String unicode = "1234567890abcdefghijklmnopqrstuvwxyz";
		//		byte[] ebcdic = unicode.getBytes(encoding);
		//		String reconsituted = new String(ebcdic, encoding);
		//		System.out.println("String " + unicode);
		//		System.out.println("EBCDIC " + new String(ebcdic));
		//		System.out.println("Converted " + reconsituted);

	}
}
