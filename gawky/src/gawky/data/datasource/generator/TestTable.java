package gawky.data.datasource.generator;

import gawky.data.datasource.ArrayListDatasource;
import gawky.data.datasource.Column;
import gawky.data.datasource.ResultSetDatasource;
import gawky.data.datasource.listener.LinkListener;

import java.util.ArrayList;

public class TestTable {

	public static void main(String[] args) throws Exception
	{
		ArrayList rows = new ArrayList();
		
		rows.add(new String[] { "1erster", "1zweiter", "1dritter"});
		rows.add(new String[] { "2erster", "2zweiter", "2dritter"});
		rows.add(new String[] { "2erster", "3zweiter", "3dritter"});
		rows.add(new String[] { "1erster", "1zweiter", "1dritter"});
		rows.add(new String[] { "2erster", "2zweiter", "2dritter"});
		rows.add(new String[] { "2erster", "3zweiter", "3dritter"});
		
		Column[] columns = new Column[] {
			new Column("CHEAD1", Column.TYPE_STRING).setWidth(100),
			new Column("CHEAD2", Column.TYPE_STRING, new LinkListener()).setWidth(300),
			new Column("CHEAD3", Column.TYPE_STRING).setWidth(200)
		};
		
		ArrayListDatasource ds = new ArrayListDatasource( rows, columns);
		
		ResultSetDatasource rs = new ResultSetDatasource(null);
		rs.addColumn("FIRST", new Column("ERSTER"));
		
		PdfTable pwalker = new PdfTable();
		
		pwalker.generate(ds, "PdfTable.pdf");

		ds.reset();
		
		HtmlTable hwalker = new HtmlTable();
		
		System.out.println( hwalker.generate(ds) );

		ds.reset();

		XlsTable xwalker = new XlsTable();
		
		xwalker.generate(ds, "XlsTable.xls");

		
	}
	
}
