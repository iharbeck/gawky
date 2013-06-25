package gawky.xml.simple;

import gawky.processor.BaseObject;
import gawky.processor.BaseObjectI;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class SimpleVTD {

	//tag position
	int pos = 0;
	//tag ende
	int epos = 0;
	
	//child ende
	int cepos = 0;
	
	// nav depth
	int depth = 0;
	
	String xml = null;

	public SimpleVTD() {
	}
	
	public SimpleVTD(String filename) throws Exception {
		initFile(filename);
	}
	
	public byte[] readFile(String filename) throws Exception
	{
		File f = new File( filename ); 
    	byte[] buffer = new byte[ (int) f.length() ]; 
    	InputStream in = new FileInputStream( f ); 
    	in.read( buffer ); 
    	in.close();
    	
    	return buffer;
	}

	public void initFile(String filename) throws Exception
	{
		xml = new String(readFile(filename));
		toStart();
	}
	
	public void parse() 
	{
	
		
		
		long length = xml.length();
		
		for(int i = 0; i < length; i++)
		{
			xml.charAt(0);
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();

		short a = 44;
		short b = 33;

		int store =  ( a | (b << 8));
		
		System.out.println(store & 0x00ff);
		System.out.println((store >> 8)); // & 0x00ff);

		
		BaseObjectI obj = new BaseObject();

		int count = 0;
	    long totalamount = 0;
		
		SimpleVTD simpleparser = new SimpleVTD("c:/test.xml");
	
	    simpleparser.parse();
	    
		ArrayList<String> username = new ArrayList<String>();
		
		while(simpleparser.toTagEmpty("booking") != -1 )
		{
			obj.setAmount   ("1");//parser.getAttribut("amount"));
			obj.setFirstname(simpleparser.getAttribut("fn"));
			obj.setLastname (simpleparser.getAttribut("ln"));
							 simpleparser.getAttribut("ne");
			obj.setAmount   (simpleparser.getAttribut("am"));
			
			if(obj.getAmount() != null)
				totalamount += Long.parseLong(obj.getAmount());

			if(!username.contains(obj.getLastname()))
				username.add(obj.getLastname());
			
			count ++;
		}
		
		System.out.println("#:" + count + " amount: " + totalamount + " ms: " + (System.currentTimeMillis() -start));
		System.out.println("UNIQUE CUSTOMERS:");
		
		for (Iterator iter = username.iterator(); iter.hasNext();) {
			System.out.println((String) iter.next());
		} 
		
	}
	
	public final void toStart() {
		pos = 0;
		depth = 0;
	}
	/**
	 * detect EMPTY Tag
	 * @param name
	 * @return
	 */
	public final int toTagEmpty(String name)
	{
		pos  = xml.indexOf("<" + name + " ", pos);
		
		if(pos != -1) 
			pos = pos + name.length() + 1;
		else 
			return -1;
		
		epos = xml.indexOf("/>", pos); 
		// no children
		cepos = -1;
		
		return pos;
	}
	
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
	
	public final int toChildTag(String name) {
		return -1;
	}
	
	public final String getAttribut(String name)
	{
		// Tag String ermitteln
		String tag = xml.substring(pos, epos);
		
		int apos = tag.indexOf(name + "=\"");
		
		if(apos == -1)
			return null;
		
		apos = apos + name.length() + 2;
		
		return tag.substring(apos, tag.indexOf("\"", apos));
	}

	
	public void cleanup () 
	{
//		char[] val = xml.toCharArray();
		
//		int len = val.length;
//	
//		char[] target = new char[len];
//		int l = 0;
//		
//		boolean prevblank = false;
//		
//		for(int i=0; i < len; i++)
//		{
//			if(val[i] == ' ' && prevblank) 
//				continue;
//			else if(val[i] == ' ') 
//				prevblank = true;
//			else 
//				prevblank = false;
//			
//			target[l++] = val[i]; 
//		}
		
//		System.out.println(target);
	}
}
