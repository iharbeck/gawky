package example.message.xmlparser;

import gawky.message.parser.PatternParser;
import gawky.message.parser.XMLParser;
import gawky.message.part.Desc;
import gawky.message.part.DescXML;
import gawky.message.part.Part;

public class XmlParserBean extends Part 
{
	public Desc[] getDesc() {
		return new Desc[] {
			new DescXML(3, "feld1", "/d/customer/name"),
			new DescXML(4, "feld2", "/d/customer/name"),
			new DescXML(2, "feld3", "/d/customer/name"),
		};
	}
	
	public static void main(String[] args) throws Exception 
	{
		XMLParser    parser = new XMLParser();
//		PatternGenerator generator = new PatternGenerator();
		
		XmlParserBean bean = new XmlParserBean();
		
		String str = "<d>" +
				     "<customer>" +
				     "<name>minime</name>" +
				     "</customer>" +
				     "</d>";
		
		bean.parse(parser, str);

		System.out.println(bean.getFeld1());
//	    System.out.println(str + "\n" + bean.toString(generator));
//	    System.out.println(parser.getNext().length());

	    
//		str = "1235555A456CD122,33111,5533,2AA";
//	    bean.parse(parser, str);
//	    System.out.println(str + "\n" + bean.toString(generator));
//	    System.out.println(parser.getNext().length());
	}
	
	String feld1;
	String feld2;
	String feld3;
	
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
}