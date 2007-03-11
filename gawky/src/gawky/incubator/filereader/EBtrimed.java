package gawky.incubator.filereader;

import gawky.host.Ebcdic;
import gawky.message.parser.EBCDICParser;
import gawky.service.dtaus_band.SatzA;
import gawky.service.dtaus_band.SatzC;
import gawky.service.dtaus_band.SatzCe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class EBtrimed 
{
    private static void read(File f) throws IOException, Exception
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
		
		EBCDICParser extparser = new EBCDICParser();
		
		byte[] part = new byte[29];
		
		while(mappedbuffer.hasRemaining())
		{
			mappedbuffer.get(line, 0, len);

			String type = Ebcdic.toUnicode(new byte[] {line[0]});
			
			
			if(type.startsWith("A"))
			{
				SatzA satz = new SatzA();
				
				satz.parse(extparser, line);
	
				System.out.println("========================================" + satz.getKennzeichen() + satz.getEmpfaenger());
				System.out.println(new String(line));
				System.out.println("::" + satz.getKontonummer());
				
				//System.exit(-1);
			}
			else if(type.startsWith("C")) 
			{
				SatzC satz = new SatzC();
				
				satz.parse(extparser, line);
				
				System.out.println(satz.getBetrageuro());

				System.out.println("KUND: " + satz.getAuftragnehmername());
				System.out.println("VERW: " + satz.getVerwendungszweck());

				
				int ext = Integer.parseInt(satz.getErweiterungskennnzeichen());
				
				for(int x=0; x < ext; x++)
				{
					SatzCe satze = new SatzCe();
					
					mappedbuffer.get(part, 0, 29);
					
					satze.parse(extparser, part);
					
					System.out.print("["+satze.getKennzeichen() +"]" + satze.getDaten());
					//System.out.println("ext: " + new String(satze.getDaten().getBytes(), "CP850"));
				}
				System.out.println();
			}
		}

		// Close the channel and the stream
		fc.close();
    }

    public static void main(String[] args) throws Exception
    {
		File f = new File("C:/work/gawky/format/dtaus.bin");
		//File f = new File("C:/work/gawky/format/rtldti230207.org");
		read(f);
    }

}

