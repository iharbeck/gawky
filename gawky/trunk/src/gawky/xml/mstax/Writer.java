package gawky.xml.mstax;

import gawky.file.Locator;

import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

public class Writer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		OutputStream out = new FileOutputStream(Locator.findPath("", Writer.class) + "../data/data.xml");
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = factory.createXMLStreamWriter(out);
		
		writer.writeStartDocument("ISO-8859-1", "1.0");
		writer.writeStartElement("greeting");
		writer.writeAttribute("id", "g<>&\"'1");
		writer.writeCharacters("Hell<o> & StAX");
		writer.writeEndDocument();
		
		writer.flush();
		writer.close();
		out.close();
	}

}
