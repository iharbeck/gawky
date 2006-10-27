package gawky.data.datasource.generator;

import gawky.data.datasource.Datasource;
import gawky.data.datasource.listener.CellListener;
import gawky.file.Locator;

import java.awt.Color;
import java.io.FileOutputStream;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
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
		document = new Document(PageSize.A4.rotate());
		document.setMargins(margin, margin, margin, margin);
		
		// output path
        String path = Locator.findBinROOT() + "/" + output;

        PdfWriter.getInstance(document, new FileOutputStream(path));

        // open document
        document.open();

        
        Table table = new Table(3); 
        table.setBorderWidth(1); 
        table.setBorderColor(new Color(0, 0, 255)); 
        table.setCellpadding(5); 
        table.setCellspacing(5); 
        Cell cell = new Cell("header"); 
        cell.setHeader(true); 
        cell.setColspan(3); 
        table.addCell(cell); 
       cell = new Cell("example cell with colspan 1 and rowspan 2"); 
        cell.setRowspan(2); 
        cell.setBorderColor(new Color(255, 0, 0)); 
        table.addCell(cell); 
        table.addCell("1.1"); 
       table.addCell("2.1"); 
        table.addCell("1.2"); 
        table.addCell("2.2"); 
        table.addCell("cell test1"); 
        cell = new Cell("big cell"); 
        cell.setRowspan(2); 
        cell.setColspan(2); 
        cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0)); 
        table.addCell(cell); 
        table.addCell("cell test2"); 
        document.add(table); 

        
        
        
        
        Table datatable = new Table(10);
        
        datatable.setPadding(4);
        datatable.setSpacing(0);
        //datatable.setBorder(Rectangle.NO_BORDER);
        int headerwidths[] = {10, 24, 12, 12, 7, 7, 7, 7, 7, 7};
        datatable.setWidths(headerwidths);
        datatable.setWidth(100);
        
        // the first cell spans 10 columns
        cell = new Cell(new Phrase("Administration -System Users Report", FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setLeading(30);
        cell.setColspan(10);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        datatable.addCell(cell);
        
        // These cells span 2 rows
        datatable.setDefaultCellBorderWidth(2);
        datatable.setDefaultHorizontalAlignment(1);
        datatable.setDefaultRowspan(2);
        datatable.addCell("User Id");
        datatable.addCell(new Phrase("Name", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
        datatable.addCell("Company");
        datatable.addCell("Department");
        
        // This cell spans the remaining 6 columns in 1 row
        datatable.setDefaultRowspan(1);
        datatable.setDefaultColspan(6);
        datatable.addCell("Permissions");
        
        // These cells span 1 row and 1 column
        datatable.setDefaultColspan(1);
        datatable.addCell("Admin");
        datatable.addCell("Data");
        datatable.addCell("Expl");
        datatable.addCell("Prod");
        datatable.addCell("Proj");
        datatable.addCell("Online");
        
        // this is the end of the table header
        datatable.endHeaders();
        
        datatable.setDefaultCellBorderWidth(1);
        datatable.setDefaultRowspan(1);
        
        for (int i = 1; i < 30; i++) {
            
            datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
            
            datatable.addCell("myUserId");
            datatable.addCell("Somebody with a very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very, very long long name");
            datatable.addCell("No Name Company");
            datatable.addCell("D" + i);
            
            datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
            datatable.addCell("No");
            datatable.addCell("Yes");
            datatable.addCell("No");
            datatable.addCell("Yes");
            datatable.addCell("No");
            datatable.addCell("Yes");
            
        }

        
        document.add(datatable);

        
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("<TABLE>\n");
		buffer.append("<TR>");
		
		for(int i=0; i < ds.getColumns(); i++)
		{
			buffer.append("<TH>")
				  .append(ds.getHead(i))
				  .append("</TH>");
		}
		buffer.append("</TR>\n");
		
		while(ds.nextRow())
		{
			buffer.append("<TR>");
			for(int i=0; i < ds.getColumns(); i++)
			{
				CellListener handler = getListener(ds, i);
				
				buffer.append("<TD class='" + handler.getAttribute("class") + "'>")
				  .append(handler.process(ds, i))
				  .append("</TD>");

			}
			buffer.append("</TR>\n");
		}
		
		buffer.append("</TABLE>\n");
		
		
		document.add(new Paragraph(leading, " ", courierfont));

		
		document.close();
	}
}
