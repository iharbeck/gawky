package example.message.filereader;

import gawky.message.part.Desc;
import gawky.message.part.DescF;
import gawky.message.part.Part;

public class Address extends Part {

	public Desc[] getDesc() {
		return new Desc[] { 
			new DescF(10, "vorname"), 
			new DescF(10, "nachname"),
			new DescF(10, "geb")
			
		};
	}
	
	String vorname;
	String nachname;
	String geb;
	
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getGeb() {
		return geb;
	}
	public void setGeb(String geb) {
		this.geb = geb;
	}
	

}
