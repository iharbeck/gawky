package gawky.service.dtaus.dtaus_band;

import java.util.ArrayList;

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
public class SatzC extends Part 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("C"),
			new DescP(5, "blzerstbeteiligt"),          
			new DescP(5, "blzkontofuehrend"),          
			new DescP(6, "kontonummer"),          
			new DescP(6, "referenznummer", false),
			
			new Reserved(7),
			
			new DescP(1, "textschluessel", false),
			new DescP(2, "textschluesselergaenzung"),

			new Reserved(1),
			
			new DescP(6, "betragdm"),
			new DescP(5, "blzauftraggeber"),
			new DescP(6, "kontonummerauftraggeber"),
			new DescP(6, "betrageuro"),
			
			new Reserved(3),
			
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "empfaengername"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "auftragnehmername"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "verwendungszweck"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "waehrungskennzeichen"),
			
			new Reserved(2),
			
			new DescP(2,   "erweiterungskennnzeichen"),
		}; 
	}

    ArrayList satzCe;
    
    public void addExtention(SatzCe ext) {
    	if(satzCe == null)
    		satzCe = new ArrayList();
    	
    	satzCe.add(ext);
    }
  
    public void reset() {
    	satzCe = null;
    }
    
    private String blzerstbeteiligt;
    private String blzkontofuehrend;
    private String kontonummer;
    private String referenznummer;
    private String bankintern;
    private String textschluessel;
    private String textschluesselergaenzung;
    private String betragdm;
    private String blzauftraggeber;
    private String kontonummerauftraggeber;
    private String betrageuro;
    private String valuta;
    private String empfaengername;
    private String auftragnehmername;
    private String verwendungszweck;
    private String waehrungskennzeichen;
    private String erweiterungskennnzeichen;
	public String getAuftragnehmername() {
		return auftragnehmername;
	}
	public void setAuftragnehmername(String auftragnehmername) {
		this.auftragnehmername = auftragnehmername;
	}
	public String getBankintern() {
		return bankintern;
	}
	public void setBankintern(String bankintern) {
		this.bankintern = bankintern;
	}
	public String getBetragdm() {
		return betragdm;
	}
	public void setBetragdm(String betragdm) {
		this.betragdm = betragdm;
	}
	public String getBetrageuro() {
		return betrageuro;
	}
	public void setBetrageuro(String betrageuro) {
		this.betrageuro = betrageuro;
	}
	public String getBlzauftraggeber() {
		return blzauftraggeber;
	}
	public void setBlzauftraggeber(String blzauftraggeber) {
		this.blzauftraggeber = blzauftraggeber;
	}
	public String getBlzerstbeteiligt() {
		return blzerstbeteiligt;
	}
	public void setBlzerstbeteiligt(String blzerstbeteiligt) {
		this.blzerstbeteiligt = blzerstbeteiligt;
	}
	public String getBlzkontofuehrend() {
		return blzkontofuehrend;
	}
	public void setBlzkontofuehrend(String blzkontofuehrend) {
		this.blzkontofuehrend = blzkontofuehrend;
	}
	public String getEmpfaengername() {
		return empfaengername;
	}
	public void setEmpfaengername(String empfaengername) {
		this.empfaengername = empfaengername;
	}
	public String getErweiterungskennnzeichen() {
		return erweiterungskennnzeichen;
	}
	public void setErweiterungskennnzeichen(String erweiterungskennnzeichen) {
		this.erweiterungskennnzeichen = erweiterungskennnzeichen;
	}
	public String getKontonummer() {
		return kontonummer;
	}
	public void setKontonummer(String kontonummer) {
		this.kontonummer = kontonummer;
	}
	public String getKontonummerauftraggeber() {
		return kontonummerauftraggeber;
	}
	public void setKontonummerauftraggeber(String kontonummerauftraggeber) {
		this.kontonummerauftraggeber = kontonummerauftraggeber;
	}
	public String getReferenznummer() {
		return referenznummer;
	}
	public void setReferenznummer(String referenznummer) {
		this.referenznummer = referenznummer;
	}
	public String getTextschluessel() {
		return textschluessel;
	}
	public void setTextschluessel(String textschluessel) {
		this.textschluessel = textschluessel;
	}
	public String getTextschluesselergaenzung() {
		return textschluesselergaenzung;
	}
	public void setTextschluesselergaenzung(String textschluesselergaenzung) {
		this.textschluesselergaenzung = textschluesselergaenzung;
	}
	public String getValuta() {
		return valuta;
	}
	public void setValuta(String valuta) {
		this.valuta = valuta;
	}
	public String getVerwendungszweck() {
		return verwendungszweck;
	}
	public void setVerwendungszweck(String verwendungszweck) {
		this.verwendungszweck = verwendungszweck;
	}
	public String getWaehrungskennzeichen() {
		return waehrungskennzeichen;
	}
	public void setWaehrungskennzeichen(String waehrungskennzeichen) {
		this.waehrungskennzeichen = waehrungskennzeichen;
	}

	public ArrayList getSatzCe() {
		return satzCe;
	}

	public void setSatzCe(ArrayList satzCe) {
		this.satzCe = satzCe;
	}
}