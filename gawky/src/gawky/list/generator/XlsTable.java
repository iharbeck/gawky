package gawky.list.generator;

import gawky.file.Locator;
import gawky.list.datasource.Datasource;
import gawky.list.listener.CellListener;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XlsTable extends AbstractTable 
{
	float fontsize = (float)8.5;
	float leading  = fontsize;
	
	public void generate(Datasource ds, String output) throws Exception
	{
		// create a new workbook
		HSSFWorkbook workBook = new HSSFWorkbook();

		//create a new worksheet
		HSSFSheet sheet = workBook.createSheet();

		HSSFFont headerfont = workBook.createFont();
		headerfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
   
		HSSFCellStyle headerStyle = workBook.createCellStyle();
   
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		headerStyle.setFont(headerfont);

		//create a formula for the total
		// totalValue.setCellType(HSSFCell.CELL_TYPE_FORMULA);
		// totalValue.setCellFormula("SUM(E6:E9)");


		HSSFRow headerRow = sheet.createRow((short) 0);
		
		for(int i=0; i < ds.getColumns(); i++)
		{
			if(ds.getWidth(i) > 0)
				sheet.setColumnWidth((short)i, (short)(ds.getWidth(i)*100));
			
			HSSFCell headerCell = headerRow.createCell((short) i);
		    headerCell.setCellStyle(headerStyle);
		    headerCell.setCellValue(ds.getHead(i));
		}
		
		for(int x=3; ds.nextRow(); x++)
		{
			HSSFRow row = sheet.createRow((short) x);
			
			for(int i=0; i < ds.getColumns(); i++)
			{
				CellListener handler = getListener(ds, i);
			
				HSSFCell cell = row.createCell((short) i);
				cell.setCellValue(handler.process(ds, i));
			}
		}
		
		

        FileOutputStream stream = new 
        FileOutputStream(Locator.findBinROOT() + "/" + output);
	       
        workBook.write(stream);

	}
}
