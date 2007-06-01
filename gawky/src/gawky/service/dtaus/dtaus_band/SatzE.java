package gawky.service.dtaus.dtaus_band;

import gawky.message.generator.EBCDICGenerator;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescP;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

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
    
    public byte[] getSatzE() 
    {
    	return generator.generateString(this, linelen);
    }
    
    private String anzahlcsaetze;
    private String sumbetraege;
    private String sumkontonummern;
    private String sumblz;
    private String sumeurobetraege;
    
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
