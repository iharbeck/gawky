package gawky.list.generator;

import gawky.list.datasource.Column;
import gawky.list.datasource.Datasource;
import gawky.list.listener.CellListener;
import gawky.list.listener.RowListener;

public class HtmlTable extends AbstractTable
{
	private String tableclass = null;
	private String tablestyle = null;

	public String generate(Datasource ds)
	{
		StringBuilder buffer = new StringBuilder();

		buffer.append("<TABLE");

		if(getTableclass() != null)
			buffer.append(" class='" + getTableclass() + "'");
		if(getTablestyle() != null)
			buffer.append(" style='" + getTablestyle() + "'");

		buffer.append(">\n");

		buffer.append("<TR>");

		for(int i=0; i < ds.getColumns(); i++)
		{
			if(ds.getWidth(i) == Column.HIDDEN)
				continue;

			buffer.append("<TH width='" + ds.getWidth(i) + "'>")
				  .append(ds.getHead(i))
				  .append("</TH>");
		}
		buffer.append("</TR>\n");

		String rollover = "onmouseover=\"this.className='rollover'\" onmouseout=\"this.className=''\"";

		RowListener rowlistener = getRowListener(ds);

		int x = 0;
		while(ds.nextRow())
		{
			buffer.append("<TR class='" + ((x%2==0) ? "secondline" : "firstline") + "'" + rollover + " " + rowlistener.process(ds, x) + ">");
			for(int i=0; i < ds.getColumns(); i++)
			{
				if(ds.getWidth(i) == Column.HIDDEN)
					continue;

				// handler für spezielle Cell formatierungen
				CellListener handler = getListener(ds, i);



				buffer
				  .append("<TD class='" + handler.getAttribute("class") + "'>")
				  .append(handler.process(ds, i))
				  .append("</TD>");

			}
			buffer.append("</TR>\n");
			x++;
		}

		buffer.append("</TABLE>\n");

		return buffer.toString();
	}


	public String getTableclass() {
		return tableclass;
	}

	public void setTableclass(String tableclass) {
		this.tableclass = tableclass;
	}

	public String getTablestyle() {
		return tablestyle;
	}

	public void setTablestyle(String tablestyle) {
		this.tablestyle = tablestyle;
	}
}
