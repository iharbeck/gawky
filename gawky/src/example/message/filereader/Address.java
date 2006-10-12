package example.message.filereader;

import gawky.message.part.Desc;
import gawky.message.part.DescF;
import gawky.message.part.Part;

public class Address extends Part {

	public Desc[] getDesc() {
		return new Desc[] { 
			new DescF(10, "vorname"), 
			new DescF(10, "nachname")
		};
	}
	
	String vorname;
	String nachname;
	
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

}
