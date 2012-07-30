package gawky.message.parser;

import gawky.global.Matcher;
import gawky.message.part.Desc;
import gawky.message.part.Part;

import java.text.SimpleDateFormat;

/**
 * @author Ingo Harbeck
 * 
 */
public class Parser
{
	//private static Log log = LogFactory.getLog(Parser.class);

	static Parser instance;

	public static Parser getInstance()
	{
		if(instance == null)
			instance = new Parser();
		return instance;
	}

	private static boolean docheck = true;
	private static boolean dotrim = false;
	private static boolean doclone = false;

	int position = 0;

	SimpleDateFormat df_YYYYMMDD;
	SimpleDateFormat df_HHMMSS;

	Desc descs[] = null;

	String line;
	Desc desc;

	public String getNext()
	{
		return line.substring(position);
	}

	public Object parsebytes(byte[] str, Object bean) throws ParserException
	{
		throw new ParserException(1, "ONLY String support");
	}

	public Object parse(String str, Part bean) throws ParserException
	{
		try
		{
			// store for splitting records
			line = str;

			// Get Description Object
			descs = bean.getCachedDesc();

			String value = "";

			int max = str.length();
			int start = 0;

			int end = 0;

			for(int i = 0; i < descs.length; i++)
			{
				desc = descs[i];

				// DISCUSS example - ID not in Importfile but later in DB
				if(desc.skipparsing)
					continue;

				if(desc.delimiter == null) // fixed length
				{
					end = start + desc.len;

					if(end > max) // Feld zu kurz (wenn nicht option)
					{
						position = max;

						// value = str.substring(start);  // bis zum Zeilenende

						value = substringer(str, start);

						i = descs.length - 1; // ende einleiten
					}
					else
					{
						//value = str.substring(start, start+desc.len);

						value = substringer(str, start, start + desc.len);

						start += desc.len;

						position = start;
					}
				}
				else
				// variable
				{
					end = str.indexOf(desc.delimiter, start); // Trennzeichen finden

					if(end == -1) // eventuell kein Delimiter am Ende; Feld zu kurz (wenn nicht option)
					{
						// eventuell fehlt einfach nur der Delimiter am Zeilenende
						//value = str.substring(start);

						value = substringer(str, start);

						i = descs.length - 1; // ende einleiten

						// am Ende der Zeile angekommen und weiteres nicht optionales feld
						//if(i+1 < descs.length  && desc.code != Desc.CODE_O)
						if(desc.code != Desc.CODE_O)
							throw new ParserException(ParserException.ERROR_LINE_TO_SHORT, desc, "");
					}
					else
					{
						//value = str.substring(start, end);

						value = substringer(str, start, end);

						start = end + desc.delimiter.length(); // Multicharacter delimiter

						position = start;

					}

					if(desc.len > 0 && value.length() > desc.len)
						throw new ParserException(ParserException.ERROR_FIELD_TO_LONG, desc, value);
				
				}

				if(desc.code == Desc.CODE_R) // Required Field
				{
					if(value.length() == 0)
						throw new ParserException(ParserException.ERROR_FIELD_REQUIRED, desc, value);
				}

				//else  // Inhaltlich prüfung
				{
					if(Parser.docheck)
						typeCheck(desc, value);
				}

				if(!desc.nostore) // wenn store dann speichern
					storeValue(bean, i, desc, value);
			}

			return bean;
		}
		finally
		{
			bean.afterFill();

			if(doclone)
				bean.doclone();
		}
	}

	public final String substringer(String value, int start)
	{
		return substringer(value, start, value.length());
	}

	public final String substringer(String value, int start, int end)
	{
		if(Parser.dotrim)
		{
			if(desc.format == Desc.FMT_DIGIT)
				return lntrim(value, start, end);
			else
				return rtrim(value, start, end);
		}

		return value.substring(start, end);
	}

	public static void main(String[] args)
	{
		System.out.println(lntrim("123456789", 0, 3));
		System.out.println(lntrim("123006789", 3, 6));
		System.out.println(lntrim("123000789", 3));

		System.out.println("[" + rtrim("123456789", 0, 3) + "]");
		System.out.println("[" + rtrim("12306 789", 3, 6) + "]");
		System.out.println("[" + rtrim("1230 \t 789", 3, 6) + "]");
		System.out.println("[" + rtrim("123   789", 3, 6) + "]");
	}

	public final static String lntrim(String value, int start)
	{
		return lntrim(value, start, value.length());
	}

	public final static String lntrim(String value, int start, int end)
	{
		while((start < end))
		{
			char ch = value.charAt(start);

			if(ch == '0' || ch == ' ')
				start++;
			else
				break;
		}

		if(start == end)
			return "0";

		return value.substring(start, end);
	}

	public final static String rtrim(String value, int start)
	{
		return rtrim(value, start, value.length());
	}

