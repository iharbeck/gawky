package gawky.incubator.nio;

public class Convert
{

	public static final char[] NEGATIVE_INFINITY = { '-', 'I', 'n', 'f', 'i', 'n', 'i', 't', 'y' };
	public static final char[] POSITIVE_INFINITY = { 'I', 'n', 'f', 'i', 'n', 'i', 't', 'y' };
	public static final char[] NaN = { 'N', 'a', 'N' };
	private static final int floatSignMask = 0x80000000;
	private static final int floatExpMask = 0x7f800000;
	private static final int floatFractMask = ~(floatSignMask | floatExpMask);
	private static final int floatExpShift = 23;
	private static final int floatExpBias = 127;
	//change dot to international character where this is used below
	public static final char[] DOUBLE_ZERO = { '0', '.', '0' };
	public static final char[] DOUBLE_ZERO2 = { '0', '.', '0', '0' };
	public static final char[] DOUBLE_ZERO0 = { '0', '.' };
	public static final char[] DOT_ZERO = { '.', '0' };
	private static final float[] f_magnitudes = {
	        1e-44F, 1e-43F, 1e-42F, 1e-41F, 1e-40F,
	        1e-39F, 1e-38F, 1e-37F, 1e-36F, 1e-35F, 1e-34F, 1e-33F, 1e-32F, 1e-31F, 1e-30F,
	        1e-29F, 1e-28F, 1e-27F, 1e-26F, 1e-25F, 1e-24F, 1e-23F, 1e-22F, 1e-21F, 1e-20F,
	        1e-19F, 1e-18F, 1e-17F, 1e-16F, 1e-15F, 1e-14F, 1e-13F, 1e-12F, 1e-11F, 1e-10F,
	        1e-9F, 1e-8F, 1e-7F, 1e-6F, 1e-5F, 1e-4F, 1e-3F, 1e-2F, 1e-1F,
	        1e0F, 1e1F, 1e2F, 1e3F, 1e4F, 1e5F, 1e6F, 1e7F, 1e8F, 1e9F,
	        1e10F, 1e11F, 1e12F, 1e13F, 1e14F, 1e15F, 1e16F, 1e17F, 1e18F, 1e19F,
	        1e20F, 1e21F, 1e22F, 1e23F, 1e24F, 1e25F, 1e26F, 1e27F, 1e28F, 1e29F,
	        1e30F, 1e31F, 1e32F, 1e33F, 1e34F, 1e35F, 1e36F, 1e37F, 1e38F
	};

	private static final char[] charForDigit = {
	        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
	        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};

	private static final char[] ZEROS = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' };

	public static void main(String[] args)
	{
		StringBuffer buf = new StringBuffer();

		append(buf, 100.45f);

		System.out.println(buf.toString());
	}

