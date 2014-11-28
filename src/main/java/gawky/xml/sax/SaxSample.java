package gawky.xml.sax;

import gawky.file.Locator;

import java.io.IOException;

import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class SaxSample implements ContentHandler
{

	@Override
	public void setDocumentLocator(org.xml.sax.Locator locator)
	{
	}

	@Override
	public void startDocument() throws SAXException
	{
	}

	@Override
	public void endDocument() throws SAXException
	{
	}

	@Override
	public void characters(char[] text, int start, int length)
	        throws SAXException
	{

		System.out.println(new String(text, start, length));
	}

	@Override
	public void startElement(String namespaceURI, String localName,
	        String rawName, Attributes atts) throws SAXException
	{
	}

	@Override
	public void endElement(String namespaceURI, String localName,
	        String rawName) throws SAXException
	{
	}

	@Override
	public void processingInstruction(String target, String data)
	        throws SAXException
	{
	}

	// methods that aren't necessary in this example
	@Override
	public void startPrefixMapping(String prefix, String uri)
	        throws SAXException
	{
		// ignore; 
	}

	@Override
	public void ignorableWhitespace(char[] text, int start, int length)
	        throws SAXException
	{
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException
	{
		// ignore; 
	}

	@Override
	public void skippedEntity(String name) throws SAXException
	{
		// ignore; 
	}

	public void setDocumentLocator(Locator locator)
	{
	}

	public static void main(String[] args)
	{

		SAXParser parser = new SAXParser();
		SaxSample counter = new SaxSample();
		parser.setContentHandler(counter);

		try
		{
			parser.parse(Locator.findPath("", SaxSample.class) + "../data/example_large.xml");
		}
		catch(SAXException e)
		{
			System.err.println(e);
		}
		catch(IOException e)
		{
			System.err.println(e);
		}

	}

}