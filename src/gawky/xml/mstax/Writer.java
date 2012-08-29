package gawky.xml.mstax;

import gawky.file.Locator;

import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

public class Writer {

	public static void simpleTag(XMLStreamWriter writer, String tagname, String tagvalue) throws Exception
	{
		writer.writeStartElement(tagname);
		writer.writeCharacters(tagvalue);
		writer.writeEndElement();		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		//OutputStream out = new FileOutputStream(Locator.findPath("", Writer.class) + "../data/data.xml");
		OutputStream out = new FileOutputStream("c:/data.xml");
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = factory.createXMLStreamWriter(out);
		
		writer.writeStartDocument("ISO-8859-1", "1.0");
		writer.writeStartElement("greeting");
		writer.writeAttribute("id1", "g<>&\"'1");
		writer.writeAttribute("id2", "g<>&\"'1");
		writer.writeCharacters("Hell<o> & StAX");
		
		simpleTag(writer, "sum", "sumtext");
		
		writer.writeStartElement("sum");
		writer.writeCharacters("sumtext");
		writer.writeEndElement();
		writer.writeEndElement();
		
		writer.writeEndDocument();
		
		writer.flush();
		writer.close();
		out.close();
	}

}
