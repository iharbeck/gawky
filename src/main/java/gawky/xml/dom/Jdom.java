package gawky.xml.dom;

import gawky.file.Locator;
import gawky.xml.sax.SaxSample;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPath;

public class Jdom
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();

		SAXBuilder parser = new SAXBuilder();
		Document doc = parser.build(new File(Locator.findPath("", SaxSample.class) + "../data/example_large.xml"));

		System.out.println(doc.getRootElement().getChildren("record").size());

		//xpath
		List nodeList = XPath.selectNodes(doc, "/fias2/record/address");

		for(int i = 0; i < nodeList.size(); i++)
		{
			Element el = (Element)nodeList.get(i);
			System.out.println(el.getParentElement().getParentElement().getAttributeValue("uniqueid"));
			System.out.println(nodeList.size());
		}

		System.out.println("ms " + (System.currentTimeMillis() - start));
	}

}
