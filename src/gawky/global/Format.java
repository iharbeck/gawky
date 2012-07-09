package gawky.global;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Format
{

	public static int getInt(String str)
	{
		try
		{
			return Integer.parseInt(str);
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	public static long getLong(String str)
	{
		try
		{
			return Long.parseLong(str);
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	public static double getDouble(String val)
	{
		return getDouble(Locale.ENGLISH, val);
	}

	public static double getDouble(Locale loc, String val)
	{
		try
		{
			NumberFormat nf = NumberFormat.getNumberInstance(loc);
			nf.setGroupingUsed(true);
			return nf.parse(val).doubleValue();
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	public static Date getDate(String value, String pattern)
	{
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		df.setLenient(false);

		try
		{
			return df.parse(value);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static String convertDate(String datum, String pattern, String targetPattern) throws Exception
	{

		if(datum == null)
			datum = "";
		try
		{
			return new SimpleDateFormat(targetPattern).format(new SimpleDateFormat(pattern).parse(datum));
		}
		catch(Exception ex)
		{
			return datum;
		}
	}

	public static String getDecimal(Locale loc, String value, String pattern)
	{
		return getDecimal(getDouble(loc, value), pattern);
	}

	public static String getDecimal(String value, String pattern)
	{
		return getDecimal(getDouble(Locale.GERMAN, value), pattern);
	}

	public static String getDecimal(double value, String pattern)
	{
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(value);
	}

	public static String stringformat(long cent)
	{
		return stringformat(Long.toString(cent));
	}

	public static String stringformat(String cent)
	{
		int nachkomma = 2;
		int len = cent.length();
		int pos = len + (len - nachkomma) / 3;

		if((len - nachkomma) % 3 == 0 || len == 1)
			pos--;

		char[] target = new char[pos + 1];

		int a = 0;
		for(int i = len - 1; i >= 0; i--, pos--, a++)
		{
			if(a == nachkomma)
			{
				target[pos] = ',';
				i++;
				continue;
			}
			if((a - nachkomma) % 4 == 0 && a > nachkomma)
			{
				target[pos] = '.';
				i++;
				continue;
			}

			target[pos] = cent.charAt(i);
		}

		return new String(target);
	}

	public static String decimalformat(long cent)
	{
		return decimalformat(Long.toString(cent));
	}

	public static String decimalformat(String cent)
	{
		return decimalformat(cent, 2);
	}

	public static String decimalformat(String cent, int decs)
	{
		int len = cent.length();
		
		if(len == 0)
			return "0";
			
		int pos = len;

		if(len <= decs)
			pos = decs + 2;
		else
			pos++;

		char[] target = new char[pos];

		int a = 0;
		for(int i = len - 1; i >= 0 || pos > 0; i--, pos--, a++)
		{
			if(a == decs)
			{
				target[pos - 1] = '.';
				i++;
				continue;
			}

			if(i >= 0)
				target[pos - 1] = cent.charAt(i);
			else
				target[pos - 1] = '0';
		}

		return new String(target);
	}

	public static void main(String[] args)
	{
		System.out.println(stringformat("123456789"));
		System.out.println(decimalformat("123456789"));
		System.out.println(decimalformat("1"));
		System.out.println(decimalformat("12"));
		System.out.println(decimalformat("123"));
		System.out.println(decimalformat("1234567"));
	}

	public static String substr(String val, int begin, int end)
	{
		try
		{
			return val.substring(begin, end);
		}
		catch(Exception e)
		{
			return val;
		}
	}

}
