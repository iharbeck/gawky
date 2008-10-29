package gawky.database;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBN {

	
    // Verbindung aus Connectionpool holen
    static public Connection getConnection() {
    	try { 
    		return DB.getConnection();
    	} catch (Exception e) {
    		return null;
    	}
    }

    static public Connection getConnection(int number) {
    	try { 
    		return DB.getConnection(number);
    	} catch (Exception e) {
    		return null;
    	}
    }

    static public Connection getConnection(String alias){
    	try { 
    		return DB.getConnection(alias);
    	} catch (Exception e) {
    		return null;
    	}
    }


    /**
     * get Long
     */

    public static String getString(String sql) {
	    try { 
			return DB.getString(sql);
		} catch (Exception e) {
			return null;
		}
	}
    
    public static String getString(int pool, String sql) {
    	try { 
    		return DB.getString(pool, sql);
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    public static String getString(int pool, String sql, Object[] params) {
    	try { 
    		return DB.getString(pool, sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}

    public static String getString(Connection conn, String sql) {
    	try { 
    		return DB.getString(conn, sql);
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    public static String getString(Connection conn, String sql, Object[] params) {
    	try { 
    		return DB.getString(conn, sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}

	
	public static int execute(String sql) {
		try { 
    		return DB.execute(sql);
    	} catch (Exception e) {
    		return 0;
    	}
	}
	  
	public static int execute(String sql, Object[] params) {
		try { 
    		return DB.execute(sql, params);
    	} catch (Exception e) {
    		return 0;
    	}
	}

	public static int execute(int pool, String sql) 
	{
		try { 
    		return DB.execute(pool, sql);
    	} catch (Exception e) {
    		return 0;
    	}
	}

	public static int execute(int pool, String sql, Object[] params) {
		try { 
    		return DB.execute(pool, sql, params);
    	} catch (Exception e) {
    		return 0;
    	}
	}

	public static int execute(Connection conn, String sql, Object[] params) {
		try { 
    		return DB.execute(conn, sql, params);
    	} catch (Exception e) {
    		return 0;
    	}
	}

    /**
     * Row als Map
     */


	public static Map getRow(String sql, String[] params) {
		try { 
    		return DB.getRow(sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}
	
	public static Map getRow(int pool, String sql, String[] params) {
		try { 
    		return DB.getRow(pool, sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}

	public static Map getRow(Connection conn, String sql, String[] params) {
		try { 
    		return DB.getHash(conn, sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}

	/**
	 * Eine ArrayList von Map
	 */

	public static ArrayList getRowList(String sql, String[] params) {
		try { 
    		return DB.getRowList(sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}

	public static ArrayList getRowList(int pool, String sql, String[] params) {
		try { 
    		return DB.getList(pool, sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}

	public static ArrayList getRowList(Connection conn, String sql, String[] params) {
		try { 
    		return DB.getList(conn, sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}

	public static HashMap getHash(String sql, String[] params) {
		try { 
    		return DB.getHash(sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}
	
	public static HashMap getHash(int pool, String sql, String[] params) {
		try { 
    		return DB.getHash(pool, sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}

	public static HashMap getHash(Connection conn, String sql, String[] params) 
	{
		try { 
    		return DB.getHash(conn, sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}

	/**
	 * Firstvalue des SELECT als ArrayList
	 *
	 */

	public static ArrayList getList(String sql, String[] params) 
	{
		try { 
    		return DB.getList(sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}

	public static ArrayList getList(int pool, String sql, String[] params) 
	{
		try { 
    		return DB.getList(pool, sql, params);
    	} catch (Exception e) {
    		return null;
    	}
	}

	public static ArrayList getList(Connection conn, String sql, String[] params) 
	{
		try {
			return DB.getList(sql, params);
		} catch (Exception e) {
			return null;
		}
	}
	
}
