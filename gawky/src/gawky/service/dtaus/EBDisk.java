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
	public static void read(File f, EBProcessorDisk processor) throws IOException, Exception
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
				
				int x=0;
				for(; x < ext && x <= 1; x++)
				{
					System.arraycopy(line, 187 + 29*x, part, 0, 29);
					processor.processSatzCe(part, x);
				}
				
				// mehr als 2 Extention??
				if(x < ext) {
					mappedbuffer.get(line, 4, 128);

					for(; x < ext; x++)
					{
						System.arraycopy(line, 4 + 29*(x-2), part, 0, 29);
						processor.processSatzCe(part, x);
					}
				}
				
			}
		}

		// Close the channel and the stream
		fc.close();
    }
    
    
	public static void write(File f, EBProcessorDisk processor) throws IOException, Exception
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
			
			int len = 0;
			
			if(c.getSatzCe() != null)
				len = c.getSatzCe().size();
			
			c.setLen("0" + (187 + len * 29 ));

			Iterator it2 = c.getSatzCe().iterator();

			String tmp = c.toString();
			for(int i=1; it2.hasNext() && i <= 2; i++)
			{
				SatzCe el = (SatzCe)it2.next();
				tmp += el.toString();
			}
			
			fos.write(Formatter.getStringC(256, tmp).getBytes());

			if(len > 2)
			{
				tmp = "";
				while(it2.hasNext())
				{
					SatzCe el = (SatzCe)it2.next();
					tmp += el.toString();
				}
				
				fos.write(Formatter.getStringC(256, tmp).getBytes());
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
    	
    	File fi = new File("P:/bcos/pcama/DBDIRECT");
		//File f = new File("C:/work/gawky/format/rtldti230207.org");
		EBDisk.read(fi, processor);
		
		File fo = new File("P:/bcos/pcama/DBDIRECT.out");
		EBDisk.write(fo, processor);
    }
}

