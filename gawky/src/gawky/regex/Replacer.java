package gawky.regex;

import java.util.regex.Pattern;

public class Replacer {

	Pattern pattern;

	public Replacer(String regex) {
		this(regex, Pattern.CASE_INSENSITIVE);
	}
	
	public Replacer(String regex, int flags) {
		pattern = Pattern.compile( regex, Pattern.CASE_INSENSITIVE);
	}
	
	public void replaceFirst(String val) 
	{
		pattern.matcher(val).replaceFirst(val);
	}

	public void replaceAll(String val) 
	{
		pattern.matcher(val).replaceAll(val);
	}
}