	public static void append(StringBuffer s, float d)
	{
		//handle the various special cases
		if(d == Float.NEGATIVE_INFINITY)
		{
			s.append(NEGATIVE_INFINITY);
		}
		else if(d == Float.POSITIVE_INFINITY)
		{
			s.append(POSITIVE_INFINITY);
		}
		else if(d != d)
		{
			s.append(NaN);
		}
		else if(d == 0.0)
		{
			//can be -0.0, which is stored differently
			if((Float.floatToIntBits(d) & floatSignMask) != 0)
			{
				s.append('-');
			}
			s.append(DOUBLE_ZERO);
		}
		else
		{
			//convert negative numbers to positive
			if(d < 0)
			{
				s.append('-');
				d = -d;
			}
			//handle 0.001 up to 10000000 separately, without exponents
			if(d >= 0.001F && d < 0.01F)
			{
				long i = (long)(d * 1E12F);
				i = i % 100 >= 50 ? (i / 100) + 1 : i / 100;
				s.append(DOUBLE_ZERO2);
				appendFractDigits(s, i, -1);
			}
			else if(d >= 0.01F && d < 0.1F)
			{
				long i = (long)(d * 1E11F);
				i = i % 100 >= 50 ? (i / 100) + 1 : i / 100;
				s.append(DOUBLE_ZERO);
				appendFractDigits(s, i, -1);
			}
			else if(d >= 0.1F && d < 1F)
			{
				long i = (long)(d * 1E10F);
				i = i % 100 >= 50 ? (i / 100) + 1 : i / 100;
				s.append(DOUBLE_ZERO0);
				appendFractDigits(s, i, -1);
			}
			else if(d >= 1F && d < 10F)
			{
				long i = (long)(d * 1E9F);
				i = i % 100 >= 50 ? (i / 100) + 1 : i / 100;
				appendFractDigits(s, i, 1);
			}
			else if(d >= 10F && d < 100F)
			{
				long i = (long)(d * 1E8F);
				i = i % 100 >= 50 ? (i / 100) + 1 : i / 100;
				appendFractDigits(s, i, 2);
			}
			else if(d >= 100F && d < 1000F)
			{
				long i = (long)(d * 1E7F);
				i = i % 100 >= 50 ? (i / 100) + 1 : i / 100;
				appendFractDigits(s, i, 3);
			}
			else if(d >= 1000F && d < 10000F)
			{
				long i = (long)(d * 1E6F);
				i = i % 100 >= 50 ? (i / 100) + 1 : i / 100;
				appendFractDigits(s, i, 4);
			}
			else if(d >= 10000F && d < 100000F)
			{
				long i = (long)(d * 1E5F);
				i = i % 100 >= 50 ? (i / 100) + 1 : i / 100;
				appendFractDigits(s, i, 5);
			}
			else if(d >= 100000F && d < 1000000F)
			{
				long i = (long)(d * 1E4F);
				i = i % 100 >= 50 ? (i / 100) + 1 : i / 100;
				appendFractDigits(s, i, 6);
			}
			else if(d >= 1000000F && d < 10000000F)
			{
				long i = (long)(d * 1E3F);
				i = i % 100 >= 50 ? (i / 100) + 1 : i / 100;
				appendFractDigits(s, i, 7);
			}
			else
			{
				//Otherwise the number has an exponent
				int magnitude = magnitude(d);
				long i;
				if(magnitude < -35)
				{
					i = (long)(d * 1E10F / f_magnitudes[magnitude + 45]);
				}
				else
				{
					i = (long)(d / f_magnitudes[magnitude + 44 - 9]);
				}
				i = i % 100 >= 50 ? (i / 100) + 1 : i / 100;
				appendFractDigits(s, i, 1);
				s.append('E');
				append(s, magnitude);
			}
		}
		//return this;
	}

	private static int magnitude(float d)
	{
		return magnitude(d, Float.floatToIntBits(d));
	}

	private static int magnitude(float d, int floatToIntBits)
	{
		int magnitude =
		        (int)((((floatToIntBits & floatExpMask) >> floatExpShift)
		        - floatExpBias) * 0.301029995663981);

		if(magnitude < -44)
		{
			magnitude = -44;
		}
		else if(magnitude > 38)
		{
			magnitude = 38;
		}

		if(d >= f_magnitudes[magnitude + 44])
		{
			while(magnitude < 39 && d >= f_magnitudes[magnitude + 44])
			{
				magnitude++;
			}
			magnitude--;
			return magnitude;
		}
		else
		{
			while(magnitude > -45 && d < f_magnitudes[magnitude + 44])
			{
				magnitude--;
			}
			return magnitude;
		}
	}

	private static void appendFractDigits(StringBuffer s, long i, int decimalOffset)
	{
		long mag = magnitude(i);
		long c;
		while(i > 0)
		{
			c = i / mag;
			s.append(charForDigit[(int)c]);
			decimalOffset--;
			if(decimalOffset == 0)
			{
				s.append('.'); //change to use international character
			}
			c *= mag;
			if(c <= i)
			{
				i -= c;
			}
			mag = mag / 10;
		}
		if(i != 0)
		{
			s.append(charForDigit[(int)i]);
		}
		else if(decimalOffset > 0)
		{
			s.append(ZEROS[decimalOffset]); //ZEROS[n] is a char array of n 0's
			decimalOffset = 1;
		}

		decimalOffset--;
		if(decimalOffset == 0)
		{
			s.append(DOT_ZERO);
		}
		else if(decimalOffset == -1)
		{
			s.append('0');
		}
	}

}
