package gawky.jasper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

class PDFFilter implements FilenameFilter
{
    public boolean accept(File dir, String name) {
        return (name.endsWith(".pdf"));
    }
}

public class Concat
{
	private static final Log log = LogFactory.getLog(Concat.class);


	public static void concatFiles(String path, String target)
	{
		// Get list of *.pdf files in source folder
		String[] files = new File(path).list(new PDFFilter());

		try {
            int x = 0;

            Document document = null;
            PdfCopy  writer = null;

            while (x < files.length-1)
            {
                PdfReader reader = new PdfReader(path + files[x]);
                reader.consolidateNamedDestinations();

                // we retrieve the total number of pages
                int n = reader.getNumberOfPages();

                if(log.isDebugEnabled())
                	log.debug("There are " + n + " pages in " + files[x]);

                if (x == 0) // create document writer
                {
                	document = new Document(reader.getPageSizeWithRotation(1));
                	writer = new PdfCopy(document, new FileOutputStream(target));
                    document.open();
                }

                // attach pages
                PdfImportedPage page;
                for (int i = 1; i <= n; i++) {
                    page = writer.getImportedPage(reader, i);
                    writer.addPage(page);
                    log.debug("Processed page " + i);
                }
                writer.freeReader(reader);
                x++;
            }


            document.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
	}

	public static Image getPDF(PdfWriter pdfWriter, String filename) throws Exception {

		PdfReader pdfCoverReader = new PdfReader(filename);

		PdfImportedPage pip = pdfWriter.getImportedPage(pdfCoverReader, 1);

		return Image.getInstance(pip);
	}

	public static void addBackground(String infile, String bgfile, String outfile)
	{
        try
        {
            PdfReader reader = new PdfReader(infile);

            int n = reader.getNumberOfPages();

            // create a stamper that will copy the document to a new file
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outfile));

            // adding content to each page
            int i = 0;
            PdfContentByte under;

            Image img = getPDF(stamp.getWriter(), bgfile);

            img.setAbsolutePosition(0, 0);

            while (i < n) {
                i++;

                under = stamp.getUnderContent(i);
                under.addImage(img);
            }

            stamp.close();
        }
        catch (Exception de) {
            de.printStackTrace();
        }
    }




}
