package gawky.xml.hybrid.alternative;

import gawky.xml.hybrid.XMLStore;
import gawky.xml.hybrid.XMLTagHandler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.xmlpull.v1.XmlPullParser;

public class XMLReaderStax
{
	HashMap<String, XMLTagHandler> hshandler = new HashMap<String, XMLTagHandler>();

	public void registerTagHandler(XMLTagHandler handler)
	{
		hshandler.put(handler.getTagName(), handler);
	}

	public void process(String filename, String encoding) throws Exception
	{
		InputStream in = new FileInputStream(filename);

		process(in, encoding);

		in.close();
	}

	public void process(InputStream in, String encoding) throws Exception
	{
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(in, encoding);

		XMLStore store = new XMLStore();

		for(int event = parser.next(); event != XMLStreamConstants.END_DOCUMENT; event = parser.next())
		{
			if(event == XMLStreamConstants.START_ELEMENT)
			{
				String tagname = parser.getLocalName();
				XMLTagHandler handler = hshandler.get(tagname);

				if(handler != null)
				{
					handleRecord(store, parser, tagname, handler.buildDOM());

					handler.handle(store);
				}
			}
		}

		parser.close();
	}

	public XMLTagHandler getHandler(String tagname)
	{
		return hshandler.get(tagname);
	}

	public void processAttributes(XMLStore store, XMLStreamReader parser)
	{
		for(int i = 0; i < parser.getAttributeCount(); i++)
		{
			store.put(store.level + "@" + parser.getAttributeLocalName(i), parser.getAttributeValue(i));
		}
	}

	public void handleRecord(XMLStore store, XMLStreamReader parser, String tagname, boolean buildDOM) throws Exception
	{
		store.level = parser.getLocalName();

		processAttributes(store, parser);

		if(!buildDOM)
			return;
		
		for(int event = parser.next(); event != XMLStreamConstants.END_DOCUMENT; event = parser.next())
		{
			switch(event)
			{
				case XMLStreamConstants.START_ELEMENT:
					store.level += "/" + parser.getLocalName();
					processAttributes(store, parser);
					break;
				case XMLStreamConstants.END_ELEMENT:
					if(parser.getLocalName().equals(tagname))
						return;
					else
						store.level = store.level.substring(0, store.level.lastIndexOf('/'));
					break;
				case XMLStreamConstants.CHARACTERS:
					store.put(store.level, parser.getText());
					break;
			}
		}

		return;
	}

	public static void handleGeneral(XMLStreamReader parser) throws Exception
	{
		System.out.println("  " + parser.getAttributeValue(null, "address_line_1"));

		for(int event = parser.next(); event != XMLStreamConstants.END_DOCUMENT; event = parser.next())
		{
			switch(event)
			{
				case XMLStreamConstants.START_ELEMENT:
					System.out.println("<" + parser.getLocalName() + " " + parser.getAttributeCount() + ">");
					break;
				case XMLStreamConstants.END_ELEMENT:
					if(parser.getLocalName().equals("record"))
						System.out.println("END!!!!!!!!!!!:" + parser.getLocalName());
					break;
				case XMLStreamConstants.CHARACTERS:
					parser.getText();
					break;
			}
		}

	}
}
