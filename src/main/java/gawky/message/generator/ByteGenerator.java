package gawky.message.generator;

import gawky.host.PackedDecimal;
import gawky.message.Formatter;
import gawky.message.part.Desc;
import gawky.message.part.Part;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ByteGenerator extends Generator
{
	private static Log log = LogFactory.getLog(ByteGenerator.class);

	String encoding;
	
	public ByteGenerator(String encoding)
	{
		this.encoding = encoding;
	}
	
	public byte[] buildString(Part bean, int len)
	{
		byte str[] = new byte[len];
		Arrays.fill(str, (byte)0x40); 

		if(bean == null)
			return str;
		
		bean.beforeStore();
		
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;
		
		int pos = 0;
		try 
		{
			for(int i = 0; i < descs.length; i++)
			{
				desc = descs[i];
				
				Object val = null;
				
				try {
					val = desc.getValue(bean);
				} catch(Exception e) {
				}
				
				if(desc.format == Desc.FMT_DIGIT)
				{
					if(!desc.isPacked())
						System.arraycopy(Formatter.getStringN(desc.len, (String)val).getBytes(encoding), 0, str, pos, desc.len);
						//System.arraycopy(Ebcdic.toEbcdic( Formatter.getStringN(desc.len, val) ), 0, str, pos, desc.len);
					else // PACKED DECIMAL
					{
						if(desc.isUnsigned()) {
							System.arraycopy(
								PackedDecimal.writeNumberPacked(desc.len, Long.parseLong((String)val), false), 0, str, pos, desc.len);
						} else {
							if(val == null)
								val = "0";
							System.arraycopy(
									PackedDecimal.writeNumberPacked(desc.len, Long.parseLong((String)val), true), 0, str, pos, desc.len);
						}
					}
						
					pos += desc.len;
				}
				else if(desc.format == Desc.FMT_BINARY)
				{	// von links mit null füllen
					System.arraycopy(Formatter.bpad(desc.len, (byte[])val), 0, str, pos, desc.len);
					pos += desc.len;
				}
				else
				{
					if(desc.delimiter != null) {
						//str.append( Formatter.getStringV(desc.len, val, desc.delimiter) );
					} 
					else
					{
						if(desc.format != Desc.FMT_CONSTANT)
						{
							if(desc.len != 0) {
								System.arraycopy(Formatter.getStringC(desc.len, (String)val).getBytes(encoding), 0, str, pos, desc.len);
								pos += desc.len;

								//str.append( Formatter.getStringC(desc.len, val) );
							} else {
								//str.append( val );
							}
						}
						else
						{
							System.arraycopy(Formatter.getStringC(desc.len, desc.name).getBytes(encoding), 0, str, pos, desc.len);
							pos += desc.len;

							//str.append( Formatter.getStringC(desc.len, desc.name) );
						}
					}
				} 
			}
		} catch(Exception e) {
			log.error("getProperty in Parser" ,e);
		}
		
		return str;
	}

	
	
	
	public String buildDebugString(Part bean)
	{
		if(bean == null)
			return "";

		bean.beforeStore();

		StringBuilder str = new StringBuilder(1000);
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;
		
		try 
		{
			for(int i = 0; i < descs.length; i++)
			{
				desc = descs[i];
				
				Object val = null;
				
				try {
					val = desc.getValue(bean);
				} catch(Exception e) {
				}
				
				str.append( desc.name).append( " <" ); 
				if(desc.format == Desc.FMT_DIGIT)
				{
					str.append( Formatter.getStringN(desc.len, (String)val) );
					if(desc.delimiter != null)
						str.append( desc.delimiter );
				}
				else if(desc.format == Desc.FMT_BINARY)
				{	// von rechts mit null füllen
					str.append( Formatter.bpad(desc.len, (byte[])val) );
					if(desc.delimiter != null)
						str.append( desc.delimiter );					
				}
				else
				{
					if(desc.delimiter != null)
						str.append( Formatter.getStringV(desc.len, (String)val, desc.delimiter) );
					else
					{
						if(desc.format != Desc.FMT_CONSTANT)
						{
							if(desc.len != 0)
								str.append( Formatter.getStringC(desc.len, (String)val) );
							else
								str.append( val );
						}
						else
						{
							str.append( Formatter.getStringC(desc.len, desc.name) );
						}
					}
				}
				str.append( ">\n" );
			}
		} catch(Exception e) {
			log.error("getProperty in Parser" ,e);
		}
		
		return str.toString();
	}
}
