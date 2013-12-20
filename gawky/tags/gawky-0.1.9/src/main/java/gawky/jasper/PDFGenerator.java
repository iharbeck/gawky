package gawky.jasper;


import gawky.file.Locator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.pdf.PdfReader;

public class PDFGenerator {

	private static Log log = LogFactory.getLog(PDFGenerator.class);
	
	static HashMap compiledreportcache = new HashMap();
	
	public static JasperPrint generateFile(Object reportdata, int rows, String jasperFileName, String outputFileName) throws Exception
	{
		return generateFile(reportdata, rows, jasperFileName, outputFileName, new HashMap());
	}
	
	public static JasperPrint generateFile(Object reportdata, int rows, String jasperFileName, String outputFileName, HashMap map) throws Exception
	{
		JasperPrint jasperPrint = null;
		
		if(jasperFileName.endsWith("jrxml")) 
		{
			JasperReport jasperReport = (JasperReport)compiledreportcache.get(jasperFileName);
			
			if(jasperReport  == null)
			{
				jasperReport = JasperCompileManager.compileReport(jasperFileName);    
				compiledreportcache.put(jasperFileName, jasperReport);
			}
			jasperPrint = JasperFillManager.fillReport(jasperReport, map, 
				new JRBeanUtilDataSource(reportdata, rows)
			);
		} 
		else 
		{
			jasperPrint = JasperFillManager.fillReport(jasperFileName, map, 
					new JRBeanUtilDataSource(reportdata, rows)
			);
		}
		  
		JasperExportManager.exportReportToPdfFile(jasperPrint, outputFileName);
		
		return jasperPrint;
	}
	
	public static JasperPrint generateStream(Object reportdata, int rows, String jasperFileName, OutputStream outputStream, HashMap map) throws Exception
	{
		JasperPrint jasperPrint = null;
		
		if(jasperFileName.endsWith("jrxml")) 
		{
			JasperReport jasperReport = (JasperReport)compiledreportcache.get(jasperFileName);
			
			if(jasperReport  == null)
			{
				jasperReport = JasperCompileManager.compileReport(jasperFileName);    
				compiledreportcache.put(jasperFileName, jasperReport);
			}
			jasperPrint = JasperFillManager.fillReport(jasperReport, map, 
				new JRBeanUtilDataSource(reportdata, rows)
			);
		} 
		else 
		{
			jasperPrint = JasperFillManager.fillReport(jasperFileName, map, 
					new JRBeanUtilDataSource(reportdata, rows)
			);
		}
		  
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		
		return jasperPrint;
	}
	
	
	public static int getNumberOfPages(InputStream instream)
	{
        try
        {
            PdfReader reader = new PdfReader(instream);

            return reader.getNumberOfPages();

        }
        catch (Exception de) {
            de.printStackTrace();
        }
        
        return 0;
    }

	public static int generatePagenumber(Object reportdata, int rows, String jasperFileName) throws Exception
	{
		return generatePagenumber(reportdata, rows, jasperFileName, new HashMap());
	}
	
	public static int generatePagenumber(Object reportdata, int rows, String jasperFileName, HashMap map) throws Exception
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		PDFGenerator.generateStream(reportdata, rows, jasperFileName, out, map);
		
		return getNumberOfPages(new ByteArrayInputStream(out.toByteArray()));
	}
	
	public static void main(String[] args) throws Exception 
	{
		// Reporttemplate
		String path = Locator.findPath("") +  "../report/";

		String jasperFileName = path + "text.jrxml";  // alternativ :jrxml wird automatisch von "PDFGenerator" compiliert
		String outputFileName = path + "test.pdf"; 
		
		
		PDFGenerator.generateFile(null, 10, jasperFileName, outputFileName);
		
		
		
		
		
		log.info("done!");
	}
}
