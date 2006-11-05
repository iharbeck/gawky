package gawky.host;

import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.Locale;

class DecimalWrapper 
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

	/**
	 * get Digits 
	 */
	public byte getDecimalDigit(int pos) {
		if (pos+1 > sizedecimal) {
			return 0;
		} else {
			return ByteMover.buildByte(
					ByteMover.getLowNibble((byte) (partdecimal[pos])), 
					(byte) (0));
		}
	}

	public byte getIntegerDigit(int pos) {
		return ByteMover.buildByte(
				ByteMover.getLowNibble((byte) (partint[pos])), 
				ByteMover.getLowNibble((byte) (0)));
	}
}

public class PackedDecimal 
{
	private static final byte NEGATIVE = 0x0d;
	private static final byte POSITIVE = 0x0c;
	private static final byte ASCII_HIGH_CHAR_NIBBLE = (byte) (0x30);
	private static final int  MAX_SIZE = 100;

	private int intsize;     
	private int decimalsize; 
	
	protected int size;      

	protected boolean even;
	
	/**
	 * @param intsize  non decimal part size
	 * @param decsize  decimal part size
	 */
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

	/**
	 * Build a packed decimal from a string number
	 * 
	 * Convert "+-99999.99" in  IBM packed decimal 
	 * last digit is the sign digit : A|C|E|F => + ; B|D => - ; the decimal point is virtual its position is
	 * defined in the second byte of dec_len
	 * 
	 * @param number
	 *            decimal String representation to be converted
	 */
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
				bytes[bytepos] = ByteMover.buildByte(
								  ByteMover.getLowNibble(bytes[bytepos]), 
								  ByteMover.getLowNibble(curdigit));
				bytepos--;
			} 
			else {
				bytes[bytepos] = ByteMover.buildByte(
								  ByteMover.getLowNibble(curdigit),
								  ByteMover.getLowNibble(bytes[bytepos]));
			}
			high = !high; // next digit
		}

		return bytes;
	}

	/**
	 * Translate current Packed into a String representation
	 * 
	 * @return String representation of packed decimal
	 */
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
				curdigit = ByteMover.getHighNibble(packed[ipacked]);
			} else {
				curdigit = ByteMover.getLowNibble(packed[ipacked]);
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
						bytes[ibytes++] = (char) (ByteMover.buildByte(
								 							curdigit,
															ByteMover.getHighNibble(ASCII_HIGH_CHAR_NIBBLE)));
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
	
	
	
	
	static void writeNumberPackedPositive(final int len, long number, final boolean sign) 
    {
    		int FORMAT_MAX_DIGITS = 17;
    	    long[] EXP10 = new long[FORMAT_MAX_DIGITS + 1];    
    	
    	    for(int i = 0; i <= FORMAT_MAX_DIGITS; i++)
                EXP10[i] = (long) Math.floor(Math.pow(10.00D, i));
    	    
    	    int FORMAT_MAX_CHARS = 105;
    	    byte[] buffer = new byte[FORMAT_MAX_CHARS + 1];
    	    
            int i;
            int pos = 0;
            final int nibbles = len * 2;
            final int digits = nibbles - (sign ? 1 : 0);
            int exp = digits - 1;
            final long maxValue =  EXP10[digits] - 1L;
            byte b = 0;
            byte digit;
            boolean highNibble = true;
            
            if(number < 0L || number > maxValue) {
                throw new IllegalArgumentException("number=" + number +
                                                   ", maxValue=" + maxValue);
                
            }
            
            for(i = 0; i < nibbles; i++, exp--) {
                // Vorzeichen des letzten Nibbles.
                if(sign && exp < 0) {
                    digit = 0xC;
                } else {
                    digit = (byte) Math.floor(number / EXP10[exp]);
                    
                    number -= (digit * EXP10[exp]);
                }
                if(highNibble) {
                    b = (byte) (((byte) (digit << 4)) & 0xF0);
                    highNibble = false;
                } else {
                    b |= digit;
                    highNibble = true;
                    buffer[pos++] = b;
                }
            }
            
        	System.out.println(new String(buffer).substring(0,len));
            //this.persistence.writeBlock(block, off, this.buffer, 0, len);
        }
	
	public static long readNumberPackedPositive(byte buffer[], 
	         final int len, final boolean sign, Locale locale) 
	{
		int FORMAT_MAX_DIGITS = 17;
	    long[] EXP10 = new long[FORMAT_MAX_DIGITS + 1];    
	
	    for(int i = 0; i <= FORMAT_MAX_DIGITS; i++)
            EXP10[i] = (long) Math.floor(Math.pow(10.00D, i));
	    
        long ret = 0L;
        final int nibbles = 2 * len;
        int exp = nibbles - (sign ? 2 : 1);
        boolean highNibble = true;
        int nibble = 0;
        int read = 0;
        int digit;
        
        try {

        	for(; nibble < nibbles; nibble++, exp--) {
                if(highNibble) {
                    if(buffer[read] < 0)
                        digit = (buffer[read] + 256) >> 4;
                    else
                        digit = buffer[read] >> 4;
                    
                    highNibble = false;
                } else {
                    digit = (buffer[read++] & 0xF);
                    highNibble = true;
                }
                
                // Vorzeichen des letzten Nibbles.
                if(sign && exp < 0) {
                    if(digit != 0xC) {
//                        throw new IllegalFormatException(field, block, off,
//                            "0xC", Integer.toString(digit), locale);
                       return -1;   
                    }
                } else {
                    if(digit < 0 || digit > 9) {
//                        throw new IllegalFormatException(field, block, off,
//                            "[0x0..0x9]{" + (nibbles - (sign ? 1 : 0)) + "}0xC",
//                            Integer.toString(digit), locale);
                    	return -1; 
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
