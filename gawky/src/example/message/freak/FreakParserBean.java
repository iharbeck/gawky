package example.message.freak;

import gawky.message.generator.FreakGenerator;
import gawky.message.parser.FreakParser;
import gawky.message.part.Desc;
import gawky.message.part.DescF;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class FreakParserBean extends Part 
{
	final String DELIMITER_LETTER    = "#[A-Z]";
	final String DELIMITER_NUMBER    = "#[0-9]";
	final String DELIMITER_NOTNUMBER = "#[^0-9]";
	final String PATTERN_CURRENCY    = "%[0-9]*,[0-9]{0,2}"; // ####,##
	
	public Desc[] getDesc() {
		return new Desc[] {
			new DescV(3, "feld1", DELIMITER_LETTER),
			new DescV(4, "feld2", DELIMITER_LETTER),
			new DescV(2, "feld3", DELIMITER_NUMBER),
			new DescV(3, "feld4", DELIMITER_LETTER),
			new DescF(2, "feld5"),
			//
			new DescV(3, "zahlbegin", ","),
			new DescV(2, "zahlend", DELIMITER_NOTNUMBER),
			//
			new DescV(15, "zahl1", PATTERN_CURRENCY),
			new DescV(15, "zahl2", PATTERN_CURRENCY)
			
		};
	}
	
	public static void main(String[] args) throws Exception 
	{
		FreakParser    parser = new FreakParser();
		FreakGenerator generator = new FreakGenerator();
		
		FreakParserBean bean = new FreakParserBean();
		
		String str = "1.3AB456CD122,33222,44,";
		bean.parse(parser, str);
	    System.out.println(str + "\n" + bean.toString(generator));
	    System.out.println(parser.getNext().length());

	    
		str = "1235555A456CD122,33111,5533,2AA";
	    bean.parse(parser, str);
	    System.out.println(str + "\n" + bean.toString(generator));
	    System.out.println(parser.getNext().length());
	}
	
	String feld1;
	String feld2;
	String feld3;
	String feld4;
	String feld5;
	
	String zahlbegin;
	String zahlend;
	
	String zahl1;
	String zahl2;
	
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
	public String getZahlbegin() {
		return zahlbegin;
	}
	public void setZahlbegin(String zahlbegin) {
		this.zahlbegin = zahlbegin;
	}
	public String getZahlend() {
		return zahlend;
	}
	public void setZahlend(String zahlend) {
		this.zahlend = zahlend;
	}
	public String getZahl1() {
		return zahl1;
	}
	public void setZahl1(String zahl1) {
		this.zahl1 = zahl1;
	}
	public String getZahl2() {
		return zahl2;
	}
	public void setZahl2(String zahl2) {
		this.zahl2 = zahl2;
	}
	
}