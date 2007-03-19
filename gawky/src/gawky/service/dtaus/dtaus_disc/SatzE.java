package gawky.service.dtaus.dtaus_disc;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Reserved;

/**
 *
 * @author  Ingo Harbeck
 */
public class SatzE extends gawky.service.dtaus.dtaus_band.SatzE 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescF(Desc.FMT_A, Desc.CODE_O, 4,  "len"),          
			new DescC("E"),
		    new Reserved(5),
			new DescF(Desc.FMT_9, Desc.CODE_R, 7,  "anzahlcsaetze"),          
			new DescF(Desc.FMT_9, Desc.CODE_R, 13, "sumbetraege"),          
			new DescF(Desc.FMT_9, Desc.CODE_R, 17, "sumkontonummern"),          
			new DescF(Desc.FMT_9, Desc.CODE_R, 17, "sumblz"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 13, "sumeurobetraege"),
		    new Reserved(51),
		}; 
	}

    private String len;

    public String getLen() {
		return len;
	}
	public void setLen(String len) {
		this.len = len;
	}
}
