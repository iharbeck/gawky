package gawky.global;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Validation 
{
	public static boolean isEmtpy(String value) {
		return value == null || value.trim().equals("");
	}
	
	public static boolean isLength(String value, int min, int max) {
		int len = value.length();
		return len > min && len < max;
	}
	
	public static boolean isDate(String value, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		df.setLenient(false);
		
		try {
			df.parse(value);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isListvalue(String value, String list) {
		String [] values = list.split(";");
		
		for(int i=0; i < values.length; i++) {
			if(values[i].equals(value))
				return true;
		}
		return false;
	}
	
	public static boolean isBetween(String value, double min, double max) 
	{
		double val = Format.getDouble(Locale.GERMAN, value);
		
		if(min <= val && val <= max)
			return true;
	
		return false;
	}
}
