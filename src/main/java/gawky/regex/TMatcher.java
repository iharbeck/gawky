package gawky.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class TMatcher 
{
	final static String varpattern = "\\$\\{(.*?)\\}";
	
	Pattern pattern;

	public TMatcher() {
		pattern = Pattern.compile(varpattern, Pattern.MULTILINE);
	}
	
	public String matchall(String val, TMatchhandler handler) 
	{
		if(val == null)
			return null;
		
		Matcher matcher = pattern.matcher(val);
		
		StringBuilder target = new StringBuilder(4000);
		
		int pos = 0;
		while(matcher.find())
		{
			target.append(val.substring(pos, matcher.start()));
			target.append(handler.handle(matcher.group(1)));
			
			pos = matcher.end();
		}
		
		target.append(val.substring(pos));
		
		
		return target.toString();
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		TMatcher grouper = new TMatcher();
		
		String text = "abc${hei}di abc${hei}di abc${hei}di abc${hei}di abc${hei}di abc${hei}di";
		
		String out = grouper.matchall(text, new TMatchhandler() { public String handle(String key) {
			return key.toUpperCase();
		} });
		
		System.out.println(out);
	}

}