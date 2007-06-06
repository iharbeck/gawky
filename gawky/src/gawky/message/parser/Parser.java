package gawky.message.parser;

import gawky.message.Formatter;
import gawky.message.part.Desc;
import gawky.message.part.Part;

import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Ingo Harbeck
 *
 */
public class Parser 
{
	private static Log log = LogFactory.getLog(Parser.class);

	static Parser instance;
	
	public static Parser getInstance() {
		if(instance == null)
			instance = new Parser();
		return instance;
	}
	
	private static boolean docheck  = true;
	private static boolean dotrim   = false;
	private static boolean doclone  = false;
	
	int    position = 0;
	
	SimpleDateFormat df_YYYYMMDD;
	SimpleDateFormat df_HHMMSS;
	
	Desc descs[] = null;
	
	String line;
	Desc   desc;
	
	public String getNext() 
	{
		return line.substring(position);
	}
	
	public void parsebytes(byte[] str, Object bean) throws ParserException
	{
		throw new ParserException(1, "ONLY String support");
	}
	
	public void parse(String str, Object bean) throws ParserException
	{
		try 
		{
			// store for splitting records 
			line = str;
			
			// Get Description Object 
			descs = ((Part)bean).getCachedDesc();
	
			String value = "";
			
			int max   = str.length();
			int start = 0;
	
			int end = 0;
			
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
						//log.warn("OPTIONAL VALUE AT RECORD END IS TO SHORT");
	
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
					end = str.indexOf(desc.delimiter, start);
					
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
						// am Ende der Zeile angekommen und weiteres nicht optionales feld
						if(i+1 < descs.length  && descs[i].code != Desc.CODE_O)
							throw new ParserException(ParserException.ERROR_LINE_TO_SHORT, desc, "");
						
						return;
					}
	
					value = str.substring(start, end);
					
					start = end + desc.delimiter.length();  // Multicharacter delimiter
	
					position = start;
	
					if(desc.len > 0 && value.length() > desc.len)
						throw new ParserException(ParserException.ERROR_FIELD_TO_LONG, desc, value);
				}
				
				// Required Field
				if(desc.code == Desc.CODE_R && value.length() == 0)
					throw new ParserException(ParserException.ERROR_FIELD_REQUIRED, desc, value);
				// Optional Field
				else if(desc.code == Desc.CODE_O && value.length() == 0)
				{
					storeValue(bean, i, desc, value);		
					continue;
				}
	
				// Inhaltlich prüfung	
				if(desc.code != Desc.CODE_O && Parser.docheck)
					typeCheck(desc, value);
	
			    storeValue(bean, i, desc, value);
			}		
			
			return;
		} finally {
			((Part)bean).afterFill();
			if(doclone)
				((Part)bean).doclone();
		}
	}
	
	public final void typeCheck(Desc desc, String value) throws ParserException 
	{
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
		
	}
	
	boolean info = log.isInfoEnabled();
	
	final void storeValue(Object bean, int pos, Desc desc, String value) throws ParserException 
	{
		if(info)
			log.info("value " + pos + " : " + desc.name + " <" + value + ">");

		if(desc.nostore)
			return;

		if(Parser.dotrim)
			value = Formatter.rtrim(value);
		
//		switch(pos) {
//		case 0:
//			((Address)bean).setVorname(value);
//		break;
//		case 1:
//			((Address)bean).setNachname(value);
//			break;
//		case 2:
//			((Address)bean).setInfo1(value);
//			break;
//		case 3:
//			((Address)bean).setInfo2(value);
//			break;
//		}
		
		try {
			desc.setValue(bean, value);
		} catch (Exception e) {	 
			throw new ParserException(ParserException.ERROR_MISSING_SETTER, desc, value + ":" + bean.toString());
		}
	}
	  
	final boolean fmt_ascii(String value) {
		return true; //value.matches("[\\w\\söäüÖÄÜß]*"); 
	}
	
	final boolean fmt_digit(String value) {
		return value.matches("[\\d,\\.+-]*"); 
	}
	
	final boolean fmt_blank(String value) {
		return value.equals(""); 
	} 
	
	final boolean fmt_blank_zero(String value) {
		return value.matches("[\\s0]*"); 
	}

	final boolean fmt_binary(String value) {
		return value.matches("[01]*"); 
	}

	final boolean fmt_upper(String value) {
		return value.matches("[A-Z]*"); 
	}

	final boolean fmt_lower(String value) {
		return value.matches("[a-z]*"); 
	}

	final boolean fmt_blank_letter(String value)	{
		return value.matches("[\\p{Lower}\\p{Upper}\\s]*"); 
	}

	final boolean fmt_DATE(String value)
	{
		if(df_YYYYMMDD == null)
			df_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
			
		try	{
			df_YYYYMMDD.parse(value);
			return true; 
		} catch (Exception e) {
			return false;
		}
	}

	final boolean fmt_TIME(String value)
	{
		if(df_HHMMSS == null)
			df_HHMMSS = new SimpleDateFormat("HHmmss");
			
		try {
			df_HHMMSS.parse(value);
			return true; 
		} catch (Exception e) {
			return false;
		} 
	}
	
	public final void setDateFormat(String format) {
		df_YYYYMMDD = new SimpleDateFormat(format);
	}
	
	public final void setTimeFormat(String format) {
		df_HHMMSS   = new SimpleDateFormat(format); 
	}
	
	public static void setDotrim(boolean dotrim) {
		Parser.dotrim = dotrim;
	}
	
	public static void setDocheck(boolean docheck) {
		Parser.docheck = docheck;
	}
	
	public static void setDoclone(boolean doclone) {
		Parser.doclone = doclone;
	}
}
