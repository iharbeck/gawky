package gawky.service.dtaus;

import gawky.message.Formatter;
import gawky.service.dtaus.dtaus_disc.SatzC;
import gawky.service.dtaus.dtaus_disc.SatzCe;
import gawky.service.dtaus.dtaus_disc.SatzE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;

public class EBDisk 
{
    private void read(File f, EBProcessorDisk processor) throws IOException, Exception
    {
		// Open the file and then get a channel from the stream
		FileInputStream fis = new FileInputStream(f);
		FileChannel fc = fis.getChannel();
	
		// Get the file's size and then map it into memory
		int sz = (int)fc.size();
		MappedByteBuffer mappedbuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

		byte[] line = new byte[256];
		byte[] length = new byte[4];
		
		byte[] part = new byte[29];
		
		while(mappedbuffer.remaining() > 5)
		{
			mappedbuffer.get(length, 0, 4);
			
			int linelen = Integer.parseInt(new String(length));
			
			if(linelen % 128 != 0)		// AUF SEGMENT LÄNGE VERGRÖSSERN
				linelen = 256;
			
			mappedbuffer.get(line, 4, linelen-4);

			String type = new String(line, 4, 1);
			
			if(type.startsWith("A"))
			{
				processor.processSatzA(line);
				//System.exit(-1);
			} 
			else if(type.startsWith("E"))
			{
				processor.processSatzE(line);
			}
			else if(type.startsWith("C")) 
			{
				SatzC satz = processor.processSatzC(line);

				int ext = Integer.parseInt(satz.getErweiterungskennnzeichen());
				
				for(int x=0; x < ext && x <= 1; x++)
				{
					// TODO MORE THAN 2 EXTENTIONS
					//mappedbuffer.get(part, 0, 29);
					System.arraycopy(line, 187 + 29*x, part, 0, 29);
					processor.processSatzCe(part, x);
				}
			}
		}

		// Close the channel and the stream
		fc.close();
    }
    
    
    private void write(File f, EBProcessorDisk processor) throws IOException, Exception
    {
		FileOutputStream fos = new FileOutputStream(f);
	
		processor.getSatza().setLen("0128");
		
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
			
			c.setLen("0" + (187 + c.getSatzCe().size() * 29 ));

			fos.write(c.getBytes());
			Iterator it2 = c.getSatzCe().iterator();
			while(it2.hasNext())
			{
				SatzCe el = (SatzCe)it2.next();
				fos.write(Formatter.getStringC(256-187, el.toString()).getBytes());
			}
		}
		
		SatzE e = processor.getSatze();
		
		e.setLen("0128");
		e.setAnzahlcsaetze(Long.toString(count));
		e.setSumeurobetraege(Long.toString(sumbetraege));
		e.setSumkontonummern(Long.toString(sumkto));
		e.setSumblz(Long.toString(sumblz));
		
		fos.write(processor.getSatze().getBytes());
		
		fos.close();
    }

    public static void main(String[] args) throws Exception
    {
    	EBProcessorDisk processor = new EBProcessorDisk();
    	
    	File f = new File("P:/bcos/pcama/DBDIRECT");
		//File f = new File("C:/work/gawky/format/rtldti230207.org");
		new EBDisk().read(f, processor);
		
		// TODO WRITER (Eine Zeile)
		// A Satz			128		Len 128
		// C Satz + Ce[1-2]	256		Len 187 + x*29
		// E Satz           128 	Len 128
		
		File fo = new File("P:/bcos/pcama/DBDIRECT.out");
		//File f = new File("C:/work/gawky/format/rtldti230207.org");
		new EBDisk().write(fo, processor);
		
    }
}

