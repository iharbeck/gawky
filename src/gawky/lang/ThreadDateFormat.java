package gawky.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThreadDateFormat
{
	private final String format;
	private static final ThreadLocal<Map<String, SimpleDateFormat>> map = new ThreadLocal<Map<String, SimpleDateFormat>>()
	{
		public Map<String, SimpleDateFormat> initialValue()
		{
			return new HashMap<String, SimpleDateFormat>();
		}
	};

	private SimpleDateFormat getDateFormat(String format)
	{
		Map<String, SimpleDateFormat> formatters = map.get();
		SimpleDateFormat formatter = formatters.get(format);
		
		if(formatter == null)
		{
			formatter = new SimpleDateFormat(format);
			formatters.put(format, formatter);
		}
		
		return formatter;
	}

	public ThreadDateFormat(String format)
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
}
