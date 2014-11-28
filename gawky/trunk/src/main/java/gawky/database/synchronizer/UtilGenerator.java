package gawky.database.synchronizer;

import gawky.database.DB;
import gawky.global.Option;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class UtilGenerator
{

	public static void main(String[] args) throws Exception
	{
		Option.init();

		String tablename = "kunde";

		Connection conn = DB.getConnection();

		Statement stmt = conn.createStatement();

		ResultSet rset = stmt.executeQuery("select * from " + tablename);

		ResultSetMetaData md = rset.getMetaData();

		for(int i = 1; i <= md.getColumnCount(); i++)
		{
			System.out.println(md.getColumnName(i));
		}

	}
}
