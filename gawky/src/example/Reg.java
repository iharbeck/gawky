package example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	   String input = "/PY/123456/OB/Citibank// ltd/OB3/Mr Smith/EI/12345678/";
		
	   String patternStr = "/(.*?)/(.*?)[/$]";

	   Pattern pattern = Pattern.compile(patternStr);
       
	   Matcher matcher = pattern.matcher(input);
	   while (matcher.find()) {
           System.out.println( matcher.group(0) );
           System.out.println( matcher.group(1) );
           System.out.println( matcher.group(2) + "\n" );
           
           input = input.substring(matcher.end()-1 );
           matcher = pattern.matcher( input );
       }

	}

}
