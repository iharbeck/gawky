package gawky.service.dtaus;

import gawky.host.Ebcdic;
import gawky.message.parser.EBCDICParser;
import gawky.message.parser.ParserException;
import gawky.service.dtaus.dtaus_band.Helper;
import gawky.service.dtaus.dtaus_band.SatzA;
import gawky.service.dtaus.dtaus_band.SatzC;
import gawky.service.dtaus.dtaus_band.SatzE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;

public class DTBand
{
	EBCDICParser extparser = new EBCDICParser();
	
	int linelen = 581;
	
	public static DTProcessorBand[] read(File f) throws IOException, Exception
    {
		DTBand handler = new DTBand();
		
		handler.open(f);
		
		long asatz = 0;
		long csatz = 0;
		long cesatz = 0;
		long esatz = 0;
		
		ArrayList<DTProcessorBand> list = new ArrayList<DTProcessorBand>();
		
		DTProcessorBand processor = null;
		
		while(handler.nextvar())
		{
			if(handler.isSatzC()) 
			{
				csatz++;
				SatzC satzc = handler.getSatzc();

				cesatz += Integer.parseInt(satzc.getErweiterungskennnzeichen());
				processor.getSatzcArray().add(satzc);
			} 
			else if(handler.isSatzE())
			{
				esatz++;
				SatzE satze = handler.getSatze();

				processor.setSatze(satze);
			}
			else if(handler.isSatzA())  // Kopfsatz Mandant ermitteln
			{
				asatz++;
				SatzA satza = handler.getSatza();

				processor = new DTProcessorBand();
				
				list.add(processor);
				
				processor.setSatza(satza);
			}
		}

		handler.close();
		
		return list.toArray(new DTProcessorBand[1]);
    }

    public static void write(File f, DTProcessorBand processor) throws IOException, Exception
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

	public boolean nextvar()  throws ParserException, UnsupportedEncodingException
	{	
		if(mappedbuffer.hasRemaining())
		{
			byte[] blen = new byte[4];
			
			mappedbuffer.mark();
			mappedbuffer.get(blen, 0, 4);
			mappedbuffer.reset();
			
			linelen = (int)Helper.readNumberBinary(blen);
			
			mappedbuffer.get(line, 0, linelen + 4);

			String type = Ebcdic.toUnicode(new byte[] {line[4]});
			
			if(type.charAt(0) == 'C') 
			{
				satzc = new SatzC();
				satzc.parse(extparser, line);

				int ext = Integer.parseInt(satzc.getErweiterungskennnzeichen());
			
				for(int x=0; x < ext; x++)
				{
					part = new byte[29];
					System.arraycopy(line, 150 + (x*29), part, 0, 29);
				
					SatzCe satzce = new SatzCe();
					satzce.parse(extparser, part);
					
					satzc.addExtention(satzce);
				}
				satztype = SATZC;
			} 
			else if(type.charAt(0) == 'A')
			{
				satza.parse(extparser, line);
				satztype = SATZA;
			}
			else if(type.charAt(0) == 'E')
			{
				satze.parse(extparser, line);
				satztype = SATZE;
			}
		} else {
			return false;
		}
		
		return true;
    }

	
	public boolean next()  throws ParserException, UnsupportedEncodingException
	{	
		if(mappedbuffer.hasRemaining())
		{
			mappedbuffer.get(line, 0, linelen);

			String type = Ebcdic.toUnicode(new byte[] {line[0]});
			
			if(type.charAt(0) == 'C') 
			{
				satzc = new SatzC();
				satzc.parse(extparser, line);

				int ext = Integer.parseInt(satzc.getErweiterungskennnzeichen());
			
				for(int x=0; x < ext; x++)
				{
					part = new byte[29];
					System.arraycopy(line, 144 + 2 + (x*29), part, 0, 29);
				
					SatzCe satzce = new SatzCe();
					satzce.parse(extparser, part);
					
					satzc.addExtention(satzce);
				}
				satztype = SATZC;
			} 
			else if(type.charAt(0) == 'A')
			{
				satza = new SatzA();
				satza.parse(extparser, line);
				satztype = SATZA;
			}
			else if(type.charAt(0) == 'E')
			{
				satze = new SatzE();
				satze.parse(extparser, line);
				satztype = SATZE;
			}
		} else {
			return false;
		}
		
		return true;
    }
    
	public void open(String infile) throws Exception {
		File f = new File(infile);
		open(f);
	}
	
	MappedByteBuffer mappedbuffer;
	FileChannel fc;
	
	byte[] line = new byte[linelen];

	byte[] part = new byte[linelen];
	
	SatzA satza = new SatzA();
	SatzE satze = new SatzE();
	SatzC satzc = new SatzC();

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
	
	public void close() throws Exception {
		// Close the channel and the stream
		if(fc != null)
			fc.close();
	}
	

    public static void main(String[] args) throws Exception
    {
    	DTProcessorBand processor = new DTProcessorBand();
    	
		//File f = new File("C:/work/gawky/format/dtaus.bin");
		//File fi = new File("G:/bcos/pcama/rtldti230207.org");    
		File fi = new File("G:/TESTFILE3.RAW");    
//		File fi = new File("G:/bcos/pcama/DBDIRECT.outhost");    
		DTBand.read(fi);
		
		File fo = new File("G:/TESTFILE3.RAW_RE");
		DTBand.write(fo, processor);

		
		processor = new DTProcessorBand();
		
		fi = new File("G:/TESTFILE3.RAW_RE");    
//		File fi = new File("G:/bcos/pcama/DBDIRECT.outhost");    
		DTBand.read(fi);
		
		fo = new File("G:/TESTFILE3.RAW_RE2");
		DTBand.write(fo, processor);
		
		
		processor = new DTProcessorBand();
		
		fi = new File("G:/TESTFILE3.RAW_RE2");    
//		File fi = new File("G:/bcos/pcama/DBDIRECT.outhost");    
		DTBand.read(fi);
		
		fo = new File("G:/TESTFILE3.RAW_RE3");
		DTBand.write(fo, processor);
		
//		System.out.println(processor.getSatza().getBlzsender());
//		
//		processor = new DTProcessorBand();
//		fi = new File("G:/bcos/pcama/DBDIRECT.outhost__");    
//		DTBand.read(fi, processor);
//
//		System.out.println(processor.getSatza().getBlzsender());
    }

}

