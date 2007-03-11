package gawky.message.parser;

import gawky.host.Ebcdic;
import gawky.host.PackedDecimal;
import gawky.message.part.Desc;
import gawky.message.part.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Ingo Harbeck
 *
 */
public class EBCDICParser extends Parser
{
	private static Log log = LogFactory.getLog(EBCDICParser.class);

	public void parse(String str, Object bean) throws ParserException
	{
		parse(str.getBytes(), bean);
	}

	public void parse(byte[] str, Object bean) throws ParserException
	{
		try 
		{
			// store for splitting records 
			//line = str;
			
			// Get Description Object 
			descs = ((Part)bean).getCachedDesc();
	
			String value = "";
			
//			int max   = str.length;
			int start = 0;
	
//			int end = 0;
			
			for(int i = 0; i < descs.length; i++)
			{
				desc = descs[i];
		
				byte[] part = new byte[desc.len];
				
				// DISCUSS example - ID not in Importfile but later in DB
				if(desc.skipparsing) 
					continue;
	
				if(desc.delimiter == null)   // fixed
				{ 
//					end = start+desc.len;
/*					
					if(end > max) // Feld zu kurz wenn nicht option
					{
						position = max;
						
						if(desc.len > 0 && desc.code != Desc.CODE_O)
							throw new ParserException(ParserException.ERROR_FIELD_TO_SHORT, desc, "..");
						
						// Optionale Felder mit zu kurzem Wert gefüllt !!!
						log.warn("OPTIONAL VALUE AT RECORD END IS TO SHORT");
	
						System.arraycopy(line, start, part, 0, desc.len);
						
						//value = str.substring(start);
						storeValue(bean, i, desc, value);
						return;
					}
*/
					try {
						
						System.arraycopy(str, start, part, 0, desc.len);

						if(desc.isPacked())	
						{	
							if(!desc.isUnsigned())
							//	value = ((DescP)desc).packeddecimal.unpack(part);
								value = "" + PackedDecimal.readNumberPacked(part, desc.len, true);
							else
								value = "" + PackedDecimal.readNumberPacked(part, desc.len, false);
						} 
						else 
						{
							value = Ebcdic.toUnicode(part);
						}
					} catch (Exception e) {
						System.out.println(e);
					}
					
					start += desc.len;
					
					position = start;
				} 

/*
				// Required Field
				if(desc.code == Desc.CODE_R && value.length() == 0)
					throw new ParserException(ParserException.ERROR_FIELD_REQUIRED, desc, value);
				// Optional Field
				else if(desc.code == Desc.CODE_O && value.length() == 0)
				{
					storeValue(bean, i, desc, value);		
					continue;
				}
*/	
				// Inhaltlich prüfung	
				// typeCheck(desc, value);
	
			    storeValue(bean, i, desc, value);
			}		
			
			return;
		} finally {
			((Part)bean).afterFill();
		}
	}

}
