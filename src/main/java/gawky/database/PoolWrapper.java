package gawky.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

public class PoolWrapper
{
	HikariDataSource pool;
	boolean active = true;

	public PoolWrapper(HikariDataSource pool)
	{
		this.pool = pool;
	}

	public void enable()
	{
		active = true;
	}

	public void disable()
	{
		active = false;
	}

	public Connection getConnection() throws SQLException
	{
		if(!active)
		{
			throw new SQLException();
		}

		return pool.getConnection();
	}
}
