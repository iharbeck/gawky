package gawky.service.dtaus.dtaus_band;

import gawky.message.generator.EBCDICGenerator;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.DescP;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author  Ingo Harbeck
 */
public class SatzA extends Part 
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
			//new DescF(Desc.FMT_A, Desc.CODE_O, 4,   "valutadatum"),
			new DescP(6, "kontonummer"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 10, "referenznummer"),
		    new Reserved(15),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 8,  "executedate"),  
		    new Reserved(58),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 1,  "waehrungskennzeichen")
		}; 
	}

    static EBCDICGenerator generator = new EBCDICGenerator();
    
    public byte[] getSatzA() throws UnsupportedEncodingException
    {
    	int linelen = 150;
    	byte[] satz = generator.buildString(this, linelen);
    	
    	Helper.writeNumberBinary(satz, linelen-4);
    	
    	return satz;
    }
    
    private String kennzeichen;
    private String blzempfaenger;
    private String blzsender;
    private String empfaenger;
    private String dateidatum;
    private String valutadatum;
    private String kontonummer;
    private String referenznummer;
    private String executedate;
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
	public String getExecutedate() {
		return executedate;
	}
	public void setExecutedate(String executedate) {
		this.executedate = executedate;
	}
}
