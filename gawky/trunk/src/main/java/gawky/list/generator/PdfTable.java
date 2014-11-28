package gawky.list.generator;

import gawky.file.Locator;
import gawky.list.datasource.Column;
import gawky.list.datasource.Datasource;
import gawky.list.listener.CellListener;

import java.awt.Color;
import java.io.FileOutputStream;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class PdfTable extends AbstractTable
{
	Document document;

	float fontsize = 10;
	float leading = fontsize;

	Font courierfont = FontFactory.getFont(FontFactory.HELVETICA, fontsize);

	float margin = 25;

	Rectangle format = PageSize.A4;

	public void setFormatPortrait()
	{
		format = PageSize.A4;
	}

	public void setFormatLandscape()
	{
		format = PageSize.A4.rotate();
	}

	public void setMargin(float margin)
	{
		this.margin = margin;
	}

	public void generate(Datasource ds, String output) throws Exception
	{
		// create document
		document = new Document(format);

		document.setMargins(margin, margin, margin, margin);

		// output path
		String path = Locator.findBinROOT() + "/" + output;

		PdfWriter.getInstance(document, new FileOutputStream(path));

		// open document
		document.open();

		Table table = new Table(ds.getColumns() - ds.getColumnsHidden());
		//        table.setBorderWidth(1); 
		//        table.setBorderColor(new Color(0, 0, 255)); 

		table.setPadding(2);
		table.setSpacing(0);

		int headerwidths[] = new int[ds.getColumns() - ds.getColumnsHidden()];

		int x = 0;
		for(int i = 0; i < ds.getColumns(); i++)
		{
			if(ds.getWidth(i) == Column.HIDDEN)
			{
				continue;
			}

			Cell cell = new Cell(ds.getHead(i));
			cell.setHeader(true);
			cell.setBackgroundColor(Color.GRAY);
			cell.setLeading(leading);
			table.addCell(cell);

			headerwidths[x++] = ds.getWidth(i) == 0 ? 100 : ds.getWidth(i);
		}

		table.setWidths(headerwidths);
		table.setWidth(100);

		table.endHeaders();

		while(ds.nextRow())
		{
			for(int i = 0; i < ds.getColumns(); i++)
			{
				if(ds.getWidth(i) == Column.HIDDEN)
				{
					continue;
				}
				CellListener handler = getListener(ds, i);

				Cell cell = new Cell(handler.process(ds, i));
				cell.setLeading(leading);
				table.addCell(cell);
			}
		}

		document.add(table);

		document.close();
	}
}
