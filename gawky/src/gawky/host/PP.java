package gawky.host;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class PP {

	public static void main(String[] args) throws Exception {
		
		String src = "12345";
		
		PackedDecimal pp = new PackedDecimal(5, 0);
		
		byte[] bytes = pp.pack(src);
		
		System.out.println("orginal " + src);
		System.out.println("  pack  " + new String(bytes));
		System.out.println("unpack  " + pp.unpack(bytes));

		System.out.println(new String(toEbcdic("te     st")));
		System.out.println(toUnicode(toEbcdic("te     st")));
			
		PackedDecimal.writeNumberPackedPositive(3, 12345, true);
		System.out.println(PackedDecimal.readNumberPackedPositive(bytes, 3, true, Locale.GERMAN));
	}

	public static byte[] toEbcdic(String unicode) throws UnsupportedEncodingException
	{
		String encoding = "Cp1047";
		return unicode.getBytes(encoding);
	}
	
	public static String toUnicode(byte[] ebcdic) throws UnsupportedEncodingException
	{
		String encoding = "Cp1047";
		return new String(ebcdic, encoding);
	}
	
	
	public static void convertEbcdic() throws UnsupportedEncodingException {
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
