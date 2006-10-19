package gawky.message.generator;


import gawky.message.Formatter;
import gawky.message.part.Desc;
import gawky.message.part.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Generator 
{
	private static Log log = LogFactory.getLog(Generator.class);

	public String generateString(Part bean)
	{
		if(bean == null)
			return "";
		
		String str = "";
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;
		
		try 
		{
			for(int i = 0; i < descs.length; i++)
			{
				desc = descs[i];
				
				String val = null;
				
				// Prepared Reflection call
				try {
					val = desc.getValue(bean);
				} catch(Exception e) {
				}
				
				if(desc.format == Desc.FMT_DIGIT)
				{
					str += Formatter.getStringN(desc.len, val);
					if(desc.delimiter != null)
						str += desc.delimiter;
				}
				else if(desc.format == Desc.FMT_BINARY)
				{	// von rechts mit null füllen
					str += Formatter.getStringNL(desc.len, val);
					if(desc.delimiter != null)
						str += desc.delimiter;					
				}
				else
				{
					if(desc.delimiter != null)
						str += Formatter.getStringV(desc.len, val, desc.delimiter);
					else
					{
						if(desc.format != Desc.FMT_CONSTANT)
						{
							if(desc.len != 0)
								str += Formatter.getStringC(desc.len, val);
							else
								str += val;
						}
						else
						{
							str += Formatter.getStringC(desc.len, desc.name);
						}
					}
				} 
			}
		} catch(Exception e) {
			log.error("getProperty in Parser" ,e);
		}
		
		return str;
	}
}
