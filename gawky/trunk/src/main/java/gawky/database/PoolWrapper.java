package gawky.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;

public class PoolWrapper
{
	BoneCP pool;
	boolean active = true;

	public PoolWrapper(BoneCP pool)
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
