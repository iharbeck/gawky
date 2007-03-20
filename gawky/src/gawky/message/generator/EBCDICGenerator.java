package gawky.message.generator;

import java.util.Arrays;

import gawky.host.Ebcdic;
import gawky.host.PackedDecimal;
import gawky.message.Formatter;
import gawky.message.part.Desc;
import gawky.message.part.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EBCDICGenerator extends Generator
{
	private static Log log = LogFactory.getLog(EBCDICGenerator.class);

	public byte[] generateString(Part bean, int len)
	{
		byte str[] = new byte[len];
		Arrays.fill(str, (byte)0x40); 

		if(bean == null)
			return str;
		
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;
		
		int pos = 0;
		try 
		{
			for(int i = 0; i < descs.length; i++)
			{
				desc = descs[i];
				
				String val = null;
				
				try {
					val = desc.getValue(bean);
				} catch(Exception e) {
				}
				
				if(desc.format == Desc.FMT_DIGIT)
				{
					if(!desc.isPacked())
						System.arraycopy(Ebcdic.toEbcdic( Formatter.getStringN(desc.len, val) ), 0, str, pos, desc.len);
					else // PACKED DECIMAL
					{
						if(desc.isUnsigned()) {
							System.arraycopy(
								PackedDecimal.writeNumberPacked(desc.len, Long.parseLong(val), false), 0, str, pos, desc.len);
						} else {
							if(val == null)
								val = "0";
							System.arraycopy(
									PackedDecimal.writeNumberPacked(desc.len, Long.parseLong(val), true), 0, str, pos, desc.len);
//							System.arraycopy(
//								((DescP)desc).getPackeddecimal().pack( Formatter.getStringN(desc.len, val) ), 0, str, pos, desc.len);
						}
					}
						
					pos += desc.len;
				}
				else if(desc.format == Desc.FMT_BINARY)
				{	// von rechts mit null füllen
					System.arraycopy(Ebcdic.toEbcdic( Formatter.getStringNL(desc.len, val) ), 0, str, pos, desc.len);
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
								System.arraycopy(Ebcdic.toEbcdic( Formatter.getStringC(desc.len, val) ), 0, str, pos, desc.len);
								pos += desc.len;

								//str.append( Formatter.getStringC(desc.len, val) );
							} else {
								//str.append( val );
							}
						}
						else
						{
							System.arraycopy(Ebcdic.toEbcdic( Formatter.getStringC(desc.len, desc.name) ), 0, str, pos, desc.len);
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

	
	
	
	public String generateDebugString(Part bean)
	{
		if(bean == null)
			return "";
		
		StringBuilder str = new StringBuilder(1000);
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;
		
		try 
		{
			for(int i = 0; i < descs.length; i++)
			{
				desc = descs[i];
				
				String val = null;
				
				try {
					val = desc.getValue(bean);
				} catch(Exception e) {
				}
				
				str.append( desc.name).append( " <" ); 
				if(desc.format == Desc.FMT_DIGIT)
				{
					str.append( Formatter.getStringN(desc.len, val) );
					if(desc.delimiter != null)
						str.append( desc.delimiter );
				}
				else if(desc.format == Desc.FMT_BINARY)
				{	// von rechts mit null füllen
					str.append( Formatter.getStringNL(desc.len, val) );
					if(desc.delimiter != null)
						str.append( desc.delimiter );					
				}
				else
				{
					if(desc.delimiter != null)
						str.append( Formatter.getStringV(desc.len, val, desc.delimiter) );
					else
					{
						if(desc.format != Desc.FMT_CONSTANT)
						{
							if(desc.len != 0)
								str.append( Formatter.getStringC(desc.len, val) );
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
