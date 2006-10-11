package gawky.message.parser;

import gawky.message.part.Desc;
import gawky.message.part.Part;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author Ingo Harbeck
 *
 */
public class FreakParser extends Parser
{
	private static Logger log = Logger.getLogger(FreakParser.class);
	
	boolean hasDelimiter;
	
	public void parse(String str, Object bean) throws ParserException
	{
		// store for splitting records 
		line = str;
		
		Desc   descs[] = ((Part)bean).getCachedDesc();
		Desc   desc;
		String value = "";
		
		int max = str.length();
		int start = 0;

		int end =0;
		
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];
			
			// DISCUSS example - ID not in Importfile but later in DB
			if(desc.skipparsing) 
				continue;
			
			if(desc.delimiter == null)   // fixed
			{ 
				end = start+desc.len;
				
				if(end > max) // Feld zu kurz wenn nicht option
				{
					position = max;
					
					if(desc.len > 0 && desc.code != Desc.CODE_O)
						throw new ParserException(ParserException.ERROR_FIELD_TO_SHORT, desc, str.substring(start));
					
					// Optionale Felder mit zu kurzem Wert gefüllt !!!
					log.warn("OPTIONAL VALUE AT RECORD END IS TO SHORT");

					value = str.substring(start);
					storeValue(bean, i, desc, value);
					return;
				}
				value = str.substring(start, start+desc.len);
				start += desc.len;
				
				position = start;
			} 
			else // variable 
			{   
				// spezial indexOf (Ende markiert durch Feld eines anderen Typs)
				// auf Zahl folgt ein CHAR
				
				//??? end = str.indexOf(desc.delimiter, start);
				
				end = indexOf(str, desc.delimiter, start);
				
				if(end > max || end == -1) // Feld zu kurz wenn nicht option
				{
					//position = max;

					if(desc.len > 0 && desc.code != Desc.CODE_O)
						throw new ParserException(ParserException.ERROR_FIELD_TO_SHORT, desc, str.substring(start));

					if(end == -1) {
						// enventuell fehlt einfach nur der Delimiter am Zeilenende
						value = str.substring(start);
						storeValue(bean, i, desc, value);
					}
					// am Ende der Zeile angekommen
					return;
				}

				if(desc.len > 0)
					end = Math.min(start+desc.len, end);
				
				value = str.substring(start, end);
				
				start = end; 
				
				// Delimiter überspringen / set by indexOf
				if(hasDelimiter)
					start += desc.delimiter.length();  // Multicharacter delimiter

				position = start;

				if(desc.len > 0 && value.length() > desc.len)
					throw new ParserException(ParserException.ERROR_FIELD_TO_LONG, desc, value);
			}
	
			// Required Field
			if(desc.code == Desc.CODE_R && value.equals(""))
				throw new ParserException(ParserException.ERROR_FIELD_REQUIRED, desc, value);
			// Optional Field
			else if(desc.code == Desc.CODE_O && value.equals(""))
			{
				storeValue(bean, i, desc, value);
				continue;
			}		
			// Inhaltlich prüfung						
		    switch (desc.format) { 
				case Desc.FMT_ASCII :
					if(!fmt_ascii(value))
						throw new ParserException(ParserException.ERROR_TYPE_ASCII, desc, value);
					break;
				case Desc.FMT_DIGIT :
					if(!fmt_digit(value))
						throw new ParserException(ParserException.ERROR_TYPE_DIGIT, desc, value);
					break;
				case Desc.FMT_BLANK :
					if(!fmt_blank(value))
						throw new ParserException(ParserException.ERROR_TYPE_BLANK, desc, value);
					break;
				case Desc.FMT_BLANK_ZERO :
					if(!fmt_blank_zero(value))
						throw new ParserException(ParserException.ERROR_TYPE_BLANK_ZERO, desc, value);
					break;
				case Desc.FMT_BINARY :
					if(!fmt_binary(value))
						throw new ParserException(ParserException.ERROR_TYPE_BINARY, desc, value);
					break;
				case Desc.FMT_UPPER :
					if(!fmt_upper(value))
						throw new ParserException(ParserException.ERROR_TYPE_UPPER, desc, value);
					break;
				case Desc.FMT_LOWER :
					if(!fmt_lower(value))
						throw new ParserException(ParserException.ERROR_TYPE_LOWER, desc, value);
					break;
				case Desc.FMT_BLANK_LETTER :
					if(!fmt_blank_letter(value))
						throw new ParserException(ParserException.ERROR_TYPE_BLANK_LETTER, desc, value);
					break;
				case Desc.FMT_DATE :
					if(!fmt_DATE(value))
						throw new ParserException(ParserException.ERROR_TYPE_DATE, desc, value);
					break;
				case Desc.FMT_TIME :
					if(!fmt_TIME(value))
						throw new ParserException(ParserException.ERROR_TYPE_TIME, desc, value);
					break;
			}
			
		    storeValue(bean, i, desc, value);
		}		
		
		return;
	}
	
	public final int indexOf(String str, String end, int start) 
	{
		if(end.charAt(0) == '#') {
			hasDelimiter = false;
			return indexOfField(str, end.substring(1), start);
		}	
		else if(end.charAt(0) == '%') {
			hasDelimiter = false;
			return indexOfPattern(str, end.substring(1), start);
		}
		else{
			hasDelimiter = true;
			return str.indexOf(end, start);
		}
	}	
	
	public final int indexOfField(String str, String delimiter, int start) 
	{
		int max = str.length();
		for(int i=start; i < max; i++)
			if( Character.toString(str.charAt(i)).matches(delimiter))
				return i;
		
		return -1;
	}
	
	public final int indexOfPattern(String str, String patternstr, int start) 
	{
	   Pattern pattern = Pattern.compile(patternstr);
	       
	   Matcher matcher = pattern.matcher(str.substring(start));
	   if (matcher.find()) {  
		   //System.out.println( matcher.group(0) );
		   return matcher.end() + start;
	   }
	   return -1;
	}

}