	public final static String rtrim(String value, int start, int end)
	{
		while(end > start && value.charAt(end - 1) <= ' ')
		{
			end--;
		}

		if(start == end)
			return "";

		return value.substring(start, end);
	}

	public final void typeCheck(Desc desc, String value) throws ParserException
	{
		if(value.length() == 0)
			return;

		// Inhaltlich prüfung
		switch(desc.format)
		{
			case Desc.FMT_ASCII:
				if(!fmt_ascii(value))
					throw new ParserException(ParserException.ERROR_TYPE_ASCII, desc, value);
				break;
			case Desc.FMT_DIGIT:
				if(!fmt_digit(value))
					throw new ParserException(ParserException.ERROR_TYPE_DIGIT, desc, value);
				break;
			case Desc.FMT_BLANK:
				if(!fmt_blank(value))
					throw new ParserException(ParserException.ERROR_TYPE_BLANK, desc, value);
				break;
			case Desc.FMT_BLANK_ZERO:
				if(!fmt_blank_zero(value))
					throw new ParserException(ParserException.ERROR_TYPE_BLANK_ZERO, desc, value);
				break;
			case Desc.FMT_BINARY:
				if(!fmt_binary(value))
					throw new ParserException(ParserException.ERROR_TYPE_BINARY, desc, value);
				break;
			case Desc.FMT_UPPER:
				if(!fmt_upper(value))
					throw new ParserException(ParserException.ERROR_TYPE_UPPER, desc, value);
				break;
			case Desc.FMT_LOWER:
				if(!fmt_lower(value))
					throw new ParserException(ParserException.ERROR_TYPE_LOWER, desc, value);
				break;
			case Desc.FMT_BLANK_LETTER:
				if(!fmt_blank_letter(value))
					throw new ParserException(ParserException.ERROR_TYPE_BLANK_LETTER, desc, value);
				break;
			case Desc.FMT_DATE:
				if(!fmt_DATE(value))
					throw new ParserException(ParserException.ERROR_TYPE_DATE, desc, value);
				break;
			case Desc.FMT_TIME:
				if(!fmt_TIME(value))
					throw new ParserException(ParserException.ERROR_TYPE_TIME, desc, value);
				break;
		}

		if(desc.pattern != null)
		{
			if(!value.matches(desc.pattern))
				throw new ParserException(ParserException.ERROR_TYPE_PATTERN, desc, value);
		}
	}

	//boolean info = log.isInfoEnabled();

	final void storeValue(Object bean, int pos, Desc desc, String value) throws ParserException
	{
		//		if(info)
		//			log.info("value " + pos + " : " + desc.name + " <" + value + ">");

		if(desc.nostore)
			return;

		try
		{
			desc.setValue(bean, value);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ParserException(ParserException.ERROR_MISSING_SETTER, desc, value + ":" + bean.toString());
		}
	}

	final boolean fmt_ascii(String value)
	{
		return true; //value.matches("[\\w\\söäüÖÄÜß]*");
	}

	static Matcher matcher_digit = new Matcher("[\\d,\\.+-]*");

	final boolean fmt_digit(String value)
	{
		return matcher_digit.matches(value);
	}

	final boolean fmt_blank(String value)
	{
		return value.length() == 0;
	}

	static Matcher matcher_blank_zero = new Matcher("[\\s0]*");

	final boolean fmt_blank_zero(String value)
	{
		return matcher_blank_zero.matches(value);
	}

	static Matcher matcher_binary = new Matcher("[01]*");

	final boolean fmt_binary(String value)
	{
		return matcher_binary.matches(value);
	}

	static Matcher matcher_upper = new Matcher("[A-Z]*");

	final boolean fmt_upper(String value)
	{
		return matcher_upper.matches(value);
	}

	static Matcher matcher_lower = new Matcher("[a-z]*");

	final boolean fmt_lower(String value)
	{
		return matcher_lower.matches(value);
	}

	static Matcher matcher_blank_letter = new Matcher("[\\p{Lower}\\p{Upper}\\s]*");

	final boolean fmt_blank_letter(String value)
	{
		return matcher_blank_letter.matches(value);
	}

	final boolean fmt_DATE(String value)
	{
		if(df_YYYYMMDD == null)
			df_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

		try
		{
			df_YYYYMMDD.parse(value);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	final boolean fmt_TIME(String value)
	{
		if(df_HHMMSS == null)
			df_HHMMSS = new SimpleDateFormat("HHmmss");

		try
		{
			df_HHMMSS.parse(value);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public final void setDateFormat(String format)
	{
		df_YYYYMMDD = new SimpleDateFormat(format);
	}

	public final void setTimeFormat(String format)
	{
		df_HHMMSS = new SimpleDateFormat(format);
	}

	public static void setDotrim(boolean dotrim)
	{
		Parser.dotrim = dotrim;
	}

	public static void setDocheck(boolean docheck)
	{
		Parser.docheck = docheck;
	}

	public static void setDoclone(boolean doclone)
	{
		Parser.doclone = doclone;
	}
}
