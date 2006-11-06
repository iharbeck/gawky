package gawky.message.part;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Packed Decimal Part
 * 
 * @author Ingo Harbeck
 *
 */
public class DescL extends Desc 
{
	Pattern pattern;
    
	public DescL(String name, String[] values)
	{	
		super(Desc.FMT_A, Desc.CODE_L, 0, name);
	
		String patternStr = "";
		for(int i=0; i < values.length-1; i++){
			patternStr += "(" + values[i] + ")|";
		}
		patternStr += "(" + values[values.length-1] + ")";

		pattern = Pattern.compile(patternStr);
	}
	
	public String lookup(String inputstr) 
	{
		Matcher matcher = pattern.matcher(inputstr);
		   
	    if(!matcher.find())
	    	return null;

	    //System.out.println( matcher.group(0) );
		//System.out.println( matcher.end() );
		   
		return matcher.group(0);
	}
}
