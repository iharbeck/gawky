package gawky.service.dtaus;

import gawky.global.Constant;
import gawky.message.parser.Parser;
import gawky.message.parser.ParserException;
import gawky.service.dtaus.dtaus_disc.SatzA;
import gawky.service.dtaus.dtaus_disc.SatzC;
import gawky.service.dtaus.dtaus_disc.SatzCe;
import gawky.service.dtaus.dtaus_disc.SatzE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;

public class DTDisk 
{
	public static void read(File f, DTProcessorDisk processor) throws IOException, Exception
    {
		DTDisk handler = new DTDisk();
		
		handler.open(f);
		
		while(handler.next())
		{
			if(handler.isSatzC()) 
			{
				SatzC satzc = handler.getSatzc();
				
				processor.getSatzcArray().add(satzc);
			} 
			else if(handler.isSatzE())
			{
				SatzE satze = handler.getSatze();

				processor.setSatze(satze);
			}
			else if(handler.isSatzA())  // Kopfsatz Mandant ermitteln
			{
				SatzA satza = handler.getSatza();

				processor.setSatza(satza);
			}
		}

		handler.close();
    }
    
    
	public static void write(File f, DTProcessorDisk processor) throws IOException, Exception
    {
		FileOutputStream fos = new FileOutputStream(f);
	
		fos.write(processor.getSatza().getSatzA());
		
		Iterator it = processor.getSatzcArray().iterator();
		
		SatzE satze = new SatzE();
		
		while(it.hasNext())
		{
			SatzC c = (SatzC)it.next(); 
			satze.add(c); // Abstimmsatz
			
			fos.write(c.getSatzC());
		}
		
		fos.write(satze.getSatzE());
		
		fos.close();
    }
	
	static Parser parser = new Parser();
	
	FileChannel fc;
	MappedByteBuffer mappedbuffer;

	SatzA satza = new SatzA();
	SatzE satze = new SatzE();
	SatzC satzc = new SatzC();

	byte[] line   = new byte[128*6];
	byte[] length = new byte[4];
	
	byte[] part   = new byte[29];
	
	public void open(String infile) throws Exception {
		File f = new File(infile);
		open(f);
	}
	
	public void open(File f) throws Exception
	{
		// Open the file and then get a channel from the stream
		FileInputStream fis = new FileInputStream(f);
		fc = fis.getChannel();
	
		// Get the file's size and then map it into memory
		int sz = (int)fc.size();
		mappedbuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);
	}
	
	static final int SATZA = 0;
	static final int SATZC = 1;
	static final int SATZE = 2;
	
	int satztype; 
	
	public final boolean isSatzA() {
		return satztype == SATZA;
	}
	public final boolean isSatzC() {
		return satztype == SATZC;
	}
	public final boolean isSatzE() {
		return satztype == SATZE;
	}
	
	public SatzA getSatza() {
		return satza;
	}
	public SatzC getSatzc() {
		return satzc;
	}
	public SatzE getSatze() {
		return satze;
	}
	
	public boolean next()  throws ParserException, UnsupportedEncodingException
	{
		if(mappedbuffer.remaining() >= 5)
		{
			mappedbuffer.get(length, 0, 4);
			
			int linelen = Integer.parseInt(new String(length, Constant.ENCODE_LATIN1));
			
			if(linelen % 128 != 0)		// AUF SEGMENT L�NGE VERGR�SSERN
			{
				linelen = 256; //((linelen / 128) + 1) * 128;
			}

			mappedbuffer.get(line, 4, linelen-4);

			String type = new String(line, 4, 1);

			if(type.charAt(0) == 'C') 
			{
				satzc = new SatzC();
				satzc.parse(parser, new String(line, Constant.ENCODE_LATIN1));

				
				
				int ext = Integer.parseInt(satzc.getErweiterungskennnzeichen());
				
				int x = 0;
				for(; x < ext && x <= 1; x++)
				{
					System.arraycopy(line, 187 + 29*x, part, 0, 29);
					SatzCe satzce = new SatzCe();
					satzce.parse(new String(part, Constant.ENCODE_LATIN1));
					
					satzc.addExtention(satzce);
				}
				
				// mehr als 2 Extention??
				if(x < ext) 
				{
					do {
						mappedbuffer.get(line, 4, 128);
	
						for(int p=0; x < ext && p < 4; x++, p++)
						{
							System.arraycopy(line, 4 + 29*(p), part, 0, 29);
							SatzCe satzce = new SatzCe();
							satzce.parse(new String(part, Constant.ENCODE_LATIN1));
							
							satzc.addExtention(satzce);
						}
					} while(x < ext);
				}
				satztype = SATZC;
			} 
			else if(type.charAt(0) == 'E')
			{
				satze.parse(parser, new String(line, Constant.ENCODE_LATIN1));
				satztype = SATZE;
			}
			else if(type.charAt(0) == 'A')  // Kopfsatz Mandant ermitteln
			{
				satza.parse(parser, new String(line, Constant.ENCODE_LATIN1));
				satztype = SATZA;
			}
		} else {
			return false;
		}
		
		return true;
	}
	
	public void close() throws Exception {
		// Close the channel and the stream
		if(fc != null)
			fc.close();
	}

    public static void main(String[] args) throws Exception
    {
    	DTProcessorDisk processor = new DTProcessorDisk();
    	
    	File fi = new File("G:/bcos/pcama/DBDIRECT");
		//File f = new File("C:/work/gawky/format/rtldti230207.org");
		DTDisk.read(fi, processor);
		
		File fo = new File("G:/bcos/pcama/DBDIRECT.out");
		DTDisk.write(fo, processor);
    }
}
