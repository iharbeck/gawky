package gawky.jasper;


import java.util.HashMap;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HTMLGenerator {

	private static Log log = LogFactory.getLog(HTMLGenerator.class);
	
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
		  
		JasperExportManager.exportReportToHtmlFile(jasperPrint, outputFileName);
		
		return jasperPrint;
	}
	
	public static void main(String[] args) throws Exception 
	{
		String jasperFileName = "D:/work/Jasper/report/mahnung.jrxml";  // alternativ :jrxml wird automatisch von "HTMLGenerator" compiliert
		String outputFileName = "d:/test.html"; 
		
		
		HTMLGenerator.generateFile(null, 0, jasperFileName, outputFileName);
		
		log.info("done!");
	}
}
