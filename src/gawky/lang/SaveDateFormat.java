package gawky.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class SaveDateFormat
{
	private final String format;

	private static final ThreadLocal<Map<String, SimpleDateFormat>> mapformat = new ThreadLocal<Map<String, SimpleDateFormat>>()
	{
		public Map<String, SimpleDateFormat> initialValue()
		{
			return new HashMap<String, SimpleDateFormat>();
		}
	};

	private SimpleDateFormat getDateFormat(String format)
	{
		Map<String, SimpleDateFormat> parsers = mapformat.get();
		SimpleDateFormat formatter = parsers.get(format);

		if(formatter == null)
		{
			formatter = new SimpleDateFormat(format);
			parsers.put(format, formatter);
		}

		return formatter;
	}

	public SaveDateFormat(String format)
	{
		this.format = format;
	}

	public String format(Date date)
	{
		return getDateFormat(format).format(date);
	}

	public String format(Object date)
	{
		return getDateFormat(format).format(date);
	}

	public Date parse(String day) throws ParseException
	{
		return getDateFormat(format).parse(day);
	}
	
	public String toPattern()
	{
		return getDateFormat(format).toPattern();
	}
	
	public void setLenient(boolean lenient)
	{
		getDateFormat(format).setLenient(lenient);
	}
	
	public void setTimeZone(TimeZone zone)
	{
		getDateFormat(format).setTimeZone(zone);
	}
}
