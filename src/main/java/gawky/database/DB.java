package gawky.database;

import gawky.database.dbpool.AConnectionDriver;
import gawky.global.Format;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Ingo Harbeck
 */

public class DB
{
	private static Log log = LogFactory.getLog(DB.class);
	private static int INITIALCAP = 2000;

	public static HashMap<String, PoolWrapper> dbpool = new HashMap<String, PoolWrapper>();

	public static void init() throws Exception
	{
		int dbc = Option.getProperties("db_${staging}.driver").length;

		log.info("Datenbanken: " + dbc + " (" + Option.getProperty("staging") + ")");

		for(int i = 0; i < dbc; i++)
		{
			String base = "db_${staging}(" + i + ")";
			String dburl = Option.getProperty(base + ".url");
			String dbpass = Option.getProperty(base + ".password");
			String dbuser = Option.getProperty(base + ".user");
			String dbdriver = Option.getProperty(base + ".driver");
			String dbalias = Option.getProperty(base + ".alias", null);
			String[] dbproperties = Option.getProperties(base + ".property");

			String trigger = Option.getProperty(base + ".trigger", null);

			int dbconnmin = Format.getInt(Option.getProperty(base + ".connmin", "3"));
			int dbconnmax = Format.getInt(Option.getProperty(base + ".connmax", "30"));
			//int dbconnpartitions = Format.getInt(Option.getProperty(base + ".connpartitions", "1"));

			Properties props = new Properties();

			if(dbproperties != null)
			{

				for(String dbpropertie : dbproperties)
				{
					String[] val = dbpropertie.split("=");
					props.put(val[0], val[1]);
				}
			}

			System.out.println(dburl);

			log.info("Register: " + dburl);

			try
			{
				Class.forName(dbdriver);
			}
			catch(Exception e)
			{
				System.out.println("No Suitable Driver: " + dbdriver);
			}

			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(dburl);
			config.setUsername(dbuser);
			config.setPassword(dbpass);
			config.setMinimumIdle(dbconnmin);
			config.setMaximumPoolSize(dbconnmax);
			config.setInitializationFailFast(false);
			
			if(trigger != null)
			{
				config.setConnectionInitSql(trigger);
			}

			config.setDataSourceProperties(props);
			
			if(!dburl.toLowerCase().contains("oracle") && !dburl.toLowerCase().contains("mysql") )
			{
				config.setConnectionTestQuery("select 1");
			}
			
			HikariDataSource ds = new HikariDataSource(config);
			
			/*
			BoneCPConfig config = new BoneCPConfig();

			config.setJdbcUrl(dburl);
			config.setUsername(dbuser);
			config.setPassword(dbpass);
			config.setMinConnectionsPerPartition(dbconnmin);
			config.setMaxConnectionsPerPartition(dbconnmax);
			config.setPartitionCount(dbconnpartitions);
			config.setDriverProperties(props);
			//config.setStatementsCacheSize(50);

			if(trigger != null)
			{
				config.setInitSQL(trigger);
			}
			config.setLazyInit(true);
			*/
		
			try
			{
				//BoneCP connectionPool = new BoneCP(config);
				
				HikariDataSource connectionPool = new HikariDataSource(config);

				if(dbalias == null)
				{
					//new gawky.database.dbpool.AConnectionDriver(dbdriver, dburl, dbuser, dbpass, "pool" + i, 5000000, props);
					dbpool.put(Integer.toString(i), new PoolWrapper(connectionPool));
				}
				else
				{
					//new gawky.database.dbpool.AConnectionDriver(dbdriver, dburl, dbuser, dbpass, dbalias, 5000000, props);
					dbpool.put(dbalias, new PoolWrapper(connectionPool));
				}
			}
			catch(Exception e)
			{
				log.error("Pooleinrichtung: " + e.getMessage());
			}
		}
	}

	static public void enablePool(int number)
	{
		dbpool.get(Integer.toString(number)).enable();
	}

	static public void enablePool(String alias)
	{
		dbpool.get(alias).enable();
	}

	static public void enablePool()
	{
		dbpool.get("0").enable();
	}

	static public void disablePool(int number)
	{
		dbpool.get(Integer.toString(number)).disable();
	}

	static public void disablePool(String alias)
	{
		dbpool.get(alias).disable();
	}

	static public void disablePool()
	{
		dbpool.get("0").disable();
	}

	static public Connection getConnection(int number) throws SQLException
	{
		return getConnection(Integer.toString(number));
	}

	// Verbindung aus Connectionpool holen
	static public Connection getConnection() throws SQLException
	{
		return getConnection("0");
	}

