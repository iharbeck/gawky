package gawky.message.parser;

import gawky.global.Constant;
import gawky.host.PackedDecimal;
import gawky.message.part.Desc;
import gawky.message.part.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Ingo Harbeck
 *
 */
public class ByteParser extends Parser
{
	private static Log log = LogFactory.getLog(ByteParser.class);

	String encoding = Constant.ENCODE_ISO;
	
	public ByteParser()
	{
	}
	
	public Object parse(String str, Object bean) throws ParserException
	{
		throw new ParserException(1, "ONLY byte[] support");
	}

	public Object parsebytes(byte[] str, Object bean) throws ParserException
	{
		try
		{
			// store for splitting records
			//line = str;

			// Get Description Object
			descs = ((Part)bean).getCachedDesc();

			Object value = "";

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

						// Optionale Felder mit zu kurzem Wert gef�llt !!!
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
						else if(desc.format == Desc.FMT_BINARY)
						{
							value = part;
						}
						else
						{
							value = new String(part, encoding);
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
				// Inhaltlich pr�fung
				// typeCheck(desc, value);

			    storeValue(bean, i, desc, value);
			}

			return bean;
		} finally {
			((Part)bean).afterFill();
		}
	}

}
