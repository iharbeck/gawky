package gawky.service.dtaus.dtaus_band;

import gawky.global.Format;
import gawky.message.generator.EBCDICGenerator;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescP;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author  Ingo Harbeck
 */
public class SatzE extends Part 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("E"),
		    new Reserved(5),
			new DescP(4, "anzahlcsaetze"),          
			new DescP(7, "sumbetraege"),          
			new DescP(9, "sumkontonummern"),          
			new DescP(9, "sumblz"),
			new DescP(7, "sumeurobetraege"),
		    new Reserved(104),
		}; 
	}

    static int linelen = 581;
    static EBCDICGenerator generator = new EBCDICGenerator();
    
    public byte[] getSatzE() throws UnsupportedEncodingException
    {
    	return generator.generateString(this, linelen);
    }
    
    protected long lsumeurobetraege;
    protected long lsumkontonummern;
    protected long lsumblz;
    protected long lanzahlcsaetze;
	
	public void add(SatzC satzc) {
		lsumeurobetraege += Format.getLong(satzc.getBetrageuro());
		lsumkontonummern += Format.getLong(satzc.getKontonummer());
		lsumblz          += Format.getLong(satzc.getBlzkontofuehrend());
		lanzahlcsaetze++;
	}

    
    protected String anzahlcsaetze;
    protected String sumbetraege;
    protected String sumkontonummern;
    protected String sumblz;
    protected String sumeurobetraege;
    
	public String getAnzahlcsaetze() {
		return Long.toString(lanzahlcsaetze);
	}
	public void setAnzahlcsaetze(String anzahlcsaetze) {
		this.lanzahlcsaetze = Long.parseLong(anzahlcsaetze);
	}
	public String getSumbetraege() {
		return "0";
	}
	public void setSumbetraege(String sumbetraege) {
	}
	public String getSumblz() {
		return Long.toString(lsumblz);
	}
	public void setSumblz(String sumblz) {
		this.lsumblz = Long.parseLong(sumblz);
	}
	public String getSumeurobetraege() {
		return Long.toString(lsumeurobetraege);
	}
	public void setSumeurobetraege(String sumeurobetraege) {
		this.lsumeurobetraege = Long.parseLong(sumeurobetraege);
	}
	public String getSumkontonummern() {
		return Long.toString(lsumkontonummern);
	}
	public void setSumkontonummern(String sumkontonummern) {
		this.lsumkontonummern = Long.parseLong(sumkontonummern);
	}
}