	static public Connection getConnection(String alias) throws SQLException
	{
		//		if(log.isInfoEnabled())
		//			log.info("get connection");
		//		return DriverManager.getConnection(AConnectionDriver.URL_PREFIX + alias);

		return dbpool.get(alias).getConnection();
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
		{
			return val.trim();
		}
		else
		{
			return "";
		}
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
		Statement stmt_select = null;

		try
		{
			if(params == null)
			{
				stmt_select = conn.createStatement();
				rset = stmt_select.executeQuery(sql);
			}
			else
			{
				stmt_select = conn.prepareStatement(sql);
				fillParams((PreparedStatement)stmt_select, params);

				rset = ((PreparedStatement)stmt_select).executeQuery();
			}

			if(rset.next())
			{
				return rset.getString(1);
			}

			return "";
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
		Statement stmt_select = null;

		try
		{
			if(params == null)
			{
				stmt_select = conn.createStatement();
				return stmt_select.executeUpdate(sql);
			}
			else
			{
				stmt_select = conn.prepareStatement(sql);
				fillParams((PreparedStatement)stmt_select, params);

				return ((PreparedStatement)stmt_select).executeUpdate();
			}
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

		Statement stmt_select = null;

		try
		{
			if(params == null)
			{
				stmt_select = conn.createStatement();
				rset = stmt_select.executeQuery(sql);
			}
			else
			{
				stmt_select = conn.prepareStatement(sql);
				fillParams((PreparedStatement)stmt_select, params);

				rset = ((PreparedStatement)stmt_select).executeQuery();
			}

			if(rset.next())
			{
				ResultSetMetaData md = rset.getMetaData();
				int columncount = md.getColumnCount();

				hs = new HashMap<String, String>(columncount);

				for(int i = columncount; i > 0; i--)
				{
					hs.put(md.getColumnLabel(i), secString(rset.getString(i)));
				}
			}
			else if(log.isWarnEnabled())
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
		Statement stmt_select = null;

		try
		{
			if(params == null)
			{
				stmt_select = conn.createStatement();
				rset = stmt_select.executeQuery(sql);
			}
			else
			{
				stmt_select = conn.prepareStatement(sql);
				fillParams((PreparedStatement)stmt_select, params);

				rset = ((PreparedStatement)stmt_select).executeQuery();
			}

			ResultSetMetaData md = rset.getMetaData();
			int columncount = md.getColumnCount();

			String[] columnname = new String[columncount];

			for(int i = 0; i < columncount; i++)
			{
				columnname[i] = md.getColumnLabel(i + 1);
			}

			while(rset.next())
			{
				Map<String, String> hs = new HashMap<String, String>(columncount);

				for(int i = columncount; i > 0; i--)
				{
					hs.put(columnname[i - 1], secString(rset.getString(i)));
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

	public static Result getMemoryList(String sql, Object... params) throws Exception
	{
		return getMemoryList(0, sql, params);
	}

	public static Result getMemoryList(int pool, String sql, Object... params) throws Exception
	{
		Connection conn = null;

		try
		{
			conn = DB.getConnection(pool);
			return getMemoryList(conn, sql, params);
		}
		finally
		{
			doClose(conn);
		}
	}

	public static Result getMemoryList(Connection conn, String sql, Object... params) throws Exception
	{
		ResultSet rset = null;
		Statement stmt_select = null;

		Result result;

		try
		{
			if(params == null)
			{
				stmt_select = conn.createStatement();
				rset = stmt_select.executeQuery(sql);
			}
			else
			{
				stmt_select = conn.prepareStatement(sql);
				fillParams((PreparedStatement)stmt_select, params);

				rset = ((PreparedStatement)stmt_select).executeQuery();
			}

			ResultSetMetaData md = rset.getMetaData();
			int columncount = md.getColumnCount();

			result = new Result(columncount);

			for(int i = 0; i < columncount; i++)
			{
				result.add(md.getColumnLabel(i + 1), i);
			}

			while(rset.next())
			{
				result.insert();

				for(int i = 0; i < columncount; i++)
				{
					result.put(i, secString(rset.getString(i + 1)));
				}
			}
		}
		finally
		{
			doClose(stmt_select);
			doClose(rset);
		}

		return result;
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
		Statement stmt_select = null;

		try
		{
			if(params == null)
			{
				stmt_select = conn.createStatement();
				rset = stmt_select.executeQuery(sql);
			}
			else
			{
				stmt_select = conn.prepareStatement(sql);
				fillParams((PreparedStatement)stmt_select, params);

				rset = ((PreparedStatement)stmt_select).executeQuery();
			}

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

		Statement stmt_select = null;

		try
		{
			if(params == null)
			{
				stmt_select = conn.createStatement();
				rset = stmt_select.executeQuery(sql);
			}
			else
			{
				stmt_select = conn.prepareStatement(sql);
				fillParams((PreparedStatement)stmt_select, params);

				rset = ((PreparedStatement)stmt_select).executeQuery();
			}

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
		int a = 1;
		for(Object param : params)
		{
			stmt.setObject(a++, param);
		}
	}

	public static final void doClose(ResultSet o)
	{
		if(o == null)
		{
			return;
		}
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
		{
			return;
		}
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
		{
			return;
		}
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
		{
			return;
		}
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
 * static final public Connection getConnection() throws Exception { //return DriverManager.getConnection("jdbc:bms:pool0"); return getDBSession(0).connection(); }
 * 
 * static final public Connection getConnection(int number) throws Exception { //return DriverManager.getConnection("jdbc:bms:pool" + number); return getDBSession(number).connection(); }
 * 
 * private static SessionFactory[] sessionFactory;
 * 
 * static { try { sessionFactory = new SessionFactory[dbc];
 * 
 * for(int i=0; i < dbc; i++) { String dburl = Option.getProperty("db(" + i + ").url"); String dbconfig = Option.getProperty("db(" + i + ").config", "hibernate.cfg.xml");
 * 
 * Configuration cfg = new Configuration().configure(dbconfig);
 * 
 * cfg //.addClass(org.hibernate.auction.Item.class) //.addClass(org.hibernate.auction.Bid.class) //.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect"); .setProperty("hibernate.connection.url", dburl) .setProperty("hibernate.connection.password", Option.getProperty("db(" + i + ").password")) .setProperty("hibernate.connection.username", Option.getProperty("db(" + i + ").user")) .setProperty("hibernate.connection.driver_class", Option.getProperty("db(" + i + ").driver"));
 * 
 * sessionFactory[i] = cfg.buildSessionFactory(); } }
 * 
 * public static Session getDBSession() throws Exception { return getDBSession(0); }
 * 
 * public static Session getDBSession(int number) throws Exception { return sessionFactory[number].openSession(); }
 * 
 * public static void initObject(Object obj) { Hibernate.initialize(obj); }
 */