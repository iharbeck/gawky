package gawky.data.datasource;

import java.util.ArrayList;

public class HtmlTable {

	static CellListener defaultListener = new CellListener() {
		public String process(Datasource ds, int column) { return (String)ds.getValue(column); };
		public String getAttribute(String name) {return ""; };
	};
	
	public static void main(String[] args) 
	{
		ArrayList rows = new ArrayList();
		
		rows.add(new String[] { "1erster", "1zweiter", "1dritter"});
		rows.add(new String[] { "2erster", "2zweiter", "2dritter"});
		rows.add(new String[] { "2erster", "3zweiter", "3dritter"});
		rows.add(new String[] { "1erster", "1zweiter", "1dritter"});
		rows.add(new String[] { "2erster", "2zweiter", "2dritter"});
		rows.add(new String[] { "2erster", "3zweiter", "3dritter"});
		
		
		CellListener handler = new CellListener() {
			public String getAttribute(String name) {
				if(name.equals("class"))
					return "BOLDSTYLE";
				else
					return "";
			}
			public String process(Datasource ds, int column) {
				return "<a href=''>" + ds.getValue(column) + "</a>";
			}
		};
		
		ArrayListDatasource ds = new ArrayListDatasource(
				rows, 
				new Column[] {
					new Column("HEAD1", Column.TYPE_STRING),
					new Column("HEAD2", Column.TYPE_STRING, handler),
					new Column("HEAD3", Column.TYPE_STRING)
				}
		);
		
		
		
		HtmlTable walker = new HtmlTable();
		
		System.out.println( walker.generate(ds) );
		
	}
	
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

	CellListener getListener(Datasource ds, int column) 
	{
		if(ds.getListener(column) == null)
			return defaultListener;
		
		return ds.getListener(column);
	}
}
