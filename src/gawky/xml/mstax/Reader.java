package gawky.xml.mstax;

import gawky.file.Locator;
import gawky.xml.sax.SaxSample;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Reader {

	public static void main(String[] args) 
	{
	      try 
	      {
	        InputStream in;
	        
	        in = new FileInputStream(Locator.findPath("", SaxSample.class) + "../data/exampleatt.xml");
	        
	        XMLInputFactory factory = XMLInputFactory.newInstance();
	        XMLStreamReader parser = factory.createXMLStreamReader(in);
	          
	        for (int event = parser.next(); event != XMLStreamConstants.END_DOCUMENT; event = parser.next()) 
	        {
	          switch (event) {
	            case XMLStreamConstants.START_ELEMENT:
	            	 System.out.println("<" + parser.getLocalName() + " " + parser.getAttributeCount() + ">");
	            	 printAtt(parser);
	              break;
	            case XMLStreamConstants.END_ELEMENT:
	            	  parser.getLocalName();
	            	  System.out.println("END:" + parser.getLocalName());
	              break;
	            case XMLStreamConstants.CHARACTERS:
	            	  parser.getText();
	              break;
	          } 
	        } 
	        
	        parser.close();
	      }
	      catch (XMLStreamException ex) {
	         System.out.println(ex);
	      }
	      catch (IOException ex) {
	        System.out.println(ex);
	      }
	}
	
	public static void printAtt(XMLStreamReader parser) {
		System.out.println("  " + parser.getAttributeValue(null, "address_line_1"));
	}
}
