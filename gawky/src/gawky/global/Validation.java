package gawky.global;

import java.text.SimpleDateFormat;

public class Validation {
	public static boolean isEmtpy(String value) {
		return value == null || value.trim().equals("");
	}
	
	public static boolean islength(String value, int min, int max) {
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
}
