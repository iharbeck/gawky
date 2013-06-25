package gawky.xml.sax;

import gawky.file.Locator;

import java.io.IOException;

import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;


public class SaxSample implements  ContentHandler {

     
  public void setDocumentLocator(org.xml.sax.Locator locator) {
  }

  public void startDocument() throws SAXException {
  }

  public void endDocument() throws SAXException {
  }
  
  public void characters(char[] text, int start, int length) 
   throws SAXException {
    
    System.out.println(new String(text, start, length));
  }
  
  public void startElement(String namespaceURI, String localName,
	 String rawName, Attributes atts) throws SAXException {
  }
  
  public void endElement(String namespaceURI, String localName,
	 String rawName) throws SAXException {
  }
  
  public void processingInstruction(String target, String data)
   throws SAXException {
  }

  // methods that aren't necessary in this example
  public void startPrefixMapping(String prefix, String uri) 
   throws SAXException {
    // ignore; 
  }

  public void ignorableWhitespace(char[] text, int start, int length)
   throws SAXException {
  }
  
  public void endPrefixMapping(String prefix) throws SAXException {
    // ignore; 
  }

  public void skippedEntity(String name) throws SAXException {
    // ignore; 
  }   
  
  public void setDocumentLocator(Locator locator) {}


  public static void main(String[] args) {
     
    SAXParser parser = new SAXParser();
    SaxSample counter = new SaxSample();
    parser.setContentHandler(counter);
    
    try {
    	parser.parse(Locator.findPath("", SaxSample.class) + "../data/example_large.xml"); 
    }
    catch (SAXException e) {
    	System.err.println(e); 
 	}
    catch (IOException e) {
    	System.err.println(e); 
    }
  
  } 

}