package example.xml;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.helpers.AttributesImpl;

public class SlimRequest {

	public static void main(String[] args) throws Exception {

		StringBuilder buf = new StringBuilder();
		//buf.replace(10, 15, "tomtom");
		
		StreamResult streamResult = new StreamResult(System.out);
		SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory
				.newInstance();
		// SAX2.0 ContentHandler.
		TransformerHandler hd = tf.newTransformerHandler();
		Transformer serializer = hd.getTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "users.dtd");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		hd.setResult(streamResult);
		hd.startDocument();
		AttributesImpl atts = new AttributesImpl();
		// USERS tag.
		hd.startElement("", "", "USERS", atts);
		// USER tags.
		String[] id = { "PWD122", "MX787", "A4Q45" };
		String[] type = { "customer", "manager", "employee" };
		String[] desc = { "Tim@Home", "Jack&Moud", "John D'oé" };
		for (int i = 0; i < id.length; i++) {
			atts.clear();
			atts.addAttribute("", "", "ID", "CDATA", id[i]);
			atts.addAttribute("", "", "TYPE", "CDATA", type[i]);
			hd.startElement("", "", "USER", atts);
			hd.characters(desc[i].toCharArray(), 0, desc[i].length());
			hd.endElement("", "", "USER");
		}
		hd.endElement("", "", "USERS");
		hd.endDocument();
	}
}
