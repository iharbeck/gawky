package gawky.database;

import gawky.database.dbpool.AConnectionDriver;
import gawky.global.Option;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Ingo Harbeck
 * 
 */

public class DB
{
	private static Log log = LogFactory.getLog(DB.class);
	private static int INITIALCAP = 2000;

	public static void init()
	{
		int dbc = Option.getProperties("db_${staging}.driver").length;

		log.info("Datenbanken: " + dbc + " (" + Option.getProperty("staging") + ")");

		for(int i = 0; i < dbc; i++)
		{
			String dburl = Option.getProperty("db_${staging}(" + i + ").url");
			String dbpass = Option.getProperty("db_${staging}(" + i + ").password");
			String dbuser = Option.getProperty("db_${staging}(" + i + ").user");
			String dbdriver = Option.getProperty("db_${staging}(" + i + ").driver");
			String dbalias = Option.getProperty("db_${staging}(" + i + ").alias", null);
			String[] dbproperties = Option.getProperties("db_${staging}(" + i + ").property");

			Properties props = new Properties();

			if(dbproperties != null)
			{

				for(int x = 0; x < dbproperties.length; x++)
				{
					String[] val = dbproperties[x].split("=");
					props.put(val[0], val[1]);
				}
			}

			log.info("Register: " + dburl);

			try
			{
				if(dbalias == null)
					new gawky.database.dbpool.AConnectionDriver(dbdriver, dburl, dbuser, dbpass, "pool" + i, 5000000, props);
				else
					new gawky.database.dbpool.AConnectionDriver(dbdriver, dburl, dbuser, dbpass, dbalias, 5000000, props);
			}
			catch(Exception e)
			{
				log.error("Pooleinrichtung: " + e.getMessage());
			}
		}
	}

	// Verbindung aus Connectionpool holen
	static public Connection getConnection() throws SQLException
	{
		Connection conn = DriverManager.getConnection(AConnectionDriver.URL_PREFIX + "pool0");
		log.info("get connection [" + conn + "]");
		return conn;
	}

	static public Connection getConnection(int number) throws SQLException
	{
		Connection conn = DriverManager.getConnection(AConnectionDriver.URL_PREFIX + "pool" + number);
		log.info("get connection [" + conn + "]");
		return conn;
	}

	static public Connection getConnection(String alias) throws SQLException
	{
		log.info("get connection");
		return DriverManager.getConnection(AConnectionDriver.URL_PREFIX + alias);
	}

	static public Driver getDriver() throws SQLException
	{
		return DriverManager.getDriver(AConnectionDriver.URL_PREFIX + "pool0");
	}

	static public Driver getDriver(int number) throws SQLException
	{
		return DriverManager.getDriver(AConnectionDriver.URL_PREFIX + "pool" + number);
	}

	static public Driver getDriver(String alias) throws SQLException
	{
		return DriverManager.getDriver(AConnectionDriver.URL_PREFIX + alias);
	}

	public static boolean isDBAvailable()
	{
		return isDBAvailable(0);
	}

	public static boolean isDBAvailable(int pool)
	{
		Connection conn = null;
		try
		{
			conn = DB.getConnection(pool);
			conn.getMetaData();
		}
		catch(Exception e)
		{
			return false;
		}
		finally
		{
			doClose(conn);
		}
		return true;
	}

	private static final String secString(String val)
	{
		if(val != null)
			val = val.trim();
		else
			val = "";
		return (String)val;
	}

	public static String getString(String sql) throws Exception
	{
		return getString(sql, (Object[])null);
	}

	public static String getString(String sql, Object... params) throws Exception
	{
		return getString(0, sql, params);
	}

	public static String getString(int pool, String sql) throws Exception
	{
		return getString(pool, sql, (Object[])null);
	}

	public static String getString(int pool, String sql, Object... params) throws Exception
	{
		Connection conn = null;

		try
		{
			conn = DB.getConnection(pool);
			return getString(conn, sql, params);
		}
		finally
		{
			doClose(conn);
		}
	}

	public static String getString(Connection conn, String sql) throws Exception
	{
		return getString(conn, sql, (Object[])null);
	}

	public static String getString(Connection conn, String sql, Object... params) throws Exception
	{
		ResultSet rset = null;
		PreparedStatement stmt_select = null;

		try
		{
			stmt_select = conn.prepareStatement(sql);

			fillParams(stmt_select, params);

			rset = stmt_select.executeQuery();
			rset.next();
			return rset.getString(1);
		}
		finally
		{
			doClose(stmt_select);
			doClose(rset);
		}
	}

