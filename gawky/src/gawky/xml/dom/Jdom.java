package gawky.xml.dom;

import gawky.file.Locator;
import gawky.xml.sax.SaxSample;

import java.io.File;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

public class Jdom {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		 
		SAXBuilder parser = new SAXBuilder();
		Document doc = parser.build(new File(Locator.findPath("", SaxSample.class) + "../data/example.xml"));

		System.out.println( doc.getRootElement().getChildren("record").size() );
		
		//xpath
		List nodeList = XPath.selectNodes(doc, "/fias2/record/address");
	
		Element el = (Element)nodeList.get(0);
		System.out.println( el.getParentElement().getParentElement().getAttributeValue("uniqueid") );
		System.out.println( nodeList.size() );
	}

}
