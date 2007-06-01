package gawky.service.dtaus;

import gawky.global.Constant;
import gawky.service.dtaus.dtaus_disc.SatzC;
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
			
			int linelen = Integer.parseInt(new String(length, Constant.ENCODE_LATIN1));
			
			if(linelen % 128 != 0)		// AUF SEGMENT L�NGE VERGR�SSERN
				linelen = 256;
			
			mappedbuffer.get(line, 4, linelen-4);

			String type = new String(line, 4, 1);
			
			if(type.startsWith("C")) 
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
			else if(type.startsWith("A"))
			{
				processor.processSatzA(line);
			} 
			else if(type.startsWith("E"))
			{
				processor.processSatzE(line);
			}
		}

		// Close the channel and the stream
		fc.close();
    }
    
    
	public static void write(File f, EBProcessorDisk processor) throws IOException, Exception
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

    public static void main(String[] args) throws Exception
    {
    	EBProcessorDisk processor = new EBProcessorDisk();
    	
    	File fi = new File("G:/bcos/pcama/DBDIRECT");
		//File f = new File("C:/work/gawky/format/rtldti230207.org");
		EBDisk.read(fi, processor);
		
		File fo = new File("G:/bcos/pcama/DBDIRECT.out");
		EBDisk.write(fo, processor);
    }
}

