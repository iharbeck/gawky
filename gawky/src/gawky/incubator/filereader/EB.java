package gawky.incubator.filereader;

import gawky.host.Ebcdic;
import gawky.message.parser.EBCDICParser;
import gawky.service.dtaus_band.SatzA;
import gawky.service.dtaus_band.SatzC;
import gawky.service.dtaus_band.SatzCe;
import gawky.service.dtaus_band.SatzE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class EB 
{
    private static void read(File f) throws IOException, Exception
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
		
		
		
		EBCDICParser extparser = new EBCDICParser();
		
		byte[] part = new byte[len];
		
		long sumbetraege = 0;
		long mandanten = 0;
		long sumdeclines = 0;
		long sumkto = 0;
		long sumblz = 0;
		
		long booking = 0;
		
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
				System.out.println("::" + mandanten++);
				
				//System.exit(-1);
			}
			if(type.startsWith("E"))
			{
				SatzE satz = new SatzE();
				
				satz.parse(extparser, line);
		
				
				System.out.println("SUM Beträge: " + satz.getSumeurobetraege());
				System.out.println("SUM Beträge check: " + sumbetraege);
				System.out.println("count: " + satz.getAnzahlcsaetze());
				System.out.println("count check: " + sumdeclines);
				System.out.println("kto: " + satz.getSumkontonummern());
				System.out.println("kto check: " + sumkto);
				System.out.println("blz: " + satz.getSumblz());
				System.out.println("blz check: " + sumblz);

				System.out.println("::: " + booking);
				
				sumdeclines = 0;
				sumbetraege = 0;
				sumkto = 0;
				sumblz = 0;
			}
			else if(type.startsWith("C")) 
			{
				sumdeclines++;
				booking++;
				
				SatzC satz = new SatzC();
				
				satz.parse(extparser, line);

				System.out.println("KTO Mandant: " + satz.getKontonummer());
				System.out.println("BLZ Mandant: " + satz.getBlzkontofuehrend());				
				System.out.println("KTO Kunde:   " + satz.getKontonummerauftraggeber());
				System.out.println("BLZ Kunde:   " + satz.getBlzauftraggeber());
				System.out.println("TEXTKEY:     " + satz.getTextschluessel() + ":" + satz.getTextschluesselergaenzung());

				System.out.println("Betrag:      " + satz.getBetrageuro());

				sumbetraege += Long.parseLong(satz.getBetrageuro());
				
				sumkto += Long.parseLong(satz.getKontonummer());
				sumblz += Long.parseLong(satz.getBlzkontofuehrend());
				
				System.out.println("KUND:        " + satz.getAuftragnehmername());
				System.out.println("VERW:        " + satz.getVerwendungszweck());

				System.out.println("EMPF:        " + satz.getEmpfaengername());

				
				int ext = Integer.parseInt(satz.getErweiterungskennnzeichen());
			
				String[] store = new String[10];
				
				for(int x=0; x < ext; x++)
				{
					SatzCe satze = new SatzCe();
					
					part = new byte[29];
					System.arraycopy(line, 144 + 2 + (x*29), part, 0, 29);
					
					satze.parse(extparser, part);
					
					System.out.println("["+satze.getKennzeichen() +"]" + satze.getDaten());
				
//					if(satze.getDaten().contains("AM 21.02.07 ZUR") || satze.getDaten().contains("KEINE EINZUGSERM"))
//					{
//						byte[] gg = satze.getDaten().getBytes(); 
//
//						System.out.println((char)0x7F);
//						System.out.println(gg);
// 					}
					
					store[x] = satze.getDaten();
				}
				
				if(satz.getTextschluessel().equals("9"))
				{
					String dat;
					try {
						dat = store[1] != null ? store[1] : "";			
						System.out.println("BETRAG___:" + Long.parseLong(dat.substring(11, 23).replaceAll("[\\., ]", "")));
	
						dat = store[1] != null ? store[2] : "";
						System.out.println("FEE FREMD:" + Long.parseLong(dat.substring(11, 15).replaceAll("[\\., ]", "")));
						System.out.println("FEE EIGEN:" + Long.parseLong(dat.substring(21, 25).replaceAll("[\\., ]", "")));						
					} catch (Exception e) {
						System.out.println(e);
						System.exit(-1);
					}
				} else {
					System.out.println("LOOK");
				}

				System.out.println("---");
			}
		}

		// Close the channel and the stream
		fc.close();
    }

    public static void main(String[] args) throws Exception
    {
		//File f = new File("C:/work/gawky/format/dtaus.bin");
		File f = new File("C:/work/gawky/format/rtldti230207.org");
	    
		try {
		read(f);
		} catch (Exception e) {
		
			System.out.println(e);
			System.exit(-1);
		}
    }

}

