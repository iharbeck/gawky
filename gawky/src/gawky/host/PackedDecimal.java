package gawky.host;


/*class DecimalWrapper 
{
	private final static byte MAX_SIZE = 20; 
	private boolean negative = false;

	private int sizeint;
	private int sizedecimal;

	private char partint[]     = new char[MAX_SIZE];
	private char partdecimal[] = new char[MAX_SIZE];

	public DecimalWrapper(String str) throws NumberFormatException 
	{
		boolean indecimalpart = false;
		
		int len = str.length();
		int pos = 0;

		while (pos < len) 
		{
			char curchar = str.charAt(pos);

			switch (curchar) 
			{
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					if (indecimalpart) {
						partdecimal[sizedecimal] = curchar;
						sizedecimal++;
					} else {
						partint[sizeint] = curchar;
						sizeint++;
					}
					break;
				case '+':
					break;
				case '-':
					negative = true;
					break;
				case '.':
					indecimalpart = true;
					break;
				default:
					throw new NumberFormatException("invalid character " + curchar);
			}
			pos++;
		}

	}

	// Accessors
	public boolean isNegative() {
		return negative;
	}

	public int getIntegerSize() {
		return sizeint;
	}

	public byte getDecimalDigit(int pos) {
		if (pos+1 > sizedecimal) {
			return 0;
		} else {
			return ByteHandler.buildByte(
					ByteHandler.getLowNibble((byte) (partdecimal[pos])), 
					(byte) (0));
		}
	}

	public byte getIntegerDigit(int pos) {
		return ByteHandler.buildByte(
				ByteHandler.getLowNibble((byte) (partint[pos])), 
				ByteHandler.getLowNibble((byte) (0)));
	}
}
*/

public class PackedDecimal 
{
	
	public static void main(String[] args) {
		
//		PackedDecimal pack = new PackedDecimal(10, 0);
//		
//		byte [] b = pack.pack("1366");
		
//		System.out.println("bb" + new String(b));
//
//		System.out.println("aa" + PackedDecimal.readNumberPacked(b, 6, true));
		
		
		
		byte[] b = PackedDecimal.writeNumberPacked(10, 0, false);
		
		System.out.println("bb" + new String(b) + "cc");
		
		//System.out.println("bb" + pack.unpack(b));
		
		//System.out.println("aa" + PackedDecimal.readNumberPacked(b, 6, true));
		
	}
	
/*	private static final byte NEGATIVE = 0x0d;
	private static final byte POSITIVE = 0x0c;
	private static final byte ASCII_HIGH_CHAR_NIBBLE = (byte) (0x30);
	private static final int  MAX_SIZE = 100;

	private int intsize;     
	private int decimalsize; 
	
	protected int size;      

	protected boolean even;
	
	public PackedDecimal(int intsize, int decimalsize) {
		this.intsize = intsize;
		this.decimalsize = decimalsize;
		
		this.size = intsize + decimalsize;
		
		this.even = (this.size % 2 == 0);
		
		if (this.even)
			this.size = (size / 2) + 1;
		else
			this.size = (size + 1) / 2;
	}

	//
	// Build a packed decimal from a string number

	// * Convert "+-99999.99" in  IBM packed decimal 
	// * last digit is the sign digit : A|C|E|F => + ; B|D => - ; the decimal point is virtual its position is
	// * defined in the second byte of dec_len
	// * 
	// * @param number
	// *            decimal String representation to be converted
	public byte[] pack(String number) throws NumberFormatException 
	{
		byte bytes[] = new byte[size]; 

		int i;
		int j;
		
		boolean decimalpart = false;
		byte curdigit;
		int  bytepos = size - 1;
		
		boolean high = true;

		DecimalWrapper decimalwrapper = new DecimalWrapper(number);

		if (decimalwrapper.isNegative())
			bytes[bytepos] = NEGATIVE;
		else
			bytes[bytepos] = POSITIVE;

		if (decimalsize != 0)
			decimalpart = true;

		i = decimalsize;
		j = decimalwrapper.getIntegerSize();

		while ((bytepos != -1) && (j != 0)) 
		{
			if (decimalpart) {
				curdigit = decimalwrapper.getDecimalDigit(--i);  
				if (i == 0)
					decimalpart = false;
			} else {
				curdigit = decimalwrapper.getIntegerDigit(--j);  
			}
			
			if (high) {
				bytes[bytepos] = ByteHandler.buildByte(
								  ByteHandler.getLowNibble(bytes[bytepos]), 
								  ByteHandler.getLowNibble(curdigit));
				bytepos--;
			} 
			else {
				bytes[bytepos] = ByteHandler.buildByte(
								  ByteHandler.getLowNibble(curdigit),
								  ByteHandler.getLowNibble(bytes[bytepos]));
			}
			high = !high; // next digit
		}

		return bytes;
	}

	public String unpack(byte[] packed) 
	{
		char bytes[] = new char[MAX_SIZE];
		char sign = ' ';

		int ibytes  = 1;  
		int ipacked = 0;  

		boolean hasDecimalPartOnly = false;

		if (decimalsize != 0)
			hasDecimalPartOnly = true;

		if (intsize == 0) {
			bytes[ibytes++] = '0';
			bytes[ibytes++] = '.';
		}
		
		boolean end = false;  
		boolean high = even;       // even -> start on high nibble 

		boolean isnotnull = false; // skip leading 0s
		
		byte curdigit; 		  
		int  digits = 0; 		   // number of parsed digits 

		while (!end) 
		{
			digits++;
			high = !high;

			if (high) {
				curdigit = ByteHandler.getHighNibble(packed[ipacked]);
			} else {
				curdigit = ByteHandler.getLowNibble(packed[ipacked]);
				ipacked++;
			}

			switch (curdigit) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
					if ((curdigit != 0) || (isnotnull)) {
						isnotnull = true; 
						bytes[ibytes++] = (char) (ByteHandler.buildByte(
								 							curdigit,
															ByteHandler.getHighNibble(ASCII_HIGH_CHAR_NIBBLE)));
						if ((digits == intsize) && (decimalsize != 0)) {  // vorkomma erreicht && hat nachkomma
							bytes[ibytes++] = '.';
							hasDecimalPartOnly = false;
						}
					}
					break;
				case 0x0A: // positive value -> no sign
				case 0x0C:
				case 0x0E:
				case 0x0F:
					sign = ' ';  
					end = true; // last byte done
					break;
				default: //0x0B | 0x0D
					sign = '-';  
					end = true;	// last byte done
					break;
			} 
		} 

		if (!isnotnull)
			return "0";

		if (hasDecimalPartOnly) { // +-0.9999
			return (sign + "0." + new String(bytes).trim()); 
		} else {
			bytes[0] = sign;
			return new String(bytes).trim();
		}
	}
*/	
	
	
	
