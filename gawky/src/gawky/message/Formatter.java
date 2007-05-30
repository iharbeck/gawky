package gawky.message;

import java.util.regex.Pattern;

/*
  Generate Char and Number Format
*/

public class Formatter
{
   public final static String getStringN(int size, long value) {
	   return getStringN(size, Long.toString(value));
   }

   public final static String getStringN(int size, String value)
   {
	   return lpad(size, value, '0');
   }

   public final static String ltrim(String value)
   {
	   int len = value.length();
	   char[] src = value.toCharArray();

	   int i=0;
	   while ((i < len) && (src[i] <= ' ')) {
		    i++;
	   }
	   
	   return value.substring(i, len);
   }

   public final static String rtrim(String value)
   {
	   int len = value.length();
	   char[] src = value.toCharArray();

	   while ((len > 0) && (src[len-1] <= ' ')) {
		    len--;
	   }

	   return value.substring(0, len);
   }

   static char[] iv = "\n\r\0\t".toCharArray();
   
   public final static String lpad(int size, String value, char filler)
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
   	
     
     for(int i=0; i < size; i++) {
   	  for(int a=0; a < 4; a++)
   		  if(target[i] == iv[a]) {
   			  target[i] = ' ';
   			  continue;
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
    
	   return (value + delim);
   }

   public final static String getSpacer(int len)
   {
     return rpad(len, null, ' ');
   }

}

class Matcher 
{
    Pattern pattern;
   
    public Matcher(String regex) { 
    	pattern = Pattern.compile(regex);   
    }
  
    public final String process(String text, String replace) {
    	return pattern.matcher(text).replaceAll(replace);
    }
}