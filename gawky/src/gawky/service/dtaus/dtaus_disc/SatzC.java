package gawky.service.dtaus.dtaus_disc;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Reserved;

/**
 *
 * @author  Ingo Harbeck
 */
public class SatzC extends gawky.service.dtaus.dtaus_band.SatzC 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescF(Desc.FMT_A, Desc.CODE_O, 4,   "len"),          
			new DescC("C"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 8,   "blzerstbeteiligt"),          
			new DescF(Desc.FMT_9, Desc.CODE_R, 8,   "blzkontofuehrend"),          
			new DescF(Desc.FMT_9, Desc.CODE_R, 10,  "kontonummer"),          
			new DescF(Desc.FMT_9, Desc.CODE_R, 13,  "referenznummer"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 2,   "textschluessel"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 3,   "textschluesselergaenzung"),
			new Reserved(1),
			new DescF(Desc.FMT_9, Desc.CODE_R, 11,  "betragdm"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 8,   "blzauftraggeber"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 10,  "kontonummerauftraggeber"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 11,  "betrageuro"),
			new Reserved(3),
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "empfaengername"),
			new Reserved(8),
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "auftraggebername"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "verwendungszweck"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "waehrungskennzeichen"),
			new Reserved(2),
			new DescF(Desc.FMT_9, Desc.CODE_R, 2,   "erweiterungskennnzeichen"),
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
