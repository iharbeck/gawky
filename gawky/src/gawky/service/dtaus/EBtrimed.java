package gawky.service.dtaus;

import gawky.host.Ebcdic;
import gawky.service.dtaus.dtaus_band.SatzC;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class EBtrimed extends EBProcessorHost
{
    private void read(File f) throws IOException, Exception
    {
		// Open the file and then get a channel from the stream
		FileInputStream fis = new FileInputStream(f);
		FileChannel fc = fis.getChannel();
	
		// Get the file's size and then map it into memory
		int sz = (int)fc.size();
		MappedByteBuffer mappedbuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

		// Zeile lesen
		int len = 146;
		
		byte[] line = new byte[len];
		
		//A 146
		//C 146 + 29
		//E 146
		
		byte[] part = new byte[29];
		
		while(mappedbuffer.hasRemaining())
		{
			mappedbuffer.get(line, 0, len);

			String type = Ebcdic.toUnicode(new byte[] {line[0]});
			
			if(type.startsWith("A"))
			{
				processSatzA(line);
				//System.exit(-1);
			} 
			else if(type.startsWith("E"))
			{
				processSatzE(line);
				//System.exit(-1);
			}
			else if(type.startsWith("C")) 
			{
				SatzC satz = processSatzC(line);

				int ext = Integer.parseInt(satz.getErweiterungskennnzeichen());
				
				for(int x=0; x < ext; x++)
				{
					mappedbuffer.get(part, 0, 29);

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
		File f = new File("C:/work/gawky/format/dtaus.bin");
		//File f = new File("C:/work/gawky/format/rtldti230207.org");
		new EBtrimed().read(f);
    }

}

