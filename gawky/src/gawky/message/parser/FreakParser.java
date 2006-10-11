package gawky.message.parser;

import gawky.message.part.Desc;
import gawky.message.part.Part;

import org.apache.log4j.Logger;

/**
 * @author Ingo Harbeck
 *
 */
public class FreakParser extends Parser
{
	private static Logger log = Logger.getLogger(FreakParser.class);
	
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
				
				end = indexOF(str, desc.delimiter, start);
				
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

				// Sonderfall Freak Parser 
				// dann wird auch kein delimiter vorhanden sein und darf nicht übersprungen werden
				end = Math.min(start+desc.len, end);
				
				value = str.substring(start, end);
				
				// Delimiter überspringen 
				start = end; // + desc.delimiter.length();  // Multicharacter delimiter

				position = start;

				if(desc.len > 0 && value.length() > desc.len)
					throw new ParserException(ParserException.ERROR_FIELD_TO_LONG, desc, value);
			}
	
			//value = value.trim();		
			
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
	
	public static void main(String[] args) throws Exception {

		
		FreakParser parser = new FreakParser();
		
		FreakParserBean bean = new FreakParserBean();
		
	    parser.parse("1.3AB456CD", bean);
	    System.out.println(bean);
	    
	    parser.parse("1235555A456CD", bean);
	    System.out.println(bean);
	}
	
	public final int indexOF(String str, String end, int start) 
	{
		if(end.charAt(0) == '#')
			return indexOfField(str, end.substring(1), start);
		else
			return str.indexOf(end, start);
	}	
	
	public final int indexOfField(String str, String end, int start) 
	{
		int max = str.length();
		
		for(int i=start; i < max; i++)
			if( Character.toString(str.charAt(i)).matches(end))
				return i;
		
		return -1;
	}

}
