package gawky.message.helper;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Format 
{
	static final int DECIMALS = 2;

	static BigDecimal[] decimals = {
		new BigDecimal("1"),
		new BigDecimal("10"),
		new BigDecimal("100"),
		new BigDecimal("1000"),
		new BigDecimal("10000"),
		new BigDecimal("100000"),
		new BigDecimal("1000000"),
		new BigDecimal("10000000")
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
		if(val.length() == 0)
			return "0";
		
		BigDecimal dc = new BigDecimal(val);
		dc = dc.divide(decimals[dec]);
		return dc.setScale(dec).toString();
		
		//return Double.toString((Double.parseDouble(val)/decimals[dec]));
	}
	
	public static final String lowerNumber(double val, int dec) 
	{
		BigDecimal dc = new BigDecimal(val);
		dc = dc.divide(decimals[dec]);
		return dc.setScale(3).toString();
		
		//return Double.toString(val/decimals[dec]);
	}
	
	public static final String upperNumber(String val, int dec) 
	{
		if(val.length() == 0)
			return "0";

		BigDecimal dc = new BigDecimal(val);
		//dc.setScale(dec);
		dc = dc.multiply(decimals[dec]);
		return dc.setScale(0).toString();
		
		//return Long.toString((long)(Double.parseDouble(val)*decimals[dec] + 0.0001));
	}
	
	public static final String upperNumber(double val, int dec) 
	{
		BigDecimal dc = new BigDecimal(val);
		//dc.setScale(dec);
		dc = dc.multiply(decimals[dec]);
		return dc.setScale(0).toString();
		
		//return Long.toString((long)(val*decimals[dec]));
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
