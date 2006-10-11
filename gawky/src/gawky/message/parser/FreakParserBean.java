package gawky.message.parser;

import gawky.message.part.Desc;
import gawky.message.part.DescF;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class FreakParserBean extends Part {

	public Desc[] getDesc() {
		return new Desc[] {
			new DescV(3, "feld1", "#[A-Z]"),
			new DescV(4, "feld2", "#[A-Z]"),
			new DescV(2, "feld3", "#[0-9]"),
			new DescV(3, "feld4", "#[A-Z]"),
			new DescF(2, "feld5")
		};
	}
	
	String feld1;
	String feld2;
	String feld3;
	String feld4;
	String feld5;
	
	public String getFeld1() {
		return feld1;
	}
	public void setFeld1(String feld1) {
		this.feld1 = feld1;
	}
	public String getFeld2() {
		return feld2;
	}
	public void setFeld2(String feld2) {
		this.feld2 = feld2;
	}
	public String getFeld3() {
		return feld3;
	}
	public void setFeld3(String feld3) {
		this.feld3 = feld3;
	}
	public String getFeld4() {
		return feld4;
	}
	public void setFeld4(String feld4) {
		this.feld4 = feld4;
	}
	public String getFeld5() {
		return feld5;
	}
	public void setFeld5(String feld5) {
		this.feld5 = feld5;
	}
	
}