package gawky.service.dtaus_band;

public class Begleitzettel {

	//Begleitzettel "Belegloser Datenträgeraustausch" Sammel-Überweisung-/-einziehungsauftrag 

	private String volnummer;

	private String erstellungsdatum;

	// SatzE
	private String anzahlcsaetze; 
	private String sumbetraege; 
	private String sumkontonummern;
	private String sumblz;
	
	// SatzA
	private String blzsender; // Bankleitzahl/Kontonummer des Absenders 

	private String kontonummer; //Name, Bankleitzahl/Kontonummer des Empfängers 

	
	public String getAnzahlcsaetze() {
		return anzahlcsaetze;
	}

	public void setAnzahlcsaetze(String anzahlcsaetze) {
		this.anzahlcsaetze = anzahlcsaetze;
	}

	public String getBlzsender() {
		return blzsender;
	}

	public void setBlzsender(String blzsender) {
		this.blzsender = blzsender;
	}

	public String getErstellungsdatum() {
		return erstellungsdatum;
	}

	public void setErstellungsdatum(String erstellungsdatum) {
		this.erstellungsdatum = erstellungsdatum;
	}

	public String getKontonummer() {
		return kontonummer;
	}

	public void setKontonummer(String kontonummer) {
		this.kontonummer = kontonummer;
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

	public String getSumkontonummern() {
		return sumkontonummern;
	}

	public void setSumkontonummern(String sumkontonummern) {
		this.sumkontonummern = sumkontonummern;
	}

	public String getVolnummer() {
		return volnummer;
	}

	public void setVolnummer(String volnummer) {
		this.volnummer = volnummer;
	}

	//Ort, Datum 

	//Firma, Unterschrift 
}
