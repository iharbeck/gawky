package example;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

public class RegOro {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	   String inputstr = "/PY/123456/OB/Citibank ltd/OB3/Mr Smith/EI/12345678";
		
	   String patternStr = "/(.*?)/(.*?)[/$]";

	   
	   int groups;
	   PatternMatcher matcher;
	   PatternCompiler compiler;
	   Pattern pattern;
	   PatternMatcherInput input;
	   MatchResult result;

	   compiler = new Perl5Compiler();
	   matcher  = new Perl5Matcher();

	   try {
	     pattern = compiler.compile(patternStr);
	   } catch(MalformedPatternException e) {
	     System.out.println("Bad pattern.");
	     System.out.println(e.getMessage());
	     return;
	   }

	   input   = new PatternMatcherInput(inputstr);

	   while(matcher.contains(input, pattern)) {
	     result = matcher.getMatch();  
	     // Perform whatever processing on the result you want.
	     // Here we just print out all its elements to show how its
	     // methods are used.
	   
	     System.out.println("Match: " + result.toString());
	     System.out.println("Length: " + result.length());
	     groups = result.groups();
	     System.out.println("Groups: " + groups);
	     System.out.println("Begin offset: " + result.beginOffset(0));
	     System.out.println("End offset: " + result.endOffset(0));
	     System.out.println("Saved Groups: ");

	     // Start at 1 because we just printed out group 0
	     for(int group = 1; group < groups; group++) {
	  	 System.out.println(group + ": " + result.group(group));
	  	 System.out.println("Begin: " + result.begin(group));
	  	 System.out.println("End: " + result.end(group));
	     }
	   }

	 

	}

}
