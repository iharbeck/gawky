package gawky.global;

import java.text.NumberFormat;
import java.util.Locale;

public class Format {

	public static int getInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}

	public static long getLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return 0;
		}
	}

	public static double getDouble(Locale loc, String val) {
		try {
			NumberFormat nf = NumberFormat.getNumberInstance(loc);
			nf.setGroupingUsed(true);
			return nf.parse(val).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}
}
