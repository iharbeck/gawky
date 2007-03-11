package gawky.service.dtaus.dtaus_disc;

import gawky.message.part.Desc;
import gawky.message.part.DescF;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

/**
 *
 * @author  Ingo Harbeck
 */
public class SatzCe extends Part 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescF(Desc.FMT_9, Desc.CODE_R, 2,   "kennzeichen"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "daten"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 2,   "kennzeichen2"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "daten2"),
			new Reserved(11),  // bis hier im ersten block 128 Zeichen
			new DescF(Desc.FMT_9, Desc.CODE_R, 2,   "kennzeichen3"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "daten3"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 2,   "kennzeichen4"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "daten4"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 2,   "kennzeichen5"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "daten5"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 2,   "kennzeichen6"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 27,  "daten6"),
			new Reserved(12), // dies im zweitenblock 128 Zeichen
		}; 
	}
    
    private String kennzeichen;
    private String daten;
    private String kennzeichen2;
    private String daten2;
    private String kennzeichen3;
    private String daten3;
    private String kennzeichen4;
    private String daten4;
    private String kennzeichen5;
    private String daten5;
    private String kennzeichen6;
    private String daten6;

    public String getDaten() {
		return daten;
	}
	public void setDaten(String daten) {
		this.daten = daten;
	}
	public String getKennzeichen() {
		return kennzeichen;
	}
	public void setKennzeichen(String kennzeichen) {
		this.kennzeichen = kennzeichen;
	}
	public String getDaten2() {
		return daten2;
	}
	public void setDaten2(String daten2) {
		this.daten2 = daten2;
	}
	public String getKennzeichen2() {
		return kennzeichen2;
	}
	public void setKennzeichen2(String kennzeichen2) {
		this.kennzeichen2 = kennzeichen2;
	}
	public String getDaten3() {
		return daten3;
	}
	public void setDaten3(String daten3) {
		this.daten3 = daten3;
	}
	public String getDaten4() {
		return daten4;
	}
	public void setDaten4(String daten4) {
		this.daten4 = daten4;
	}
	public String getDaten5() {
		return daten5;
	}
	public void setDaten5(String daten5) {
		this.daten5 = daten5;
	}
	public String getDaten6() {
		return daten6;
	}
	public void setDaten6(String daten6) {
		this.daten6 = daten6;
	}
	public String getKennzeichen3() {
		return kennzeichen3;
	}
	public void setKennzeichen3(String kennzeichen3) {
		this.kennzeichen3 = kennzeichen3;
	}
	public String getKennzeichen4() {
		return kennzeichen4;
	}
	public void setKennzeichen4(String kennzeichen4) {
		this.kennzeichen4 = kennzeichen4;
	}
	public String getKennzeichen5() {
		return kennzeichen5;
	}
	public void setKennzeichen5(String kennzeichen5) {
		this.kennzeichen5 = kennzeichen5;
	}
	public String getKennzeichen6() {
		return kennzeichen6;
	}
	public void setKennzeichen6(String kennzeichen6) {
		this.kennzeichen6 = kennzeichen6;
	}

}