	public static byte[] writeNumberPacked(final int len, long number, final boolean sign) 
    {
    	    byte[] buffer = new byte[len];
    	    
            int pos = 0;
            final int nibbles = len * 2;
            final int digits = nibbles - (sign ? 1 : 0);
            int exp = digits - 1;
            byte curbyte = 0;
            byte digit;
            boolean highNibble = true;
            
            boolean neg = number < 0L;
            
            if(neg)
            	number = -number;
            
            for(int i = 0; i < nibbles; i++, exp--) 
            {
                // Vorzeichen im letzten Nibbles.
                if(sign && exp < 0) {
                	if(neg)
                		digit = 0xD;
                	else
                		digit = 0xC;
                } else {
                    digit = (byte) Math.floor(number / EXP10[exp]);
                    
                    number -= (digit * EXP10[exp]);
                }
                if(highNibble) {
                    curbyte = (byte) (((byte) (digit << 4)) & 0xF0);
                    highNibble = false;
                } else {
                    curbyte |= digit;
                    highNibble = true;
                    buffer[pos++] = curbyte;
                }
            }
            
        	return buffer;
    }
	
	
	static int MAX_DIGITS = 17 + 10;
	static long[] EXP10 = new long[MAX_DIGITS + 1];    

	static {
	    for(int i = 0; i <= MAX_DIGITS; i++)
            EXP10[i] = (long) Math.floor(Math.pow(10.00D, i));
	}
	
	public static long readNumberPacked(byte buffer[], final int len, final boolean sign) 
	{
        long ret = 0L;
        final int nibbles = 2 * len;
        int exp = nibbles - (sign ? 2 : 1);
        int nibble = 0;
        int pos = 0;
        int digit;
        
        boolean highNibble = true;

        try {
        	for(; nibble < nibbles; nibble++, exp--) 
        	{
                if(highNibble) {
                    if(buffer[pos] < 0)
                        digit = (buffer[pos] + 256) >> 4;
                    else
                        digit = buffer[pos] >> 4;
                    
                    highNibble = false;
                } else {
                    digit = (buffer[pos++] & 0xF);
                    highNibble = true;
                }
                
                // Vorzeichen des letzten Nibbles.
                if(sign && exp < 0) {
                    if(digit != 0x0C && 
                       digit != 0x0A && 
                       digit != 0x0E && 
                       digit !=0x0F) { //(neg)0x0B:0x0D    (pos)0x0A:0x0C:0x0E:0x0F:
                    	ret = -ret;
                    }
                } else {
                    if(digit < 0 || digit > 9) {
                    	digit = 0;
                    }
                    ret += (digit & 0xF) * EXP10[exp]; 
                }
            }
            
            return ret;
        } catch(Exception e) {
        	
        }
        return -1;
    }
}
