package gawky.data.datasource.generator;

import gawky.data.datasource.Datasource;
import gawky.data.datasource.listener.CellListener;

public class PdfTable extends AbstractTable 
{
	public String generate(Datasource ds)
	{
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
		
		return buffer.toString();
	}
}
