package gawky.regex;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grouper {

	Pattern pattern;

	public Grouper(String regex) {
		this(regex, Pattern.CASE_INSENSITIVE);
	}
	
	public Grouper(String regex, int flags) {
		pattern = Pattern.compile( regex, flags);
	}
	
	public String[] match(String val) 
	{
		if(val == null)
			return null;
		
		Matcher matcher = pattern.matcher(val);
		
		if(!matcher.find())
			return null;

		int c = matcher.groupCount()+1;
		
		String[] groups = new String[c];
		
		for (int i = 0; i < c; i++) {
			groups[i] = matcher.group(i);
		}
		
		return groups;
	}

	public String[] matchall(String val) 
	{
		if(val == null)
			return null;
		
		Matcher matcher = pattern.matcher(val);
		
		ArrayList<String> list = new ArrayList<String>();
		while(matcher.find())
		{
			int c = matcher.groupCount()+1;
			
			
			for (int i = 0; i < c; i++) {
				list.add(matcher.group(i));
			}
		}
		
		String[] groups  = (String[])list.toArray(new String[list.size()]);
		
		return groups;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		Grouper grouper = new Grouper("FREMD(.....)EIGEN(.....)EU");
		
		String[] values = grouper.match("xxx\\nFREMD00012EIGEN00000EUcc\n\nsss<");
		
		System.out.println(values[1]); // -> 00012
		System.out.println(values[2]); // -> 00000

		Grouper grouper2 = new Grouper("<import file=\"(.*?)\".?/>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		String org = new String(readbytearray("D:/work/obex/etc/config.xml"));

		//String org = "[<import file=\"kaspersky\"/>]\nsahdkj ksahkdhka k k [<import file=\"plus\"/>] kjk ljlkjllk lj[<import file=\"edeka\"/>]";
		
		String[] values2 = grouper2.matchall(org);
		
		for(int i=0; i < values2.length; i = i + 2)
		{
			String include = "";
			try {
				include = new String(readbytearray("D:/work/obex/etc/config_" + values2[i+1] + ".xml"));
			} catch (Exception e) {
			}
			org = org.replace(values2[i], include);
			System.out.println(values2[i] + "  -- " + values2[i+1]); // -> 00012
		}
		
		System.out.println(org);
		
		
	}

	public static byte[] readbytearray(String filename) throws Exception
	{
		File f = new File( filename ); 
    	byte[] buffer = new byte[ (int) f.length() ]; 
    	InputStream in = new FileInputStream( f ); 
    	in.read( buffer ); 
    	in.close();
    	
    	return buffer;
	}
}
