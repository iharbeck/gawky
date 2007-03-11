package gawky.incubator.filereader;

import gawky.message.parser.EBCDICParser;
import gawky.service.dtaus_band.SatzA;
import gawky.service.dtaus_band.SatzC;
import gawky.service.dtaus_band.SatzCe;
import gawky.service.dtaus_band.SatzE;

import java.nio.MappedByteBuffer;

public class EBProcessor {

	MappedByteBuffer mappedbuffer;
	
	long sumbetraege = 0;
	long mandanten = 0;
	long sumdeclines = 0;
	long sumkto = 0;
	long sumblz = 0;
	
	long booking = 0;
	
	long count = 0;
	
	EBCDICParser extparser = new EBCDICParser();

	String[] cestore;  // Verwzweck
	
	public void processSatzA(byte[] line) throws Exception
	{
		SatzA satz = new SatzA();
		satz.parse(extparser, line);

		System.out.println("========================================" );
		//System.out.println(new String(line));
		System.out.println("::" + satz.getBlzsender());   // nur RTL...
		System.out.println("::" + satz.getKontonummer());
		System.out.println("::" + mandanten++);
	}

	public void processSatzE(byte[] line) throws Exception
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

	SatzC satzc;
	
	public SatzC processSatzC(byte[] line) throws Exception
	{
		cestore = new String[10];

		sumdeclines++;
		booking++;

		satzc = new SatzC();
		satzc.parse(extparser, line);
		
		
		System.out.println("BLZ Mandant: " + satzc.getBlzkontofuehrend());				
		System.out.println("KTO Mandant: " + satzc.getKontonummer());
		System.out.println("BLZ Kunde:   " + satzc.getBlzauftraggeber());
		System.out.println("KTO Kunde:   " + satzc.getKontonummerauftraggeber());
		System.out.println("Referenz:    " + satzc.getReferenznummer());
		System.out.println("TEXTKEY:     " + satzc.getTextschluessel() + ":" + satzc.getTextschluesselergaenzung());

		System.out.println("Betrag:      " + satzc.getBetrageuro());

		sumbetraege += Long.parseLong(satzc.getBetrageuro());
		
		sumkto += Long.parseLong(satzc.getKontonummer());
		sumblz += Long.parseLong(satzc.getBlzkontofuehrend());
		
		System.out.println("KUND:        " + satzc.getAuftragnehmername());
		System.out.println("VERW:        " + satzc.getVerwendungszweck());

		System.out.println("EMPF:        " + satzc.getEmpfaengername());

		count++;

		return satzc;
	}
	
	public void finishSatzC() 
	{
		if(satzc.getTextschluessel().equals("9"))
		{
			String dat;
			try {
				dat = cestore[1] != null ? cestore[1] : "";			
				System.out.println("BETRAG___:" + Long.parseLong(dat.substring(11, 23).replaceAll("[\\., ]", "")));

				dat = cestore[1] != null ? cestore[2] : "";
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
	
	public void processSatzCe(byte[] line, int x) throws Exception
	{
		SatzCe satze = new SatzCe();
		satze.parse(extparser, line);
		
		System.out.println("["+satze.getKennzeichen() +"]" + satze.getDaten());
	
//		if(satze.getDaten().contains("AM 21.02.07 ZUR") || satze.getDaten().contains("KEINE EINZUGSERM"))
//		{
//			byte[] gg = satze.getDaten().getBytes(); 
//
//			System.out.println((char)0x7F);
//			System.out.println(gg);
//			}
		
		cestore[x] = satze.getDaten();
	
	}
}