	public static int execute(String sql) throws Exception
	{
		return execute(0, sql, (Object[])null);
	}

	public static int execute(String sql, Object... params) throws Exception
	{
		return execute(0, sql, params);
	}

	public static int execute(int pool, String sql) throws Exception
	{
		return execute(pool, sql, (Object[])null);
	}

	public static int execute(int pool, String sql, Object... params) throws Exception
	{
		Connection conn = null;

		try
		{
			conn = DB.getConnection(pool);
			return execute(conn, sql, params);
		}
		finally
		{
			doClose(conn);
		}
	}

	public static int execute(Connection conn, String sql) throws Exception
	{
		return execute(conn, sql, (Object[])null);
	}

	public static int execute(Connection conn, String sql, Object... params) throws Exception
	{
		PreparedStatement stmt_select = null;

		try
		{
			stmt_select = conn.prepareStatement(sql);

			fillParams(stmt_select, params);

			return stmt_select.executeUpdate();
		}
		finally
		{
			doClose(stmt_select);
		}
	}

	/**
	 * Row als Map
	 */

	public static Map<String, String> getRow(String sql, Object... params) throws Exception
	{
		return getRow(0, sql, params);
	}

	public static Map<String, String> getRow(int pool, String sql, Object... params) throws Exception
	{
		Connection conn = null;

		try
		{
			conn = DB.getConnection(pool);
			return getRow(conn, sql, params);
		}
		finally
		{
			doClose(conn);
		}
	}

	public static Map<String, String> getRow(Connection conn, String sql, Object... params) throws Exception
	{
		Map<String, String> hs = null;

		ResultSet rset = null;

		PreparedStatement stmt_select = null;

		try
		{
			stmt_select = conn.prepareStatement(sql);

			fillParams(stmt_select, params);

			rset = stmt_select.executeQuery();

			if(rset.next())
			{
				hs = new Hashtable<String, String>();
				ResultSetMetaData md = rset.getMetaData();

				boolean info = log.isInfoEnabled();

				for(int i = md.getColumnCount(); i > 0; i--)
				{
					if(info)
						log.info(md.getColumnName(i) + " -- " + rset.getString(i));
					hs.put(md.getColumnName(i), secString(rset.getString(i)));
				}
			}
			else
			{
				log.warn("no result (" + sql + ")");
			}
		}
		finally
		{
			doClose(stmt_select);
			doClose(rset);
		}

		return hs;
	}

	/**
	 * Eine ArrayList von Map
	 */

	public static ArrayList<Map<String, String>> getRowList(String sql, Object... params) throws Exception
	{
		return getRowList(0, sql, params);
	}

	public static ArrayList<Map<String, String>> getRowList(int pool, String sql, Object... params) throws Exception
	{
		Connection conn = null;

		try
		{
			conn = DB.getConnection(pool);
			return getRowList(conn, sql, params);
		}
		finally
		{
			doClose(conn);
		}
	}

	public static ArrayList<Map<String, String>> getRowList(Connection conn, String sql, Object... params) throws Exception
	{
		ArrayList<Map<String, String>> al = new ArrayList<Map<String, String>>(INITIALCAP);

		ResultSet rset = null;
		PreparedStatement stmt_select = null;

		try
		{
			stmt_select = conn.prepareStatement(sql);

			fillParams(stmt_select, params);

			rset = stmt_select.executeQuery();

			ResultSetMetaData md = rset.getMetaData();

			boolean info = log.isInfoEnabled();

			while(rset.next())
			{
				Map<String, String> hs = new Hashtable<String, String>();

				for(int i = md.getColumnCount(); i > 0; i--)
				{
					if(info)
						log.info(md.getColumnName(i) + " -- " + rset.getString(i));
					hs.put(md.getColumnName(i), secString(rset.getString(i)));
				}

				al.add(hs);
			}
		}
		finally
		{
			doClose(stmt_select);
			doClose(rset);
		}

		return al;
	}

	public static Map<String, String> getHash(String sql, Object... params) throws Exception
	{
		return getHash(0, sql, params);
	}

	public static Map<String, String> getHash(int pool, String sql, Object... params) throws Exception
	{
		Connection conn = null;

		try
		{
			conn = DB.getConnection(pool);
			return getHash(conn, sql, params);
		}
		finally
		{
			doClose(conn);
		}
	}

