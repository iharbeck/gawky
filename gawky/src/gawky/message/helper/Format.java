package gawky.message.helper;

import java.text.NumberFormat;

public class Format {

	static NumberFormat fmt = NumberFormat.getInstance();
	
	public static final String formatNumber(String val) 
	{
		return formatNumber(val, 2);
	}
	
	static int[] stellen = {
		1,
		10,
		100,
		1000,
		10000,
		100000,
		1000000,
		10000000
	};
	
	public static final String formatNumber(String val, int dec) 
	{
		try {
			return fmt.format(fmt.parse(val).doubleValue()/stellen[dec]);
		} catch(Exception e ) {
		}
		return "0";
	}
}
