package gawky.service.dtaus.dtaus_disc;

import gawky.global.Constant;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Reserved;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author  Ingo Harbeck
 */
public class SatzA extends gawky.service.dtaus.dtaus_band.SatzA 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescF(Desc.FMT_A, Desc.CODE_O, 4,   "len"),          
			new DescC("A"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 2,   "kennzeichen"),          
			new DescF(Desc.FMT_9, Desc.CODE_R, 8,   "blzempfaenger"),          
			new DescF(Desc.FMT_9, Desc.CODE_R, 8,   "blzsender"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "empfaenger"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 6,   "dateidatum"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 4,   "valutadatum"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 10,  "kontonummer"),
			new DescF(Desc.FMT_9, Desc.CODE_O, 10,  "referenznummer"),
			new Reserved(15),
			new DescF(Desc.FMT_A, Desc.CODE_O, 8,   "executedate"),
			new Reserved(24),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "waehrungskennzeichen")
		}; 
	}

    public byte[] getSatzA() throws UnsupportedEncodingException
    {
    	return this.getBytes(Constant.ENCODE_LATIN1);
    }
    
    public String getLen() {
		return "0128";
	}

    public void setLen(String len) {
	}
}
