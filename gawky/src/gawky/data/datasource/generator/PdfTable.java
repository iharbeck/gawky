package gawky.data.datasource.generator;

import gawky.data.datasource.Datasource;
import gawky.data.datasource.listener.CellListener;
import gawky.file.Locator;

import java.awt.Color;
import java.io.FileOutputStream;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class PdfTable extends AbstractTable 
{
	
	Document document;
	
	float fontsize = (float)8.5;
	float leading  = fontsize;
	
	Font courierfont = FontFactory.getFont(FontFactory.COURIER, fontsize);

	float margin   = 25;
	
	public void generate(Datasource ds, String output) throws Exception
	{
		// create document
		//document = new Document(PageSize.A4.rotate());
		document = new Document(PageSize.A4);
		document.setMargins(margin, margin, margin, margin);
		
		// output path
        String path = Locator.findBinROOT() + "/" + output;

        PdfWriter.getInstance(document, new FileOutputStream(path));

        // open document
        document.open();

        
        Table table = new Table(ds.getColumns()); 
//        table.setBorderWidth(1); 
//        table.setBorderColor(new Color(0, 0, 255)); 
//        table.setCellpadding(5); 
//        table.setCellspacing(5); 
        
        table.setPadding(2);
        table.setSpacing(0);
        
        int headerwidths[] = new int[ds.getColumns()];
        
        
        
		for(int i=0; i < ds.getColumns(); i++)
		{
	        Cell cell = new Cell(ds.getHead(i)); 
	        cell.setHeader(true); 
	        cell.setBackgroundColor(Color.GRAY);
	        cell.setLeading(10);
	        table.addCell(cell); 
	        
	        headerwidths[i] = ds.getWidth(i);
		}

        table.setWidths(headerwidths);
        //table.setWidth(100);

        table.endHeaders();


		while(ds.nextRow())
		{
			for(int i=0; i < ds.getColumns(); i++)
			{
				CellListener handler = getListener(ds, i);
				
				table.addCell(handler.process(ds, i)); 
			}
		}

        document.add(table); 
		
		document.close();
	}
}
