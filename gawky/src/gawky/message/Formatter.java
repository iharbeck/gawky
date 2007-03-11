package gawky.message;

/*
  Generate Char and Number Format
*/

public class Formatter
{

	   public final static String getStringN(int size, String value)
	   {
	     if(value == null)
	        value = "";
	     
	     value = value.replaceAll("[\n\r\0\t]", " ");
	     
	     if(size == 0)
	    	 return value;
	     
	     return (getSpacer(size-value.length(), "0") + value).substring(0, size);
	   }

	   public final static String getStringNL(int size, String value)
	   {
	     if(value == null)
	    	 value = "";
	    
	     if(size == 0)
	    	 return value;
	    
	     return (value + getSpacer(size-value.length(), "0") ).substring(0, size);
	   }

	   public final static String getStringC(int size, String value)
	   {
	     if(value == null)
	      value = "";
	    
	     value = value.replaceAll("[\n\r\0\t]", " ");
	    
	     if(size == 0)
	    	 return value;
	    
	     return (value + getSpacer(size-value.length())).substring(0, size);
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
	     return getSpacer(len, " ");
	   }

	   public final static String getSpacer(int len, String filler)
	   {
		 StringBuilder value = new StringBuilder(200);

	     for (int i=0; i < len; i++) {
	       value.append(filler);
	     }
	     return value.toString();
	   }

}