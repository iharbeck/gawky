package gawky.xml.simple;

import gawky.xml.vtd.BaseObject;
import gawky.xml.vtd.BaseObjectI;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Simple {

	int pos = 0;
	int epos = 0;
	
	String xml = null;

	public Simple() {
	}
	
	public Simple(String filename) throws Exception {
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
	}
	
	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();

		BaseObjectI obj = new BaseObject();

		int count = 0;
	    long totalamount = 0;
		
		Simple parser = new Simple("c:/test.xml");
	
		ArrayList username = new ArrayList();
		
		while(parser.toTag("booking") != -1 )
		{
			obj.setFirstname(parser.getAttribut("fn"));
			obj.setLastname (parser.getAttribut("ln"));
							 parser.getAttribut("ne");
			obj.setAmount   (parser.getAttribut("am"));
			
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
	
	public final int toTag(String name)
	{
		pos  = xml.indexOf("<" + name + " ", pos);
		
		if(pos != -1) 
			pos = pos + name.length() + 1;
		else 
			return -1;
		
		epos = xml.indexOf("/>", pos); 
		
		return pos;
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

	public final String __getAttribut(String name)
	{
		int apos = xml.indexOf(name + "=\"", pos);
		
		if(apos == -1 || apos > epos)
			return null;
		
		apos = apos + name.length() + 2;
		
		return xml.substring(apos, xml.indexOf("\"", apos));
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
