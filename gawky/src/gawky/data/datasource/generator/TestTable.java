package gawky.data.datasource.generator;

import gawky.data.datasource.ArrayListDatasource;
import gawky.data.datasource.Column;
import gawky.data.datasource.Datasource;
import gawky.data.datasource.listener.CellListener;

import java.util.ArrayList;

public class TestTable {

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
		
		
		PdfTable walker = new PdfTable();
		
		System.out.println( walker.generate(ds) );
		
	}
	
}
