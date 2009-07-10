package example.xml;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import net.sourceforge.jwap.util.wbxml.WBXMLDecoder;
import net.sourceforge.jwap.util.wbxml.WBXMLEncoder;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;

/**
 * 
 * @author <a href="mailto:suvarna@witscale.com">Suvarna Kadam</a>
 */

public class WBXMLConverter 
{
	public static void main(String[] args) {
		try {
	
		//if (args[0].equalsIgnoreCase("-encode")) 
		
		{
//			String xml_filename = args[1];
//			String wbxml_filename = args[2];

			String xml_filename = "c:/Series_40_v72_VoIP_Settings_Example.xml";
			String wbxml_filename = "c:/Series_40_v72_VoIP_Settings_Example.xml.prov";

			
			System.out.println("Encoding XML file " + xml_filename + "...");

			try {
				System.setProperty(
					"org.xml.sax.driver",
					"org.apache.crimson.parser.XMLReaderImpl");
				FileInputStream xmlStream = new FileInputStream(xml_filename);
				FileOutputStream fo = new FileOutputStream(wbxml_filename);
				ByteArrayOutputStream tokens =
					(ByteArrayOutputStream) WBXMLEncoder.getInstance().encode(
						xmlStream);
				xmlStream.close();
				tokens.writeTo(fo);
				tokens.close();
				fo.flush();
				fo.close();
			} catch (Exception exp) {
				System.out.println(
					"Error while encoding XML file " + xml_filename + "!");
				exp.printStackTrace();
				printUsage();
			}
			return;
		}
//		if (args[0].equalsIgnoreCase("-decode")) {
//			String wbxml_filename = args[1];
//			String xml_filename = args[2];
//			System.out.println("Decoding WBXML file " + wbxml_filename + "...");
//			try {
//				FileInputStream tokenStream = new FileInputStream(wbxml_filename);
//				FileOutputStream xmlStream = new FileOutputStream(xml_filename);
//				Document document = WBXMLDecoder.getInstance().decode(tokenStream);
//				OutputFormat of = new OutputFormat(document);
//				XMLSerializer serial = new XMLSerializer(xmlStream,of);
//				serial.setOutputByteStream(xmlStream);
//				serial.setOutputFormat(of);
//				serial.asDOMSerializer();
//				serial.serialize(document);
//				xmlStream.close();  
//				tokenStream.close();
//			} catch (Exception exp) {
//				System.out.println(
//					"Error while decoding WBXML file " + wbxml_filename + "!\n\n");
//				exp.printStackTrace();
//				printUsage();
//			}
//			return;
//		}
		} catch (Exception exp) {
					System.out.println(
						"Error!You haven't entered the required arguments for using WBXML Parser");
					printUsage();
		}
	}
	
	private static void printUsage() {
		System.out.println(
			"\nUsage:- WBXMLConverter [-encode/-decode] inputfilename outputfilename");
		System.out.println("\n    Example:- $ WBXMLConverter -encode test.xml test.wbxml");
		System.out.println("    Example:- $ WBXMLConverter -decode test.wbxml test.xml");
	}
}
