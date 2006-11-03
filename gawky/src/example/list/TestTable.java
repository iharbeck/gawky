package example.list;

import gawky.database.DB;
import gawky.global.Option;
import gawky.list.datasource.ArrayListDatasource;
import gawky.list.datasource.Column;
import gawky.list.datasource.ResultSetDatasource;
import gawky.list.generator.HtmlTable;
import gawky.list.generator.PdfTable;
import gawky.list.generator.XlsTable;
import gawky.list.listener.LinkListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TestTable {

	public static void main(String[] args) throws Exception
	{
		ArrayList rows = new ArrayList();
		
		rows.add(new String[] { "1", "2", "3"});
		rows.add(new String[] { "11", "22", "33"});
		rows.add(new String[] { "111", "222", "333"});
		rows.add(new String[] { "1111", "2222", "3333"});
		rows.add(new String[] { "11111", "22222", "33333"});
		rows.add(new String[] { "111111", "222222", "333333"});
		
		Column[] columns = new Column[] {
			new Column("CHEAD1", Column.TYPE_STRING).setWidth(100),
			new Column("CHEAD2", Column.TYPE_STRING, new LinkListener()).setWidth(300),
			new Column("CHEAD3", Column.TYPE_STRING).setWidth(200)
		};
		
		ArrayListDatasource ds = new ArrayListDatasource( rows, columns);
		
		
		Option.init();
		Connection conn = DB.getConnection();
		
		Statement stmt = conn.createStatement();
		
		ResultSet rset = stmt.executeQuery("select * from kunde");
		
		ResultSetDatasource rs = new ResultSetDatasource(rset);
		rs.addColumn("kunde_id", new Column("ERSTER").setHidden());
		rs.addColumn("name", new Column("DER NAME"));
		
		PdfTable pwalker = new PdfTable();
		pwalker.generate(rs, "PdfTable.pdf");

		ds.reset();
		
		HtmlTable hwalker = new HtmlTable();
		System.out.println( hwalker.generate(ds) );

		ds.reset();

		XlsTable xwalker = new XlsTable();
		xwalker.generate(ds, "XlsTable.xls");

		System.exit(0);
	}
	
}
