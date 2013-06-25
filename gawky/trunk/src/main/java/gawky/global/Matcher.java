package gawky.global;

import java.util.regex.Pattern;

public class Matcher 
{
    Pattern pattern;
   
    public Matcher(String regex) { 
    	pattern = Pattern.compile(regex);   
    }
  
    public final String process(String text, String replace) {
    	return pattern.matcher(text).replaceAll(replace);
    }
    
    public final boolean matches(String text) {
    	return pattern.matcher(text).matches();
    }
}
