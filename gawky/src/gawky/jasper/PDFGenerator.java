package gawky.jasper;


import gawky.file.Locator;

import java.util.HashMap;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class PDFGenerator {

	/**
	 * @param args
	 */
	public static void generateFile(Object reportdata, int rows, String jasperFileName, String outputFileName) throws Exception
	{
		JasperPrint jasperPrint = null;
		
		if(jasperFileName.endsWith("jrxml")) 
		{
			// TODO Cache compiled report
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperFileName);    
			jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), 
					new JRBeanUtilDataSource(reportdata, rows)
			);
		} 
		else 
		{
			jasperPrint = JasperFillManager.fillReport(jasperFileName, new HashMap(), 
					new JRBeanUtilDataSource(reportdata, rows)
			);
		}
		  
		JasperExportManager.exportReportToPdfFile(jasperPrint, outputFileName);
	}

	
	public static void main(String[] args) throws Exception 
	{
		// Reporttemplate
		String path = Locator.findPath("") +  "../report/";

		String jasperFileName = path + "test.jasper";  // alternativ :jrxml wird automatisch von "PDFGenerator" compiliert
		String outputFileName = path + "test.pdf"; 
		
		
		PDFGenerator.generateFile(null, 10, jasperFileName, outputFileName);
		
		System.out.println("done!");
	}
}