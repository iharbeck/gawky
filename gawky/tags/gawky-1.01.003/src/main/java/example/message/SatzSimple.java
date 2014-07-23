package example.message;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.DescP;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

/**
 *
 * @author  Ingo Harbeck
 */
public class SatzSimple extends Part 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new Reserved(4),	
			new DescC("A"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 2,   "kennzeichen"),          
			new DescP(5, "blzempfaenger"),          
			new DescP(5, "blzsender"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "empfaenger"),
			new DescP(4, "dateidatum"),
			new DescP(4, "valutadatum"),
			new DescP(6, "kontonummer"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 10, "referenznummer"),
		    new Reserved(15),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 8,  "executedate"),  
		    new Reserved(58),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 1,  "waehrungskennzeichen")
		}; 
	}
    
    private String kennzeichen;

	public String getKennzeichen() {
		return kennzeichen;
	}

	public void setKennzeichen(String kennzeichen) {
		this.kennzeichen = kennzeichen;
	}
    
}
