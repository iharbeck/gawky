package gawky.service.dtaus;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
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
			new DescF(Desc.FMT_A, Desc.CODE_R, 4,   "len"),          
			new DescC("E"),
		    new Reserved(5),
			new DescF(Desc.FMT_A, Desc.CODE_R, 4,   "anzahlcsaetze"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 7,   "sumbetraege"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 9,   "sumkontonummern"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 9,   "sumblz"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 7,   "sumeurobetraege"),
		    new Reserved(104),
		}; 
	}

    private String len;
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
	public String getLen() {
		return len;
	}
	public void setLen(String len) {
		this.len = len;
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
