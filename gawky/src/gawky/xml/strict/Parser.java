package gawky.xml.strict;

import gawky.lang.Stringer;

public class Parser {

	int pos = 0;   //tag position
	int epos = 0;  //tag ende

	int cepos = 0; //child ende
	
	String xml = null;
	char[] cxml;
	
	int size;
	
	Stringer builder;
	
	public Parser(byte[] data, String encoding) throws Exception {
		xml = new String(data, encoding);
		builder = new Stringer(xml);

		cxml = xml.toCharArray();
		size = xml.length();
	}
	
	public final void toStart() {
		pos = 0;
	}

	/**
	 * Find position of EMPTY Tag
	 * @param name
	 * @return
	 */
	public final int toTagEmpty(String name)
	{
		epos = size-1;
		pos = movetag(name);
		
		if(pos == -1)
			return -1;
		
		epos = movegeneral("/>"); 
		
		// no children
		//cepos = -1;
		
		return pos;
	}

	public final int movegeneral(String name) 
	{
		int targetcount = name.length(); // length;

		char first  = name.charAt(0); // [0];
		for (int i = pos; i <= epos; i++) {
            /* Look for first character. */
            if (cxml[i] != first) {
                while (++i <= epos && cxml[i] != first);
            }
            /* Found first character, now look at the rest of v2 */
            if (i <= epos) 
            {
                int end = i + targetcount;
                i++;
                for (int k = 1; i < end && cxml[i] == name.charAt(k); i++, k++)
                	;
                if(i == end) { // && cxml[i+1] == '=') {
                	return i;
                }
            }
        }
		
		return -1;
	}
	
	public final int movetag(String name) 
	{
		int targetcount = name.length(); // length;

		char first = '<'; // [0];
		for (int i = pos; i <= epos; i++) {
            /* Look for first character. */
            if (cxml[i] != first) {
                while (++i <= epos && cxml[i] != first);
            }
            /* Found first character, now look at the rest of v2 */
            if (i <= epos) 
            {
                int end = i + targetcount;
                i++;
                for (int k=0; i < end && cxml[i] == name.charAt(k); i++, k++)
                	;
                if(i == end && cxml[i+1] == ' ') {
                	return i+2;
                }
            }
        }
		
		return -1;
	}
	
	public final int moveattribut(String name) 
	{
		int targetcount = name.length(); // length;

		char first  = name.charAt(0); 
		for (int i = pos; i <= epos; i++) {
            /* Look for first character. */
            if (cxml[i] != first) {
                while (++i <= epos && cxml[i] != first);
            }
            /* Found first character, now look at the rest of v2 */
            if (i <= epos) 
            {
                int end = i + targetcount;
                for (int k=0; i < end && cxml[i] == name.charAt(k); i++, k++)
                	;
                if(i == end  && cxml[i] == '=') {
                	return i;
                }
            }
        }
		
		return -1;
	}
	
	public final String getAttribut(String name)
	{
		int a = moveattribut(name);
		
		if(a == -1)
			return "";

		int start = a + 2;  // ="
		
		a++;

		while (++a <= epos && cxml[a] != '"');
		
		return xml.substring(start, a);
	}

	public final void setAttribut(String name, String val)
	{
		int a = moveattribut(name);
		
		if(a == -1)
			return;

		int start = a + 2;  // ="
		
		a++;

		while (++a <= epos && cxml[a] != '"');
		
		builder.replace(start, a, val);
	}
	
	public String getDoc() {
		return builder.toString();
	}
	
	/**
	 * Find position of FULL Tag with Attributes
	 * @param name
	 * @return
	 */
	public final int toTagFull(String name)
	{
		pos  = xml.indexOf("<" + name + " ", pos);
		
		if(pos != -1) 
			pos = pos + name.length() + 1;
		else 
			return -1;
		
		epos = xml.indexOf(">", pos);
		// children to this pos
		cepos = xml.indexOf("</" + name + ">", pos); 
		
		return pos;
	}
}
