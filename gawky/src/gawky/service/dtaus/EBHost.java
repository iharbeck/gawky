package gawky.service.dtaus;

import gawky.host.Ebcdic;
import gawky.message.generator.EBCDICGenerator;
import gawky.service.dtaus.dtaus_band.SatzC;
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
	static EBCDICGenerator generator = new EBCDICGenerator();
	
	static int linelen = 581; 
	
	public static void read(File f, EBProcessorHost processor) throws IOException, Exception
    {
		// Open the file and then get a channel from the stream
		FileInputStream fis = new FileInputStream(f);
		FileChannel fc = fis.getChannel();
		
		// Get the file's size and then map it into memory
		int sz = (int)fc.size();
		MappedByteBuffer mappedbuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

		byte[] line = new byte[linelen];
		
		
		byte[] part = new byte[linelen];
		
		
		while(mappedbuffer.hasRemaining())
		{
			mappedbuffer.get(line, 0, linelen);

			String type = Ebcdic.toUnicode(new byte[] {line[0]});
			
			if(type.startsWith("C")) 
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
    
    
    public static void write(File f, EBProcessorHost processor) throws IOException, Exception
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
    	EBProcessorHost processor = new EBProcessorHost();
    	
		//File f = new File("C:/work/gawky/format/dtaus.bin");
		File fi = new File("G:/bcos/pcama/rtldti230207.org");    
//		File fi = new File("G:/bcos/pcama/DBDIRECT.outhost");    
		EBHost.read(fi, processor);
		
		System.out.println(processor.getSatza().getBlzsender());
		System.out.println(processor.getSatza().getBlzempfaenger());
		System.out.println(processor.getSatza().getKontonummer());
		
		File fo = new File("G:/bcos/pcama/DBDIRECT.outhost__");
		EBHost.write(fo, processor);

		System.out.println(processor.getSatza().getBlzsender());
		System.out.println(processor.getSatza().getBlzempfaenger());
		System.out.println(processor.getSatza().getKontonummer());
		
		processor = new EBProcessorHost();
		fi = new File("G:/bcos/pcama/DBDIRECT.outhost__");    
		EBHost.read(fi, processor);

		System.out.println(processor.getSatza().getBlzsender());
		System.out.println(processor.getSatza().getBlzempfaenger());
		System.out.println(processor.getSatza().getKontonummer());
    }

}

