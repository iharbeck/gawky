package gawky.database;


import gawky.global.Option;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


import org.apache.log4j.Logger;


/**
 * @author Ingo Harbeck
 *
 */

public class DB 
{
	static final Logger log = Logger.getLogger(DB.class);

	static 
	{
		List dbs = Option.getConfig().getList("db.driver");
		
		int dbc = dbs.size();
		log.info("Datenbanken: " + dbc);
		
	    try {
	    	for(int i=0; i < dbc; i++) 
	    	{
	    		String dburl    = Option.getProperty("db(" + i + ").url");
	    		//String dbconfig = Option.getProperty("db(" + i + ").config", null);  // "hibernate.cfg.xml"
				String dbpass   = Option.getProperty("db(" + i + ").password");
				String dbuser   = Option.getProperty("db(" + i + ").user");
				String dbdriver = Option.getProperty("db(" + i + ").driver");
	
		        log.info(dburl);
	
		        new gawky.database.dbpool.AConnectionDriver(dbdriver, dburl, dbuser, dbpass, "pool" + i, 5000000);
	    	}
		} catch (Exception e) {
	        log.error("Problem bei Pooleinrichtung: ", e);
		}
	} 
    
    // Verbindung aus Connectionpool holen
    static public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:bms:pool0");
    }
    
    static public Connection getConnection(int number) throws SQLException {
    	return DriverManager.getConnection("jdbc:bms:pool" + number);
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
    
    /**
     * Row als Hashtable
     */
	
	
	public static Hashtable getRow(String sql, String[] params)
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
    
	public static Hashtable getRow(Connection conn, String sql, String[] params)
	{
		Hashtable hs = null;
		
		ResultSet rset = null;

		PreparedStatement stmt_select = null;
		
		try {
			conn = DB.getConnection(0);
			
			stmt_select = conn.prepareStatement(sql);
			
			int a = 1;
			
			for (int i=0; i < params.length; i++) {
				String param = params[i];
				stmt_select.setString(a++, param);
			}

			rset = stmt_select.executeQuery();

			if (rset.next())
			{
				hs = new Hashtable();
				ResultSetMetaData md = stmt_select.getMetaData();
				
				for (int i = md.getColumnCount(); i > 0; i --) {
					log.info(md.getColumnName(i) + " -- " + rset.getString(i));
					hs.put(md.getColumnName(i), secString(rset.getString(i)));
				}
			} else {
				log.error("no result (" + sql + ")" + params[0]);
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
	 * Eine ArrayList von Hashtables
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
			conn = DB.getConnection(0);
			
			stmt_select = conn.prepareStatement(sql);
			
			int a = 1;
			for (int i=0; i < params.length; i++) {
				String param = params[i];
				stmt_select.setString(a++, param);
			}

			rset = stmt_select.executeQuery();

			while (rset.next())
			{
				Hashtable hs = new Hashtable();
				ResultSetMetaData md = stmt_select.getMetaData();
				
				for (int i = md.getColumnCount(); i > 0; i --) {
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
			conn = DB.getConnection(0);
			
			stmt_select = conn.prepareStatement(sql);
			
			int a = 1;
			for (int i=0; i < params.length; i++) {
				String param = params[i];
				stmt_select.setString(a++, param);
			}

			rset = stmt_select.executeQuery();

			while (rset.next())
			{
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
	
	public static final void doClose(ResultSet o)
	 {
		try { 
			if (o != null) o.close();
		} catch (Exception e) { }
	 }

	 public static final void doClose(Statement o)
	 {
		try { 
			if (o != null) o.close();
		} catch (Exception e) { }
	 }

	 public static final void doClose(Connection o)
	 {
		try { 
			if (o != null) o.close();
		} catch (Exception e) { }
	 }
}


/*
 
  
  package gawky.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class DB 
{
	static final Logger log = Logger.getLogger(DB.class);
	
	static 
	{
//		try 
//		{
//			log.debug("Parameter initialisieren ");
//		
//			List dbs = Option.config.getList("db.driver");
//			
//			System.out.println("Datenbanken: " + dbs.size());
//			
//			db_driver   = Option.getProperties("db(0).driver");
//			db_url 	    = Option.getProperties("db(0).url");
//			db_user     = Option.getProperties("db(0).user");
//			db_password = Option.getProperties("db(0).password");
//			
//			db_config   = Option.getProperties("db(0).config");
//		} catch (Exception e) {
//			log.error(e);	
//		}
//		
//		try {
//            System.out.println("Init Pool");
//            for(int i = 0; i < db_driver.length; i++)
//            {
//            	Class.forName(db_driver[i]);
//            	
//            	long time = 50000000;
////            	if(db_time.length > 0)
////            		time = Long.parseLong(db_time[i]);
////            	
////            	new gawky.dbpool.AConnectionDriver(db_driver[i], db_url[i], db_user[i], db_password[i], "pool" + i, time);
//            	System.out.println(db_url[i]);
//            }	
//            System.out.println("Pool done");
//		} catch(Exception e) {
//            System.out.println("Problem bei Pooleinrichtung: " + e);
//        }
	} 
    
    // Verbindung aus Connectionpool holen
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
	      System.out.println("Initializing Datasources");
	      
			List dbs = Option.config.getList("db.driver");
			
			int dbc = dbs.size();
			System.out.println("Datenbanken: " + dbc);
			
//			db_driver   = Option.getProperties("db(0).driver");
//			db_url 	    = Option.getProperties("db(0).url");
//			db_user     = Option.getProperties("db(0).user");
//			db_password = Option.getProperties("db(0).password");
//			
//			db_config   = Option.getProperties("db(0).config");

			
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

		        System.out.println(dburl);
		        
		    	sessionFactory[i] = cfg.buildSessionFactory();
	    	}
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		  
		  System.out.println("Finished Initializing Datasources");
	 }
	  

	 public static Session getDBSession() throws Exception
	 {
	      return getDBSession(0);
	 }

	 public static Session getDBSession(int number) throws Exception
	 {
	      return sessionFactory[number].openSession();
	 }
    
    
	 public static boolean isDBAvailable()
	 {
		return isDBAvailable(0); 
	 }
	
	 public static boolean isDBAvailable(int number)
     {
    	Connection conn = null;
    	try {
			conn = getConnection(number);
			conn.getMetaData();
		} catch (Exception e) {
			return false;
		} finally {
			try { if(conn != null) conn.close(); } catch (Exception e) {} 
		}
		return true;
    }
    
    final static private String secString(ResultSet rset, int i)
	{
    	try {
    		String val = rset.getString(i);
    		if(val == null)
    			return "";
    		else
    			return val;
    	} catch (Exception e) {
		}
    	return "";
	}
	
	public static Hashtable getRow(String sql, String[] params)
	{
		Hashtable hs = null;
		Connection conn = null;
		ResultSet rset = null;

		PreparedStatement stmt_select = null;
		
		try {
			conn = DB.getConnection();
			
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
				ResultSetMetaData md = stmt_select.getMetaData();
				
				for (int i = md.getColumnCount(); i > 0; i --) {
					//System.out.println(md.getColumnName(i) + " -- " + secString(rset,i));
					hs.put(md.getColumnName(i), secString(rset,i));
				}
			} else {
				System.out.println("no result (" + sql + ")" + (params != null ? params[0] : ""));
			}
		} catch (Exception e) {
			System.out.println(".." + e);
		} finally {
			doClose(stmt_select); 
			doClose(rset);
			doClose(conn);
		}
		
		return hs;
	}
	
	
	public static ArrayList getRowList(String sql, String[] params)
	{
		ArrayList al = new ArrayList();
		
		
		Connection conn = null;
		
		ResultSet rset = null;

		PreparedStatement stmt_select = null;
		
		try {
			conn = DB.getConnection();
			
			stmt_select = conn.prepareStatement(sql);
			
			int a = 1;
			for (int i=0; params != null && i < params.length; i++) {
				String param = params[i];
				stmt_select.setString(a++, param);
			}

			rset = stmt_select.executeQuery();

			while (rset.next())
			{
				Hashtable hs = new Hashtable();
				ResultSetMetaData md = stmt_select.getMetaData();
				
				for (int i = md.getColumnCount(); i > 0; i --) {
					//System.out.println(md.getColumnName(i) + " -- " + secString(rset, i));
					hs.put(md.getColumnName(i), secString(rset, i));
				}
				
				al.add(hs);
			} 
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			doClose(stmt_select); 
			doClose(rset);
			doClose(conn);
		}
		
		return al;
	}

	public static ArrayList getList(String sql, String[] params)
	{
		ArrayList al = new ArrayList();
		
		
		Connection conn = null;
		
		ResultSet rset = null;

		PreparedStatement stmt_select = null;
		
		try {
			conn = DB.getConnection();
			
			stmt_select = conn.prepareStatement(sql);
			
			int a = 1;
			for (int i=0; params != null && i < params.length; i++) {
				String param = params[i];
				stmt_select.setString(a++, param);
			}

			rset = stmt_select.executeQuery();

			while (rset.next())
			{
				al.add(secString(rset, 1));
			} 
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			doClose(stmt_select); 
			doClose(rset);
			doClose(conn);
		}
		
		return al;
	}
	
	
	 
	 public static final void doClose(ResultSet o)
	 {
		try { 
			if (o != null) o.close();
		} catch (Exception e) { }
	 }

	 public static final void doClose(Statement o)
	 {
		try { 
			if (o != null) o.close();
		} catch (Exception e) { }
	 }

	 public static final void doClose(Connection o)
	 {
		try { 
			if (o != null) o.close();
		} catch (Exception e) { }
	 }
	 
	 public static void initObject(Object obj) {
		Hibernate.initialize(obj);
	 }
}
 * 
 * 
 */