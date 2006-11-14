package gawky.message.helper;

import java.text.NumberFormat;
import java.util.Locale;

public class Format 
{
	static final int DECIMALS = 2;

	static double[] decimals = {
		1,
		10,
		100,
		1000,
		10000,
		100000,
		1000000,
		10000000
	};

	public static final String lowerNumber(String val) 
	{
		return lowerNumber(val, DECIMALS);
	}
	
	public static final String lowerNumber(double val) 
	{
		return lowerNumber(val, DECIMALS);
	}
	
	public static final String upperNumber(String val) 
	{
		return upperNumber(val, DECIMALS);
	}
	
	public static final String upperNumber(double val) 
	{
		return upperNumber(val, DECIMALS);
	}
	
	public static final String lowerNumber(String val, int dec) 
	{
		return Double.toString((Double.parseDouble(val)/decimals[dec]));
	}
	
	public static final String lowerNumber(double val, int dec) 
	{
		return Double.toString(val/decimals[dec]);
	}
	
	public static final String upperNumber(String val, int dec) 
	{
		return Long.toString((long)(Double.parseDouble(val)*decimals[dec]));
	}
	
	public static final String upperNumber(double val, int dec) 
	{
		return Long.toString((long)(val*decimals[dec]));
	}
	
	public static final String toEuro(double val) 
	{
		return lowerNumber(val); 
	}
	
	public static final String toEuro(String val) 
	{
		return lowerNumber(val); 
	}
	
	public static final String toCent(double val) 
	{
		return upperNumber(val); 
	}
	
	public static final String toCent(String val) 
	{
		return upperNumber(val); 
	}
	
	public static final String toJavaDecimal(String val) 
	{
		return val.replace(',', '.');
	}
	
	public static void main(String[] args) 
	{
		String val = "123456";
		
		System.out.println(toEuro(val));

		System.out.println(toCent(1234.78));

		val = "333,55";
		System.out.println(toJavaDecimal(val));
		
		System.out.println(Double.parseDouble( toJavaDecimal(val) ));
		
		NumberFormat fmt = NumberFormat.getInstance(Locale.ENGLISH);
		fmt.setGroupingUsed(false);
		
		System.out.println(fmt.format(1234.44));
		System.out.println(fmt.format(1234.0));
		System.out.println(fmt.format(1256));
		
		System.out.println(1234.0);
		System.out.println(1256);
	}
}
