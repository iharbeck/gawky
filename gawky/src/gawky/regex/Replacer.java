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
	
	public String replaceFirst(String val, String rep) 
	{
		return pattern.matcher(val).replaceFirst(rep);
	}

	public String replaceAll(String val, String rep) 
	{
		return pattern.matcher(val).replaceAll(rep);
	}
	
	public static void main(String[] args) {
		Replacer replacer = new Replacer("(\\d*),(\\d{2})");
		
		System.out.println(replacer.replaceFirst("12345,78", "$1$2"));
		System.out.println(replacer.replaceFirst("12345,78", "$1$2"));
		System.out.println(replacer.replaceFirst("12345,78", "$1$2"));
		System.out.println(replacer.replaceFirst("12345,78", "$1$2"));
		System.out.println(replacer.replaceFirst("12345,78", "$1$2"));
	}
}
