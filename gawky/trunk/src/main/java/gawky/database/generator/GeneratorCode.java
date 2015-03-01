package gawky.database.generator;

import gawky.database.DB;
import gawky.global.Option;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GeneratorCode
{
	private static Log log = LogFactory.getLog(GeneratorCode.class);

	public static void main(String[] args) throws Exception
	{
		Option.init();
		System.out.println(GeneratorCode.generateDesc("kunde"));
	}

	public static String generateDesc(String table)
	{
		String descstr = "";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try
		{
			conn = DB.getConnection();

			stmt = conn.createStatement();
			rset = stmt.executeQuery("select * from " + table);

			ResultSetMetaData md = rset.getMetaData();
			int c = md.getColumnCount();

			descstr += "	// Record definition\n";
			descstr += "	public Desc[] getDesc()\n";
			descstr += "	{\n";
			descstr += "		//setDescID(0);  // Manual set ID\n";

			descstr += "		return new Desc[]  {\n";

			for(int i = 1; i <= c; i++)
			{
				System.out.println(i + " : " + md.getColumnType(i));
				if(md.getColumnType(i) == Types.NUMERIC || md.getColumnType(i) == Types.INTEGER)
				{
					descstr += "			new NColumn(\"" + md.getColumnLabel(i).toLowerCase() + "\"), //" + md.getPrecision(i) + "." + md.getScale(i) + "\n";
				}
				else
				{
					descstr += "			new Column(\"" + md.getColumnLabel(i).toLowerCase() + "\"), //" + md.getPrecision(i) + "." + md.getScale(i) + "\n";
				}
			}

			descstr += "		};\n";
			descstr += "	}\n";

			for(int i = 1; i <= c; i++)
			{
				descstr += "	private String " + md.getColumnLabel(i).toLowerCase() + ";\n";
			}

			descstr += "\n";

			for(int i = 1; i <= c; i++)
			{
				descstr += buildGetter(md.getColumnLabel(i).toLowerCase());
				descstr += "\n";
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.doClose(rset);
			DB.doClose(stmt);
			DB.doClose(conn);
		}
		return descstr;
	}

	public static String buildGetter(String name)
	{

		String uname = name.substring(0, 1).toUpperCase() + name.substring(1);

		String buf = "	public String get" + uname + "() {\n";
		buf += "		return " + name + ";\n";
		buf += "	}\n\n";

		buf += "	public void set" + uname + "(String " + name + ") {\n";
		buf += "		this." + name + " = " + name + ";\n";
		buf += "	}\n";

		return buf;
	}
}
