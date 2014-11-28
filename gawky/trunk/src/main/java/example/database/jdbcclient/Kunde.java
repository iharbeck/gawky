package example.database.jdbcclient;

import gawky.database.jdbcclient.JdbcParameterMapper;
import gawky.database.jdbcclient.JdbcSelectMapper;
import gawky.database.jdbcclient.StatementDecorator;

import java.sql.ResultSet;

public class Kunde implements JdbcSelectMapper, JdbcParameterMapper
{
	public int id;
	public String name;

	@Override
	public Object selectmapper(ResultSet rset) throws Exception
	{
		Kunde k = new Kunde();
		k.id = rset.getInt("kunde_id");
		k.name = rset.getString("name");
		return k;
	}

	@Override
	public void parametermapper(StatementDecorator stmt) throws Exception
	{
		stmt.setString(name);
		stmt.setInt(id);
	}
}
