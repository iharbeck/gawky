package example.list;

import gawky.database.DB;
import gawky.global.Option;
import gawky.list.datasource.ArrayListDatasource;
import gawky.list.datasource.Column;
import gawky.list.generator.HtmlTable;
import gawky.list.generator.PdfTable;
import gawky.list.generator.XlsTable;
import gawky.list.listener.LinkListener;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class TestTable {

	public static void main(String[] args) throws Exception
	{
		Option.init();
		
		//ArrayList Source with String[]
		ArrayList<String[]> rows = new ArrayList<String[]>();
		
		rows.add(new String[] { "1", "2", "3"});
		rows.add(new String[] { "11", "22", "33"});
		rows.add(new String[] { "111", "222", "333"});
		rows.add(new String[] { "1111", "2222", "3333"});
		rows.add(new String[] { "11111", "22222", "33333"});
		rows.add(new String[] { "111111", "222222", "333333"});
		
		Column[] columns = new Column[] {
			new Column("CHEAD1", Column.TYPE_STRING).setWidth(100),
			new Column("CHEAD2", Column.TYPE_STRING, new LinkListener(0, "test")).setWidth(300),
			new Column("CHEAD3", Column.TYPE_STRING).setWidth(200)
		};
		
		ArrayListDatasource ds = new ArrayListDatasource( rows, columns);
		
		//Database Source
		Connection conn = DB.getConnection();
		Statement  stmt = conn.createStatement();
		//ResultSet  rset = stmt.executeQuery("select * from kunde");
		
		//ResultSetDatasource rs = new ResultSetDatasource(rset);
		//rs.addColumn("kunde_id", new Column("ERSTER").setHidden());
		//rs.addColumn("name",     new Column("DER NAME"));
		
		
		//PDFgenerator
		PdfTable pwalker = new PdfTable();
		pwalker.generate(ds, "PdfARTable.pdf");
		//pwalker.generate(rs, "PdfDBTable.pdf");
		ds.reset();
		
		//HTMLgenerator
		HtmlTable hwalker = new HtmlTable();
		hwalker.setClass("class");
		hwalker.setStyle("style");
		
		System.out.println( hwalker.generate(ds) );
		//System.out.println( hwalker.generate(rs) );
		ds.reset();

		//XLSgenerator
		XlsTable xwalker = new XlsTable();
		xwalker.generate(ds, "XlsARTable.xls");
		//xwalker.generate(rs, "XlsDBTable.xls");

		System.exit(0);
	}
	
}
