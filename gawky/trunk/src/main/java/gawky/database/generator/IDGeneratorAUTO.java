package gawky.database.generator;

import gawky.database.DB;
import gawky.database.part.Table;

import java.sql.Connection;

public class IDGeneratorAUTO extends IDGenerator
{
	@Override
	public String nextVal(Connection conn, Table table)
	{
		return null;
	}

	@Override
	public String lastVal(Connection conn, Table table) throws Exception
	{
		return DB.getString(conn, "select last_insert_id()");
	}
}
