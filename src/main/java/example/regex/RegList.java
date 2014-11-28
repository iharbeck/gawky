package example.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegList
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		String inputstr = "RD";

		String patternStr = "(D)|(C)|(RD)|(RC)";

		Pattern pattern = Pattern.compile(patternStr);

		Matcher matcher = pattern.matcher(inputstr);

		if(matcher.find())
		{
			System.out.println(matcher.group(0));
			System.out.println(matcher.end());
		}
	}

}
