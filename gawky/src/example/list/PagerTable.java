package example.list;

import gawky.database.DB;
import gawky.global.Option;
import gawky.list.datasource.ArrayListDatasource;
import gawky.list.datasource.Column;
import gawky.list.datasource.Datasource;
import gawky.list.datasource.PagedDatasource;
import gawky.list.datasource.ResultSetDatasource;
import gawky.list.generator.HtmlTable;
import gawky.list.listener.LinkListener;
import gawky.list.listener.NumberFormatListener;
import gawky.list.listener.RowListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PagerTable {

	public static void main(String[] args) throws Exception
	{
		Option.init();
		
		//ArrayList Source with String[]
		ArrayList rows = new ArrayList();
		
		rows.add(new String[] { "a1", "2", "3"});
		rows.add(new String[] { "a11", "22", "33"});
		rows.add(new String[] { "a111", "222", "333"});
		rows.add(new String[] { "a1111", "2222", "3333"});
		rows.add(new String[] { "a11111", "22222", "33333"});
		rows.add(new String[] { "a111111", "222222", "333333"});
		rows.add(new String[] { "b1", "2", "3"});
		rows.add(new String[] { "b11", "22", "33"});
		rows.add(new String[] { "b111", "222", "333"});
		rows.add(new String[] { "b1111", "2222", "3333"});
		rows.add(new String[] { "b11111", "22222", "33333"});
		rows.add(new String[] { "b111111", "222222", "333333"});
		rows.add(new String[] { "c1", "2", "3"});
		rows.add(new String[] { "c11", "22", "33"});
		rows.add(new String[] { "c111", "222", "333"});
		rows.add(new String[] { "c1111", "2222", "3333"});
		rows.add(new String[] { "c11111", "22222", "33333"});
		rows.add(new String[] { "c111111", "222222", "333333"});
		
		/* Style 1
			Column[] columns = new Column[] {
				new Column("CHEAD1", Column.TYPE_STRING).setWidth(100).setHidden(),
				new Column("CHEAD2", Column.TYPE_STRING, new LinkListener(0)).setWidth(300),
				new Column("CHEAD3", Column.TYPE_STRING).setWidth(200)
			};
			
			ArrayListDatasource ds = new ArrayListDatasource( rows, columns);
		*/
		
		ArrayListDatasource ds = new ArrayListDatasource( rows );
		 ds.addColumn(new Column("CHEAD1", Column.TYPE_STRING).setWidth(100).setHidden());
		 ds.addColumn(new Column("CHEAD2", Column.TYPE_STRING, new LinkListener(0, "url")).setWidth(300));
		 ds.addColumn(new Column("CHEAD3", Column.TYPE_STRING).setWidth(200));
	
		//Database Source
		Connection conn = DB.getConnection();
		Statement  stmt = conn.createStatement();
		ResultSet  rset = stmt.executeQuery("select * from kunde order by kunde_id");

/*		
		rset.last();
		rset.getRow();
		rset.beforeFirst();
		rset.absolute(row);
*/
		
		ResultSetDatasource rs = new ResultSetDatasource(rset, 2);
		 //rs.addColumn("kunde_id", new Column("ERSTER").setHidden());
		 rs.addColumn("name",     new Column("DER NAME"));
		 rs.addColumn("val",      new Column("val").setListener(new NumberFormatListener()));
		
		 RowListener t =  new RowListener() {
				public String process(Datasource ds, int row) {
					
					return "" + ds.getValue(0);
				} 
			};
			
		 rs.setRowListener( t );
		 
		int numberOfRows = 10;
		int page = 22;
		
		//HTMLgenerator
		HtmlTable hwalker = new HtmlTable();
		hwalker.setTablestyle("width:100%");
		PagedDatasource ds2 = new PagedDatasource(rs, numberOfRows, page);
		
		
		System.out.println("PAGES: " + page + "/" + ds2.getPages());
		
		System.out.println( hwalker.generate( ds2 ));
		
		System.out.println("previous: " + ds2.hasprev());
		System.out.println("next:     " + ds2.hasnext());
		rs.reset();

		System.exit(0);
	}
	
}
