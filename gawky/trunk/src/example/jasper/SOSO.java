package example.jasper;
import gawky.jasper.EchoDataSource;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


public class SOSO {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
        String file ="C:/work/DUNE/templates/t/";
	            
        JasperCompileManager.compileReportToFile(file + "main.jrxml", file + "main.jasper");
        JasperCompileManager.compileReportToFile(file + "DataSourceReport.jrxml", file + "DataSourceReport.jasper");
        
        Map parameters = new HashMap();
        parameters.put("DAT", new EchoDataSource());
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(file + "main.jasper", parameters,  new JREmptyDataSource(1));
        
        JasperExportManager.exportReportToPdfFile(jasperPrint, file + "Tutorials.pdf");
	            
		
//		JRDataSource daten = new JRBeanCollectionDataSource(list);
//
//		JasperReport subReport1 = JasperCompileManager.compileReport("C:/work/DUNE/templates/test.jrxml");
//
//		JasperPrint jasperPrint = JasperFillManager.fillReport(subReport1, new HashMap(), daten );
//		JasperViewer.viewReport(jasperPrint, false);
	}

}
