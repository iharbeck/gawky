package gawky.database;

import gawky.database.dbpool.AConnectionDriver;
import gawky.global.Option;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Ingo Harbeck
 *
 */

public class DB
{
	private static Log log = LogFactory.getLog(DB.class);

	public static void init()
	{
		String staging = Option.getProperty("staging", "");

		if(staging.length() > 0)
			staging = "_" + staging;

		int dbc = Option.getProperties("db" + staging + ".driver").length;

		log.info("Datenbanken: " + dbc);

		for(int i=0; i < dbc; i++)
    	{
    		String dburl    = Option.getProperty("db" + staging + "(" + i + ").url");
			String dbpass   = Option.getProperty("db" + staging + "(" + i + ").password");
			String dbuser   = Option.getProperty("db" + staging + "(" + i + ").user");
			String dbdriver = Option.getProperty("db" + staging + "(" + i + ").driver");
			String dbalias  = Option.getProperty("db" + staging + "(" + i + ").alias", null);

	        log.info("Register: " + dburl);
	        try {
	        	if(dbalias == null)
	        		new gawky.database.dbpool.AConnectionDriver(dbdriver, dburl, dbuser, dbpass, "pool" + i, 5000000);
	        	else
	        		new gawky.database.dbpool.AConnectionDriver(dbdriver, dburl, dbuser, dbpass, dbalias, 5000000);
	    	} catch (Exception e) {
		        log.error("Pooleinrichtung: " + e.getMessage());
			}
    	}
	}

    // Verbindung aus Connectionpool holen
    static public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(AConnectionDriver.URL_PREFIX + "pool0");
    }

    static public Connection getConnection(int number) throws SQLException {
    	return DriverManager.getConnection(AConnectionDriver.URL_PREFIX + "pool" + number);
    }

    static public Connection getConnection(String alias) throws SQLException {
    	return DriverManager.getConnection(AConnectionDriver.URL_PREFIX + alias);
    }

    public static boolean isDBAvailable()
    {
    	return isDBAvailable(0);
    }

    public static boolean isDBAvailable(int number)
    {
    	Connection conn = null;
    	try {
			conn = DB.getConnection(number);
			conn.getMetaData();
		} catch (Exception e) {
			return false;
		} finally {
			doClose(conn);
		}
		return true;
    }

    private static final String secString(Object val)
	{
		if(val != null)
			val = ((String)val).trim();
		else
			val = "";
		return (String)val;
	}


    /**
     * get Long
     */

    public static String getString(String sql)
	{
		Connection conn = null;

		try
		{
			conn = DB.getConnection(0);
			return getString(conn, sql);
		} catch (Exception e) {
			log.error(e);
		} finally {
			doClose(conn);
		}

		return null;
	}

	public static String getString(Connection conn, String sql) throws Exception
	{
		ResultSet rset = null;
		PreparedStatement stmt_select = null;

		try {
			stmt_select = conn.prepareStatement(sql);
			rset 	    = stmt_select.executeQuery();

			rset.next();
			return rset.getString(1);
		} finally {
			doClose(stmt_select);
			doClose(rset);
		}
	}

	  public static int execute(String sql)
		{
		  return execute(0, sql);
		}
	  
	  public static int execute(String sql, Object[] params)
		{
		  return execute(0, sql, params);
		}

	  public static int execute(int pool, String sql)
		{
		  return execute(pool, sql, null);
		}

	  public static int execute(int pool, String sql, Object[] params)
		{
			Connection conn = null;

			try
			{
				conn = DB.getConnection(pool);
				return execute(conn, sql, params);
			} catch (Exception e) {
				log.error(e);
			} finally {
				doClose(conn);
			}
			return 0;
		}

	public static int execute(Connection conn, String sql, Object[] params) throws Exception
	{
		PreparedStatement stmt_select = null;

		try {

			stmt_select = conn.prepareStatement(sql);
			return stmt_select.executeUpdate();
		} finally {
			doClose(stmt_select);
		}
	}

    /**
     * Row als Map
     */


	public static Map getRow(String sql, String[] params)
	{
		Connection conn = null;

		try
		{
			conn = DB.getConnection(0);
			return getRow(conn, sql, params);
		} catch (Exception e) {
			log.error(e);
		} finally {
			doClose(conn);
		}

		return null;
	}

	public static Map getRow(Connection conn, String sql, String[] params)
	{
		Map hs = null;

		ResultSet rset = null;

		PreparedStatement stmt_select = null;

		try {
			stmt_select = conn.prepareStatement(sql);

			int a = 1;

			for (int i=0; params != null && i < params.length; i++) {
				String param = params[i];
				stmt_select.setString(a++, param);
			}

			rset = stmt_select.executeQuery();

			if (rset.next())
			{
				hs = new Hashtable();
				ResultSetMetaData md = rset.getMetaData();

				for (int i = md.getColumnCount(); i > 0; i --) {
					log.info(md.getColumnName(i) + " -- " + rset.getString(i));
					hs.put(md.getColumnName(i), secString(rset.getString(i)));
				}
			} else {
				log.error("no result (" + sql + ")");
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			doClose(stmt_select);
			doClose(rset);
		}

		return hs;
	}

	/**
	 * Eine ArrayList von Map
	 */

	public static ArrayList getRowList(String sql, String[] params)
	{
		Connection conn = null;

		try
		{
			conn = DB.getConnection(0);
			return getRowList(conn, sql, params);
		} catch (Exception e) {
			log.error(e);
		} finally {
			doClose(conn);
		}

		return null;
	}

	public static ArrayList getRowList(Connection conn, String sql, String[] params)
	{
		ArrayList al = new ArrayList();

		ResultSet rset = null;
		PreparedStatement stmt_select = null;

		try {
			stmt_select = conn.prepareStatement(sql);

			int a = 1;
			for (int i=0; params != null && i < params.length; i++) {
				String param = params[i];
				stmt_select.setString(a++, param);
			}

			rset = stmt_select.executeQuery();

			ResultSetMetaData md = rset.getMetaData();
			boolean info = log.isInfoEnabled();
			while (rset.next())
			{
				Map hs = new Hashtable();

				for (int i = md.getColumnCount(); i > 0; i --) {
					if(info)
						log.info(md.getColumnName(i) + " -- " + rset.getString(i));
					hs.put(md.getColumnName(i), secString(rset.getString(i)));
				}

				al.add(hs);
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			doClose(stmt_select);
			doClose(rset);
		}

		return al;
	}


	public static HashMap getHash(String sql, String[] params)
	{
		Connection conn = null;

		try
		{
			conn = DB.getConnection(0);
			return getHash(conn, sql, params);
		} catch (Exception e) {
			log.error(e);
		} finally {
			doClose(conn);
		}

		return null;
	}

	public static HashMap getHash(Connection conn, String sql, String[] params)
	{
		HashMap hs = new HashMap();

		ResultSet rset = null;
		PreparedStatement stmt_select = null;

		try {
			stmt_select = conn.prepareStatement(sql);

			int a = 1;
			for (int i=0; params != null && i < params.length; i++) {
				String param = params[i];
				stmt_select.setString(a++, param);
			}

			rset = stmt_select.executeQuery();


			while (rset.next())
			{
				hs.put(rset.getString(1), rset.getString(2));
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			doClose(stmt_select);
			doClose(rset);
		}

		return hs;
	}

	/**
	 * Firstvalue des SELECT als ArrayList
	 *
	 */

	public static ArrayList getList(String sql, String[] params)
	{
		Connection conn = null;

		try
		{
			conn = DB.getConnection(0);
			return getList(conn, sql, params);
		} catch (Exception e) {
			log.error(e);
		} finally {
			doClose(conn);
		}

		return null;
	}

	public static ArrayList getList(Connection conn, String sql, String[] params)
	{
		ArrayList al = new ArrayList();


		ResultSet rset = null;

		PreparedStatement stmt_select = null;

		try {
			stmt_select = conn.prepareStatement(sql);

			int a = 1;
			for (int i=0; params != null && i < params.length; i++) {
				String param = params[i];
				stmt_select.setString(a++, param);
			}

			rset = stmt_select.executeQuery();

			while (rset.next()) {
				al.add(secString(rset.getString(1)));
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			doClose(stmt_select);
			doClose(rset);
		}

		return al;
	}

	public static final void doClose(ResultSet o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) { }
	 }

	 public static final void doClose(Statement o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) { }
	 }

	 public static final void doClose(Connection o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) { }
	 }
}


