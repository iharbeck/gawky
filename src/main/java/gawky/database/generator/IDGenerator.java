package gawky.database.generator;

import gawky.database.part.Table;

import java.sql.Connection;

public abstract class IDGenerator
{
	abstract public String nextVal(Connection conn, Table table);

	public final static IDGenerator ID_SEQUENCE(String seq_name)
	{
		return new IDGeneratorSEQUENCE(seq_name);
	}

	public final static IDGenerator ID_AUTO()
	{
		return new IDGeneratorAUTO();
	}

	public final static IDGenerator ID_SQL(String sql)
	{
		return new IDGeneratorSQL(sql);
	}

	public final static IDGenerator ID_MAX()
	{
		return new IDGeneratorMAX();
	}

	public String lastVal(Connection conn, Table table) throws Exception
	{
		throw new Exception();
	}
}
