package gawky.message.generator;


import gawky.message.Formatter;
import gawky.message.part.Desc;
import gawky.message.part.Part;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;


public class Generator 
{
	private static Logger log = Logger.getLogger(Generator.class);
	
	public static String generateString(Part bean)
	{
		if(bean == null)
			return "";
		
		String str = "";
		Desc   descs[] = bean._getDesc();
		Desc   desc;
		
		try 
		{
			for(int i = 0; i < descs.length; i++)
			{
				desc = descs[i];
				
				String val = null;
				
				try {
					val = (String)PropertyUtils.getSimpleProperty(bean, desc.name);
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
