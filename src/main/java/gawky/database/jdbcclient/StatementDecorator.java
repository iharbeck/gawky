package gawky.database.jdbcclient;

import java.sql.Date;
import java.sql.PreparedStatement;

public class StatementDecorator
{
	PreparedStatement stmt;
	int param = 1;
	int maxparamcount = 0;

	public StatementDecorator(PreparedStatement stmt, int maxparamcount)
	{
		this.stmt = stmt;
		this.maxparamcount = maxparamcount;
	}

	public void setString(String string) throws Exception
	{
		if(param <= maxparamcount)
		{
			stmt.setString(param++, string);
		}
	}

	public void setDate(Date date) throws Exception
	{
		if(param <= maxparamcount)
		{
			stmt.setDate(param++, date);
		}
	}

	public void setDate(double d) throws Exception
	{
		if(param <= maxparamcount)
		{
			stmt.setDouble(param++, d);
		}
	}

	public void setInt(int i) throws Exception
	{
		if(param <= maxparamcount)
		{
			stmt.setInt(param++, i);
		}
	}

	public void setLong(long l) throws Exception
	{
		if(param <= maxparamcount)
		{
			stmt.setLong(param++, l);
		}
	}

	public PreparedStatement getStmt()
	{
		return stmt;
	}
}
