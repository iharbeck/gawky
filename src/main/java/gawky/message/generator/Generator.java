package gawky.message.generator;

import gawky.message.Formatter;
import gawky.message.part.Desc;
import gawky.message.part.DescV;
import gawky.message.part.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Generator
{
	static Generator instance;

	public static Generator getInstance()
	{
		if(instance == null)
			instance = new Generator();
		return instance;
	}

	private static Log log = LogFactory.getLog(Generator.class);

	public String buildString(Part bean) throws Exception
	{
		if(bean == null)
			return "";

		//bean.beforeStore();

		StringBuilder str = new StringBuilder(1000);
		Desc descs[] = bean.getCachedDesc();
		Desc desc;

		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];

			if(desc.nostring)
				continue;

			Object val = null;

			try
			{
				val = desc.getValue(bean);
			}
			catch(Exception e)
			{
			}

			if(desc.format == Desc.FMT_DIGIT)
			{
				str.append(Formatter.getStringN(desc.len, (String)val));
				if(desc.delimiter != null)
					str.append(desc.delimiter);
			}
			else if(desc.format == Desc.FMT_BINARY)
			{ // von links mit null füllen
				str.append(new String(Formatter.bpad(desc.len, (byte[])val)));
				if(desc.delimiter != null)
					str.append(desc.delimiter);
			}
			else
			{
				if(desc.delimiter != null)
					str.append(Formatter.getStringV(desc.len, (String)val, desc.delimiter));
				else
				{
					if(desc.format != Desc.FMT_CONSTANT)
					{
						if(desc.len != 0 && !(desc instanceof DescV))
							str.append(Formatter.getStringC(desc.len, (String)val));
						else
							str.append(val);
					}
					else
					{
						str.append(Formatter.getStringC(desc.len, desc.name));
					}
				}
			}
		}

		return str.toString();
	}

	public String buildDebugString(Part bean)
	{
		if(bean == null)
			return "";

		//bean.beforeStore();

		StringBuilder str = new StringBuilder(1000);
		Desc descs[] = bean.getCachedDesc();
		Desc desc;

		try
		{
			for(int i = 0; i < descs.length; i++)
			{
				desc = descs[i];

				Object val = null;

				try
				{
					val = (String)desc.getValue(bean);
				}
				catch(Exception e)
				{
				}

				str.append(desc.name).append(" <");
				if(desc.format == Desc.FMT_DIGIT)
				{
					str.append(Formatter.getStringN(desc.len, (String)val));
					if(desc.delimiter != null)
						str.append(desc.delimiter);
				}
				else if(desc.format == Desc.FMT_BINARY)
				{ // von rechts mit null füllen
					str.append(new String(Formatter.bpad(desc.len, (byte[])val)));
					if(desc.delimiter != null)
						str.append(desc.delimiter);
				}
				else
				{
					if(desc.delimiter != null)
						str.append(Formatter.getStringV(desc.len, (String)val, desc.delimiter));
					else
					{
						if(desc.format != Desc.FMT_CONSTANT)
						{
							if(desc.len != 0)
								str.append(Formatter.getStringC(desc.len, (String)val));
							else
								str.append(val);
						}
						else
						{
							str.append(Formatter.getStringC(desc.len, desc.name));
						}
					}
				}
				str.append(">\n");
			}
		}
		catch(Exception e)
		{
			log.error("getProperty in Parser", e);
		}

		return str.toString();
	}
}
