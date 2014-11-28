package gawky.database;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class DBN
{
	/**
	 * get String
	 */

	public static String getString(String sql)
	{
		try
		{
			return DB.getString(sql);
		}
		catch(Exception e)
		{
			return "";
		}
	}

	public static String getString(int pool, String sql, Object... params)
	{
		try
		{
			return DB.getString(pool, sql, params);
		}
		catch(Exception e)
		{
			return "";
		}
	}

	public static String getString(Connection conn, String sql, Object... params)
	{
		try
		{
			return DB.getString(conn, sql, params);
		}
		catch(Exception e)
		{
			return "";
		}
	}

	public static int execute(String sql) throws Exception
	{
		return execute(0, sql, (Object[])null);
	}

	public static int execute(String sql, Object... params)
	{
		try
		{
			return DB.execute(sql, params);
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	public static int execute(int pool, String sql, Object... params)
	{
		try
		{
			return DB.execute(pool, sql, params);
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	public static int execute(Connection conn, String sql, Object... params)
	{
		try
		{
			return DB.execute(conn, sql, params);
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	/**
	 * Row als Map
	 */

	public static Map<String, String> getRow(String sql, Object... params)
	{
		try
		{
			return DB.getRow(sql, params);
		}
		catch(Exception e)
		{
			return new HashMap<String, String>();
		}
	}

	public static Map<String, String> getRow(int pool, String sql, Object... params)
	{
		try
		{
			return DB.getRow(pool, sql, params);
		}
		catch(Exception e)
		{
			return new HashMap<String, String>();
		}
	}

	public static Map<String, String> getRow(Connection conn, String sql, Object... params)
	{
		try
		{
			return DB.getHash(conn, sql, params);
		}
		catch(Exception e)
		{
			return new HashMap<String, String>();
		}
	}

	/**
	 * Eine ArrayList von Map
	 */

	public static ArrayList<Map<String, String>> getRowList(String sql, Object... params)
	{
		try
		{
			return DB.getRowList(sql, params);
		}
		catch(Exception e)
		{
			return new ArrayList<Map<String, String>>();
		}
	}

	public static ArrayList<Map<String, String>> getRowList(int pool, String sql, Object... params)
	{
		try
		{
			return DB.getRowList(pool, sql, params);
		}
		catch(Exception e)
		{
			return new ArrayList<Map<String, String>>();
		}
	}

	public static ArrayList<Map<String, String>> getRowList(Connection conn, String sql, Object... params)
	{
		try
		{
			return DB.getRowList(conn, sql, params);
		}
		catch(Exception e)
		{
			return new ArrayList<Map<String, String>>();
		}
	}

	public static Map<String, String> getHash(String sql, Object... params)
	{
		try
		{
			return DB.getHash(sql, params);
		}
		catch(Exception e)
		{
			return new HashMap<String, String>();
		}
	}

	public static Map<String, String> getHash(int pool, String sql, Object... params)
	{
		try
		{
			return DB.getHash(pool, sql, params);
		}
		catch(Exception e)
		{
			return new HashMap<String, String>();
		}
	}

	public static Map<String, String> getHash(Connection conn, String sql, Object... params)
	{
		try
		{
			return DB.getHash(conn, sql, params);
		}
		catch(Exception e)
		{
			return new HashMap<String, String>();
		}
	}

	/**
	 * Firstvalue des SELECT als ArrayList
	 *
	 */

	public static ArrayList<String> getList(String sql, Object... params)
	{
		try
		{
			return DB.getList(sql, params);
		}
		catch(Exception e)
		{
			return new ArrayList<String>();
		}
	}

	public static ArrayList<String> getList(int pool, String sql, Object... params)
	{
		try
		{
			return DB.getList(pool, sql, params);
		}
		catch(Exception e)
		{
			return new ArrayList<String>();
		}
	}

	public static ArrayList<String> getList(Connection conn, String sql, Object... params)
	{
		try
		{
			return DB.getList(conn, sql, params);
		}
		catch(Exception e)
		{
			return new ArrayList<String>();
		}
	}

}
