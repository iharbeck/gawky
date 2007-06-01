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
    	anzahlcsaetze   = Long.toString(count);
        sumbetraege     = Long.toString(0);
        sumkontonummern = Long.toString(lsumkto);
        sumblz          = Long.toString(lsumblz);
        sumeurobetraege = Long.toString(lsumbetraege);
        	
    	return generator.generateString(this, linelen);
    }
    
    protected long lsumbetraege;
    protected long lsumkto;
    protected long lsumblz;
    protected long count;
	
	public void add(SatzC satzc) {
		lsumbetraege += Format.getLong(satzc.getBetrageuro());
		lsumkto      += Format.getLong(satzc.getKontonummer());
		lsumblz      += Format.getLong(satzc.getBlzkontofuehrend());
		count++;
	}

    
    protected String anzahlcsaetze;
    protected String sumbetraege;
    protected String sumkontonummern;
    protected String sumblz;
    protected String sumeurobetraege;
    
	public String getAnzahlcsaetze() {
		return anzahlcsaetze;
	}
	public void setAnzahlcsaetze(String anzahlcsaetze) {
		this.anzahlcsaetze = anzahlcsaetze;
	}
	public String getSumbetraege() {
		return sumbetraege;
	}
	public void setSumbetraege(String sumbetraege) {
		this.sumbetraege = sumbetraege;
	}
	public String getSumblz() {
		return sumblz;
	}
	public void setSumblz(String sumblz) {
		this.sumblz = sumblz;
	}
	public String getSumeurobetraege() {
		return sumeurobetraege;
	}
	public void setSumeurobetraege(String sumeurobetraege) {
		this.sumeurobetraege = sumeurobetraege;
	}
	public String getSumkontonummern() {
		return sumkontonummern;
	}
	public void setSumkontonummern(String sumkontonummern) {
		this.sumkontonummern = sumkontonummern;
	}
}
