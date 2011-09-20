package gawky.jasper;

import java.io.FileOutputStream;
import java.io.StringReader;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;

public class Html2PDF {

	public static void main(String[] args) {
		
		try {

			Document document = new Document(PageSize.A4);
			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("/tmp/testpdf.pdf"));
			document.open();
			//document.addAuthor("Author of the Doc");
			//document.addCreator("Creator of the Doc");
			//document.addSubject("Subject of the Doc");
			//document.addCreationDate();
			//document.addTitle("This is the title");

			//SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			//SAXmyHtmlHandler shh = new SAXmyHtmlHandler(document);

			HTMLWorker htmlWorker = new HTMLWorker(document);
			String str = "<html><head><title>titlu</title></head><body><table border=1><tr><td><p style='font-size: 10pt; font-family: Times'>" +
			"Cher Monsieur,</p><br><p align='justify' style='text-indent: 2em; font-size: 10pt; font-family: Times'>" +
			"asdasdasdsadas<br></p><p align='justify' style='text-indent: 2em; font-size: 10pt; font-family: Times'>" +
			"En vous remerciant &agrave; nouveau de la confiance que vous nous t&eacute;moignez,</p>" +
			"<br><p style='font-size: 10pt; font-family: Times'>Bien Cordialement,<br>" +
			"<br>ADMINISTRATEUR ADMINISTRATEUR<br>Ligne directe : 04 42 91 52 10<br>Acadomia&reg; – " +
			"37 BD Aristide Briand  – 13100 Aix en Provence  </p></td></tr></table></body></html>";
			
			str = "<table><tr><td>erster</td><td>zweiter <img src='img/img.png'></td></tr><tr><td>erster shsdkjfh ks skdf hkf hk</td><td>zweiter</td></tr></table>";
			
			htmlWorker.parse(new StringReader(str));

			document.close();

			} catch(Exception e) {
			e.printStackTrace();
			} 
		
	}
}
