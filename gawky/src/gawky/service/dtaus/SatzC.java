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
public class SatzC extends Part 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescF(Desc.FMT_A, Desc.CODE_R, 4,   "len"),          
			new DescC("C"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 8,   "blzerstbeteiligt"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 8,   "blzkontofuehrend"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 10,   "kontonummer"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 13,   "referenznummer"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 2,   "textschluessel"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "textschluesselergaenzung"),
			new Reserved(1),
			new DescF(Desc.FMT_A, Desc.CODE_R, 11,  "betragdm"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 8,   "blzauftraggeber"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 10,   "kontonummerauftraggeber"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 11,  "betrageuro"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "valuta"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "empfaengername"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "auftragnehmername"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "verwendungszweck"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "waehrungskennzeichen"),
			new Reserved(2),
			new DescF(Desc.FMT_A, Desc.CODE_R, 2,   "erweiterungskennnzeichen"),
		}; 
	}

    
    
    private String len;
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
}
