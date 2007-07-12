package gawky.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grouper {

	Pattern pattern;

	public Grouper(String regex) {
		this(regex, Pattern.CASE_INSENSITIVE);
	}
	
	public Grouper(String regex, int flags) {
		pattern = Pattern.compile( regex, Pattern.CASE_INSENSITIVE);
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
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Grouper grouper = new Grouper("FREMD(.....)EIGEN(.....)EU");
		
		String[] values = grouper.match("FREMD00012EIGEN00000EU");
		
		System.out.println(values[1]); // -> 00012
		System.out.println(values[2]); // -> 00000
	}

}
