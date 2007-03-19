package gawky.service.dtaus;

import gawky.host.Ebcdic;
import gawky.message.Formatter;
import gawky.service.dtaus.dtaus_band.SatzC;
import gawky.service.dtaus.dtaus_band.SatzCe;
import gawky.service.dtaus.dtaus_band.SatzE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;

public class EBHost
{
	public static void read(File f, EBProcessorHost processor) throws IOException, Exception
    {
		// Open the file and then get a channel from the stream
		FileInputStream fis = new FileInputStream(f);
		FileChannel fc = fis.getChannel();
	
		// Zeile lesen
		int len = 581; //146;
		
		// Get the file's size and then map it into memory
		int sz = (int)fc.size();
		MappedByteBuffer mappedbuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

		byte[] line = new byte[len];
		
		
		byte[] part = new byte[len];
		
		
		while(mappedbuffer.hasRemaining())
		{
			mappedbuffer.get(line, 0, len);

			String type = Ebcdic.toUnicode(new byte[] {line[0]});
			
			if(type.startsWith("A"))
			{
				processor.processSatzA(line);
				
				//System.exit(-1);
			}
			if(type.startsWith("E"))
			{
				processor.processSatzE(line);
			}
			else if(type.startsWith("C")) 
			{
				SatzC satz = processor.processSatzC(line);

				int ext = Integer.parseInt(satz.getErweiterungskennnzeichen());
			
				for(int x=0; x < ext; x++)
				{
					part = new byte[29];
					System.arraycopy(line, 144 + 2 + (x*29), part, 0, 29);
					
					processor.processSatzCe(part, x);
				}
			}
		}

		// Close the channel and the stream
		fc.close();
    }
    
    
    public static void write(File f, EBProcessorHost processor) throws IOException, Exception
    {
    	//TODO check HOST FORMAT!!!!
		FileOutputStream fos = new FileOutputStream(f);
	
		
		fos.write(processor.getSatza().getBytes());
		
		Iterator it = processor.getSatzcArray().iterator();
		
		long sumbetraege = 0;
		long sumkto = 0;
		long sumblz = 0;
		long count = 0;
		
		while(it.hasNext())
		{
			SatzC c = (SatzC)it.next(); 
			
			sumbetraege += Long.parseLong(c.getBetrageuro());
			sumkto      += Long.parseLong(c.getKontonummer());
			sumblz      += Long.parseLong(c.getBlzkontofuehrend());
			count++;
			
			fos.write(c.getBytes());
			Iterator it2 = c.getSatzCe().iterator();
			while(it2.hasNext())
			{
				SatzCe el = (SatzCe)it2.next();
				fos.write(Formatter.getStringC(256-187, el.toString()).getBytes());
			}
		}
		
		SatzE e = processor.getSatze();
		
		e.setAnzahlcsaetze(Long.toString(count));
		e.setSumeurobetraege(Long.toString(sumbetraege));
		e.setSumkontonummern(Long.toString(sumkto));
		e.setSumblz(Long.toString(sumblz));
		
		fos.write(processor.getSatze().getBytes());
		
		fos.close();
    }

    public static void main(String[] args) throws Exception
    {
    	EBProcessorHost processor = new EBProcessorHost();
    	
		//File f = new File("C:/work/gawky/format/dtaus.bin");
		File fi = new File("C:/work/gawky/format/rtldti230207.org");    
		EBHost.read(fi, processor);
		
		File fo = new File("P:/bcos/pcama/DBDIRECT.outhost");
		EBHost.write(fo, processor);
    }

}

