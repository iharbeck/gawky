package gawky.message;

import java.math.BigInteger;

import gawky.global.Matcher;

/*
  Generate Char and Number Format
*/

public class Formatter
{
   public final static byte[] bpad(int size, byte[] value)
   {
	   byte[] target = new byte[size];
	   
	   if(value != null) 
	   {
		   for(int i=size, x=value.length; i > 0; i--, x--) 
		   {
			   if(x <= 0)
				   target[i-1] = (char)0;
			   else
				   target[i-1] = value[x-1];
		   }
	   }
	   
	   //System.arraycopy(value, 0, target, pos, desc.len);
	   
	   return target;
   }
      
   public static void ___main(String[] args) 
   {
	   int avg = 0;
	   for(int x = 1; x < 50; x++)
	   {
		   long start = System.currentTimeMillis();
		   String val = "";
		   for(int i = 1; i < 30000; i++)
		     	val = ltrim("00000ingo    sjdkasjdklajdklasjdljsljlsdjklsajlsjad" + System.currentTimeMillis(), '0');
		   
		   avg += System.currentTimeMillis() - start;
		   System.out.println(val);
	   }
	   System.out.println(avg / 50);
   }
   
   public static void _main(String[] args) 
   {
	   byte[] pass = new byte[] {}; //'1', '2', '3', '1', '2', '3'};
	 
	   byte[] fin = bpad(6, pass);
	   
	   long t = 37000;
	   
	   byte[] v1 = convertNum2Bytes(t);
	   byte[] v2 = new String(convertNum2Bytes(t)).getBytes();
	   
	   
	   System.out.println(convertBytes2Num(v1));
	   System.out.println(convertBytes2Num(v2));
	   
	   
	   System.out.println(fin);
   }
   
   public final static long convertBytes2Num(byte[] val) {
	   try {
		   return new BigInteger(val).longValue();
	   } catch (Exception e) {
		   return 0L;
	   }
   }
   public final static byte[] convertNum2Bytes(long val) {
	   return new BigInteger(Long.toString(val)).toByteArray();
   }
	
   public final static String getStringN(int size, long value) {
	   return getStringN(size, Long.toString(value));
   }

   public final static String getStringN(int size, String value)
   {
	   return lpad(size, value, '0');
   }

   public final static String ltrim(String value)
   {
	   return ltrim(value, ' ');
   }
   
   
   public static void main(String[] args) 
   {
	    System.out.println(lntrim("000000"));
	    System.out.println(lntrim(""));
	    System.out.println(lntrim("2"));
	    System.out.println(lntrim("00000123"));
	   	System.out.println(lntrim("000001.23"));
	   	System.out.println(lntrim("0000012,3"));
	   	System.out.println(lntrim("00000.123"));
	   	System.out.println(lntrim("000000"));
   }

   
   public final static String lntrim(String value)
   {
	   int len = value.length();

	   if(len == 0)
		   return "";
	   
	   int i = 0;
	   while ((i < len) && (value.charAt(i) == '0')) {
		    i++;
	   }
	   
	   if(i == len)  // nichts bleibt �ber
		   return "0";
	   
	   if(i > 0)
	   {
		   char ch = value.charAt(i);  // letztes Zeichen decimal
		   
		   if(ch == '.' || ch == ',')
			   i--;
			   
		   return value.substring(i, len);
	   }
	   
	   return value; // keine nullen		  
   }

   
   public final static String ltrim(String value, char ch)
   {
	   int len = value.length();

	   int i=0;
	   while ((i < len) && (value.charAt(i) == ch)) {
		    i++;
	   }
	   
	   if(i > 0)
		   return value.substring(i, len);
	   
	   return value;			  
   }

   public final static String rtrim(String value)
   {
	   return rtrim(value, ' ');
   }
   
   public final static String rtrim(String value, char ch)
   {
	   int count = value.length();

	   if(count == 0)
		   return value;
	   
	   int len = count;
	   
	   while ((len > 0) && (value.charAt(len-1) <= ch)) {
		    len--;
	   }

	   if(count == len)
		   return value;
	   
	   return value.substring(0, len);
   }

   
   public final static String rtrim_old(String value)
   {
	   int len = value.length();
	   char[] src = value.toCharArray();

	   while ((len > 0) && (src[len-1] <= ' ')) {
		    len--;
	   }

	   return value.substring(0, len);
   }

   static char[] iv = "\n\r\0\t".toCharArray();
   
//   public final static String lpad(int size, String value, char filler)
//   {
//	   return lpad(size, value, filler, false);
//   }
   
   public final static String lpad(int size, String value, char filler) //, boolean binary)
   {
     if(value == null)
         value = "";

	 if(size == 0)
		 return value;

     char[] target = new char[size];

     int len = value.length();
     int spos = (len <= size) ? len : size ;
     
     value.getChars(0, spos, target, size-spos);
     
     for(int i=0; i < size-spos; i++)
   	    target[i] = filler;
   	
     //if(!binary) 
     {
	     for(int i=0; i < size; i++) {
	   	   for(int a=0; a < 4; a++) 
	   		  if(target[i] == iv[a]) {
	   			  target[i] = ' ';
	   			  continue;
	   		  }
	     }
     }
     return new String(target);
   }
   
   public final static String rpad(int size, String value, char filler)
   {
	  if(value == null)
	      value = "";
	   
	  if(size == 0)
		 return value;

	  
	  char[] target = new char[size];
     
      int len = value.length();
      int epos = (len <= size) ? len : size;
      
      value.getChars(0, epos, target, 0);
      
      for(int i=epos; i < size; i++)
    	  target[i] = filler;
    
    
      for(int i=0; i < size; i++) {
       	  for(int a=0; a < 4; a++)
       		  if(target[i] == iv[a]) {
       			  target[i] = ' ';
       			  continue;
       		  }
      }
      
      return new String(target);
   }
   
   public final static String getStringNL(int size, long value) {
	   return getStringNL(size, Long.toString(value));
   }
   
   public final static String getStringNL(int size, String value)
   {
	 return rpad(size, value, '0');  
   }

   static Matcher spaces = new Matcher("[\n\r\0\t]");
   
   public final static String getStringC(int size, String value)
   {
	  return rpad(size, value, ' ');
   }

   public final static String getStringCR(int size, String value)
   {
	   return lpad(size, value, ' ');  
   }


   public final static String getStringV(int size, String value) 
   {
	   return getStringV(size, value, ""+'\001');
   }
   
   public final static String getStringV(int size, String value, String delim) 
   {
	   if (value == null)
          value = "";
    
	   if(value.length() > size && size > 0)
		   value = value.substring(0, size);
	   
	   return (value + delim);
   }

   public final static String getSpacer(int len)
   {
     return rpad(len, null, ' ');
   }

}

