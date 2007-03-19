package gawky.service.dtaus;

import gawky.host.Ebcdic;
import gawky.service.dtaus.dtaus_band.SatzC;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class EBHost
{
    private void read(File f, EBProcessorHost processor) throws IOException, Exception
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
				
				processor.finishSatzC();
			}
		}

		// Close the channel and the stream
		fc.close();
    }

    public static void main(String[] args) throws Exception
    {
    	EBProcessorHost processor = new EBProcessorHost();
    	
		//File f = new File("C:/work/gawky/format/dtaus.bin");
		File f = new File("C:/work/gawky/format/rtldti230207.org");
	    
		try {
			new EBHost().read(f, processor);
		} catch (Exception e) {
		
			System.out.println(e);
			System.exit(-1);
		}
    }

}

