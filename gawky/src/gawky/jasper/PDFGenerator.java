package gawky.jasper;


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

}