	public static Map<String, String> getHash(Connection conn, String sql, Object... params) throws Exception
	{
		LinkedHashMap<String, String> hs = new LinkedHashMap<String, String>(INITIALCAP);

		ResultSet rset = null;
		PreparedStatement stmt_select = null;

		try
		{
			stmt_select = conn.prepareStatement(sql);

			fillParams(stmt_select, params);

			rset = stmt_select.executeQuery();

			while(rset.next())
			{
				hs.put(rset.getString(1), rset.getString(2));
			}
		}
		finally
		{
			doClose(stmt_select);
			doClose(rset);
		}

		return hs;
	}

	/**
	 * Firstvalue des SELECT als ArrayList
	 * 
	 */

	public static ArrayList<String> getList(String sql, Object... params) throws Exception
	{
		return getList(0, sql, params);
	}

	public static ArrayList<String> getList(int pool, String sql, Object... params) throws Exception
	{
		Connection conn = null;

		try
		{
			conn = DB.getConnection(pool);
			return getList(conn, sql, params);
		}
		finally
		{
			doClose(conn);
		}
	}

	public static ArrayList<String> getList(Connection conn, String sql, Object... params) throws Exception
	{
		ArrayList<String> al = new ArrayList<String>(INITIALCAP);

		ResultSet rset = null;

		PreparedStatement stmt_select = null;

		try
		{
			stmt_select = conn.prepareStatement(sql);

			fillParams(stmt_select, params);

			rset = stmt_select.executeQuery();

			while(rset.next())
			{
				al.add(secString(rset.getString(1)));
			}
		}
		finally
		{
			doClose(stmt_select);
			doClose(rset);
		}

		return al;
	}

	private static void fillParams(PreparedStatement stmt, Object... params) throws SQLException
	{
		if(params == null)
			return;

		int a = 1;
		for(int i = 0; i < params.length; i++)
		{
			stmt.setObject(a++, params[i]);
		}
	}

	public static final void doClose(ResultSet o)
	{
		if(o == null)
			return;
		try
		{
			o.close();
		}
		catch(Exception e)
		{
		}
	}

	public static final void doClose(Statement o)
	{
		if(o == null)
			return;
		try
		{
			o.close();
		}
		catch(Exception e)
		{
		}
	}

	public static final void doClose(Connection o)
	{
		if(o == null)
			return;
		try
		{
			o.setAutoCommit(true);
			o.close();
		}
		catch(Exception e)
		{
		}
	}

	public static final void doRollback(Connection o)
	{
		if(o == null)
			return;
		try
		{
			o.rollback();
		}
		catch(Exception e)
		{
		}
	}
}

/*
 * static final public Connection getConnection() throws Exception { //return
 * DriverManager.getConnection("jdbc:bms:pool0"); return
 * getDBSession(0).connection(); }
 * 
 * static final public Connection getConnection(int number) throws Exception {
 * //return DriverManager.getConnection("jdbc:bms:pool" + number); return
 * getDBSession(number).connection(); }
 * 
 * private static SessionFactory[] sessionFactory;
 * 
 * static { try { sessionFactory = new SessionFactory[dbc];
 * 
 * for(int i=0; i < dbc; i++) { String dburl = Option.getProperty("db(" + i +
 * ").url"); String dbconfig = Option.getProperty("db(" + i + ").config",
 * "hibernate.cfg.xml");
 * 
 * Configuration cfg = new Configuration().configure(dbconfig);
 * 
 * cfg //.addClass(org.hibernate.auction.Item.class)
 * //.addClass(org.hibernate.auction.Bid.class)
 * //.setProperty("hibernate.dialect",
 * "org.hibernate.dialect.SQLServerDialect");
 * .setProperty("hibernate.connection.url", dburl)
 * .setProperty("hibernate.connection.password", Option.getProperty("db(" + i +
 * ").password")) .setProperty("hibernate.connection.username",
 * Option.getProperty("db(" + i + ").user"))
 * .setProperty("hibernate.connection.driver_class", Option.getProperty("db(" +
 * i + ").driver"));
 * 
 * sessionFactory[i] = cfg.buildSessionFactory(); } }
 * 
 * public static Session getDBSession() throws Exception { return
 * getDBSession(0); }
 * 
 * public static Session getDBSession(int number) throws Exception { return
 * sessionFactory[number].openSession(); }
 * 
 * public static void initObject(Object obj) { Hibernate.initialize(obj); }
 */