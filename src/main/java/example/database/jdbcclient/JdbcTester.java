package example.database.jdbcclient;

import gawky.database.DB;
import gawky.database.jdbcclient.JdbcClient;
import gawky.database.jdbcclient.JdbcParameterMapper;
import gawky.database.jdbcclient.JdbcSelectMapper;
import gawky.database.jdbcclient.StatementDecorator;
import gawky.global.Option;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JdbcTester
{
	public static JdbcSelectMapper ksmap = new JdbcSelectMapper()
	{
		@Override
		public Object selectmapper(ResultSet rset) throws Exception
		{
			Kunde k = new Kunde();
			return k;
		}
	};

	public static JdbcParameterMapper kpmap = new JdbcParameterMapper()
	{
		@Override
		public void parametermapper(StatementDecorator stmt) throws Exception
		{

		}
	};

	public static void main(String[] args) throws Exception
	{
		Option.init();

		Connection conn = DB.getConnection();

		JdbcClient jdbcclient = new JdbcClient(conn);

		String sql = null;
		Kunde k = new Kunde();

		// Objekt aus DB holen
		// Optional params for ID Selection
		sql = "SELECT * FROM kunde where kunde_id = ?";
		k = (Kunde)jdbcclient.select(sql, k, new Object[] { "30" });

		// Liste von Objekten ermitteln
		sql = "SELECT * FROM kunde where kunde_id > ?";
		ArrayList list = jdbcclient.list(sql, k, new Object[] { "1" });

		// Objekt "inserten" ! last param from mapper is ignored
		sql = "INSERT into kunde (name) VALUES (?)";
		jdbcclient.execute(sql, k);

		sql = "INSERT into kunde (name)";
		sql = JdbcClient.completeInsert(sql);
		jdbcclient.execute(sql, new Object[] { "hanshans" });

		// Objekt updaten
		sql = "UPDATE kunde SET name=? where kunde_id = ?";
		jdbcclient.execute(sql, k);

		// Run Execute mit optional parameters
		sql = "DELETE FROM kunde where kunde_id=?";
		jdbcclient.execute(sql, new Object[] { "31" });

		sql = "SELECT * FROM kunde where kunde_id > ?";
		ArrayList list2 = jdbcclient.list(sql, ksmap, new Object[] { "30" });

		DB.doClose(conn);

		System.out.println(k.name);
		System.out.println("Elements in List : " + list.size());
		System.out.println("Elements in List2: " + list2.size());

		System.exit(0);
	}
}