/*

    static final public Connection getConnection() throws Exception {
    	//return DriverManager.getConnection("jdbc:bms:pool0");
    	return getDBSession(0).connection();
    }

    static final public Connection getConnection(int number) throws Exception {
    	//return DriverManager.getConnection("jdbc:bms:pool" + number);
    	return getDBSession(number).connection();
    }

    private static SessionFactory[] sessionFactory;

	static {
	      try {
	    	sessionFactory  = new SessionFactory[dbc];

	    	for(int i=0; i < dbc; i++)
	    	{
	    		String dburl = Option.getProperty("db(" + i + ").url");
	    		String dbconfig = Option.getProperty("db(" + i + ").config", "hibernate.cfg.xml");

	    		Configuration cfg = new Configuration().configure(dbconfig);

		        cfg
		        //.addClass(org.hibernate.auction.Item.class)
		        //.addClass(org.hibernate.auction.Bid.class)
		        //.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
		        .setProperty("hibernate.connection.url", dburl)
				.setProperty("hibernate.connection.password", Option.getProperty("db(" + i + ").password"))
				.setProperty("hibernate.connection.username", Option.getProperty("db(" + i + ").user"))
				.setProperty("hibernate.connection.driver_class", Option.getProperty("db(" + i + ").driver"));

		    	sessionFactory[i] = cfg.buildSessionFactory();
	    	}
	 }

	 public static Session getDBSession() throws Exception
	 {
	      return getDBSession(0);
	 }

	 public static Session getDBSession(int number) throws Exception
	 {
	      return sessionFactory[number].openSession();
	 }

	 public static void initObject(Object obj) {
		Hibernate.initialize(obj);
	 }
 */