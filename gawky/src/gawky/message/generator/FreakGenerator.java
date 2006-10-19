package gawky.message.generator;


import gawky.message.Formatter;
import gawky.message.part.Desc;
import gawky.message.part.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FreakGenerator extends Generator
{
	private static Log log = LogFactory.getLog(FreakGenerator.class);
	
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
		
				String delimiter = desc.delimiter;
				
				if(delimiter != null && delimiter.length() > 0 && (delimiter.charAt(0) == '#' || delimiter.charAt(0) == '%'))
					delimiter = "";
				
				String val = null;
				
				// Prepared Reflection call
				try {
					val = (String)desc.gmethod.invoke(bean, (Object[]) null );
				} catch(Exception e) {
				}
				
				if(desc.format == Desc.FMT_DIGIT)
				{
					str += Formatter.getStringN(desc.len, val);
					if(delimiter != null)
						str += delimiter;
				}
				else if(desc.format == Desc.FMT_BINARY)
				{	// von rechts mit null f�llen
					str += Formatter.getStringNL(desc.len, val);
					if(delimiter != null)
						str += delimiter;					
				}
				else
				{
					if(delimiter != null)
						str += Formatter.getStringV(desc.len, val, delimiter);
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
