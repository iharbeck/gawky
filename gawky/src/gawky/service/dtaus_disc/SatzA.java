package gawky.service.dtaus_disc;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

/**
 *
 * @author  Ingo Harbeck
 */
public class SatzA extends Part 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescF(Desc.FMT_A, Desc.CODE_R, 4,   "len"),          
			new DescC("A"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 2,   "kennzeichen"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 8,   "blzempfaenger"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 8,   "blzsender"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "empfaenger"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 6,   "dateidatum"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 4,   "valutadatum"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 10,  "kontonummer"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 10,  "referenznummer"),
		    new Reserved(47),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "waehrungskennzeichen")
		}; 
	}

    private String len;
    private String kennzeichen;
    private String blzempfaenger;
    private String blzsender;
    private String empfaenger;
    private String dateidatum;
    private String valutadatum;
    private String kontonummer;
    private String referenznummer;
    private String waehrungskennzeichen;
    
	public String getBlzempfaenger() {
		return blzempfaenger;
	}
	public void setBlzempfaenger(String blzempfaenger) {
		this.blzempfaenger = blzempfaenger;
	}
	public String getBlzsender() {
		return blzsender;
	}
	public void setBlzsender(String blzsender) {
		this.blzsender = blzsender;
	}
	public String getDateidatum() {
		return dateidatum;
	}
	public void setDateidatum(String dateidatum) {
		this.dateidatum = dateidatum;
	}
	public String getEmpfaenger() {
		return empfaenger;
	}
	public void setEmpfaenger(String empfaenger) {
		this.empfaenger = empfaenger;
	}
	public String getKennzeichen() {
		return kennzeichen;
	}
	public void setKennzeichen(String kennzeichen) {
		this.kennzeichen = kennzeichen;
	}
	public String getKontonummer() {
		return kontonummer;
	}
	public void setKontonummer(String kontonummer) {
		this.kontonummer = kontonummer;
	}
	public String getLen() {
		return len;
	}
	public void setLen(String len) {
		this.len = len;
	}
	public String getReferenznummer() {
		return referenznummer;
	}
	public void setReferenznummer(String referenznummer) {
		this.referenznummer = referenznummer;
	}
	public String getValutadatum() {
		return valutadatum;
	}
	public void setValutadatum(String valutadatum) {
		this.valutadatum = valutadatum;
	}
	public String getWaehrungskennzeichen() {
		return waehrungskennzeichen;
	}
	public void setWaehrungskennzeichen(String waehrungskennzeichen) {
		this.waehrungskennzeichen = waehrungskennzeichen;
	}
}
