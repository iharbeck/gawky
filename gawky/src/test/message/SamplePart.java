package test.message;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.Part;

public class SamplePart extends Part {

	// Record definition
	public Desc[] getDesc() {
		return new Desc[] {
			new DescC("H"),
			new Desc(Desc.FMT_A,      Desc.CODE_F, 4,  "feld1"),
			new Desc(Desc.FMT_A,      Desc.CODE_F, 10, "feld2"),
			new Desc(Desc.FMT_A,      Desc.CODE_F, 10, "feld3"),
			new Desc(Desc.FMT_BINARY, Desc.CODE_F, 4,  "feld4"),
			new Desc(Desc.FMT_LOWER,  Desc.CODE_O, 20, "feld5", Desc.END),
			new Desc(Desc.FMT_LOWER,  Desc.CODE_O, 20, "feld6", Desc.END),
			new Desc(Desc.FMT_A,      Desc.CODE_O, 8,  "feld7")
		};
	}
	
	String feld1;
	String feld2;
	String feld3;
	String feld4;
	String feld5;
	String feld6;
	String feld7;
	
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
	public String getFeld6() {
		return feld6;
	}
	public void setFeld6(String feld6) {
		this.feld6 = feld6;
	}
	public String getFeld7() {
		return feld7;
	}
	public void setFeld7(String feld7) {
		this.feld7 = feld7;
	}
}
