package gawky.service.dtaus;

import gawky.service.dtaus.dtaus_band.SatzC;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class EBDisk extends EBProcessorDisk
{
    private void read(File f) throws IOException, Exception
    {
		// Open the file and then get a channel from the stream
		FileInputStream fis = new FileInputStream(f);
		FileChannel fc = fis.getChannel();
	
		// Get the file's size and then map it into memory
		int sz = (int)fc.size();
		MappedByteBuffer mappedbuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

		byte[] line = new byte[256];
		byte[] length = new byte[4];
		
		byte[] part = new byte[29*2];
		
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
				processSatzA(line);
				//System.exit(-1);
			} 
			else if(type.startsWith("E"))
			{
				processSatzE(line);
			}
			else if(type.startsWith("C")) 
			{
				SatzC satz = processSatzC(line);

				int ext = Integer.parseInt(satz.getErweiterungskennnzeichen());
				
				for(int x=0; x < ext; x++)
				{
					// TODO MORE THAN 2 EXTENTIONS
					//mappedbuffer.get(part, 0, 29);
					System.arraycopy(line, 185, part, 0, 29*2);
					processSatzCe(part, x);
				}

				finishSatzC();
			}
		}

		System.out.println("\ncount::" + count);

		// Close the channel and the stream
		fc.close();
    }

    public static void main(String[] args) throws Exception
    {
		File f = new File("P:/bcos/pcama/DBDIRECT");
		//File f = new File("C:/work/gawky/format/rtldti230207.org");
		new EBDisk().read(f);
		
		// TODO WRITER (Eine Zeile)
		// A Satz			128
		// C Satz + Ce[1-2]	256
		// E Satz           128 
    }
}

