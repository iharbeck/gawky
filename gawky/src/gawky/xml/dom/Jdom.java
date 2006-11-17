package gawky.xml.dom;

import java.io.File;
import java.util.List;

import gawky.file.Locator;
import gawky.xml.sax.SaxSample;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

public class Jdom {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		 
		SAXBuilder parser = new SAXBuilder();
		Document doc = parser.build(new File(Locator.findPath("", SaxSample.class) + "../example.xml"));

		System.out.println( doc.getRootElement().getChildren("record").size() );
		
		//xpath
		List nodeList = XPath.selectNodes(doc, "/fias2/record/address");
	
		System.out.println( nodeList.size() );
	}

}
