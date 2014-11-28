package example.xml;

import java.io.FileOutputStream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Generator
{
	//http://www.jsp-develop.de/workshops/xmljdom/1

	public static void main(String[] args) throws Exception
	{

		//		 Erzeugung eines XML-Dokuments
		Document doc = new Document();

		//		 Erzeugung des Root-XML-Elements mit dem Namen books
		Element elRoot = new Element("books");
		//		 Erzeugung eines XML-Elements mit dem Namen book
		Element elBook = new Element("book");
		//		 Dem Element book geben wir noch ein Attribut mit dem Namen id und dem Wert 1
		elBook.setAttribute("id", "1");

		//		 Und noch ein XML-Element mit dem Namen title
		Element elTitle = new Element("title");
		//		 dem wir einen Inhalt geben
		elTitle.addContent(new Text("Java 2 Enterprise Edition"));
		//		 und hiermit an unser Element book haengen
		elBook.addContent(elTitle);

		//		 Was waere ein Buch ohne Autor, also erzeugen wir noch ein Autor-Element
		//		 und haengen die an unser Buch in verkuerzter Form
		elBook.addContent(new Element("author").addContent(new Text("Mark Wuttka")));

		//		 Jetzt muessen wir noch an unser Root-Element books haengen
		elRoot.addContent(elBook);

		//		 Damit das eine Buch nicht so alleine ist haengen wir noch ein zweites dran
		elBook = new Element("book");
		elBook.setAttribute("id", "2");
		elBook.addContent(new Element("title").addContent(new Text("JavaServer Pages and Servlets")));
		elBook.addContent(new Element("author").addContent(new Text("Mark Wuttka")));
		elRoot.addContent(elBook);

		//		 Und jetzt haengen wir noch das Root-Element an das Dokument,
		//		 haette man auch gleich nach dessen Erzeugung machen koennen
		doc.setRootElement(elRoot);

		//		 Damit das XML-Dokument schoen formattiert wird holen wir uns ein Format
		Format format = Format.getPrettyFormat();
		//		 und setzen das encoding, da in unseren Buechern auch Umlaute vorkommen koennten.
		//		 Mit format kann man z.B. auch die Einrueckung beeinflussen
		format.setEncoding("iso-8859-1");

		//		 Erzeugung eines XMLOutputters dem wir gleich unser Format mitgeben
		XMLOutputter xmlOut = new XMLOutputter(format);

		//		 Schreiben der XML-Datei ins Filesystem
		xmlOut.output(doc, new FileOutputStream("c:/test.xml"));

	}
}
