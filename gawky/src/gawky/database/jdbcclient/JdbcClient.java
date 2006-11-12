package gawky.database.jdbcclient;

import gawky.database.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JdbcClient 
{
	Connection conn;

	public JdbcClient(Connection conn)
	{
		this.conn = conn;
	}
	
	public JdbcClient() throws Exception
	{
		this.conn = DB.getConnection();
	}
	
	public void close() {
		DB.doClose(conn);
	}
	
	
	public Object select(String sql) throws Exception {
		return select(sql, null, null);
	}
	
	public Object select(String sql, JdbcSelectMapper object) throws Exception {
		return select(sql, object, null);
	}
	
	public Object select(String sql, JdbcSelectMapper object, Object[] params) throws Exception
	{
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		try 
		{
			stmt = conn.prepareStatement(sql);
			
			if(params != null) {
				for(int i = 1; i <= params.length; i++)
					stmt.setObject(i, params[i-1]);
			}
	
			rset = stmt.executeQuery();
			
			if(rset.next())
			{
				if(object != null)
					return object.selectmapper(rset);
				else
					return rset.getObject(0);
			} else {
				throw new Exception("Object not found " + object);
			}
		} finally {
			DB.doClose(rset);
			DB.doClose(stmt);
		}
	}

	
	public String selectString(String sql, Object[] params) throws Exception
	{
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		try 
		{
			stmt = conn.prepareStatement(sql);
			
			if(params != null) {
				for(int i = 1; i <= params.length; i++)
					stmt.setObject(i, params[i-1]);
			}
	
			rset = stmt.executeQuery();
			
			if(rset.next())
					return rset.getString(0);
			else 
				throw new Exception("Object not found !");
			
		} finally {
			DB.doClose(rset);
			DB.doClose(stmt);
		}
	}
	
	public long selectLong(String sql, Object[] params) throws Exception
	{
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		try 
		{
			stmt = conn.prepareStatement(sql);
			
			if(params != null) {
				for(int i = 1; i <= params.length; i++)
					stmt.setObject(i, params[i-1]);
			}
	
			rset = stmt.executeQuery();
			
			if(rset.next())
					return rset.getLong(0);
			else 
				throw new Exception("Object not found !");
			
		} finally {
			DB.doClose(rset);
			DB.doClose(stmt);
		}
	}

	public double selectDouble(String sql, Object[] params) throws Exception
	{
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		try 
		{
			stmt = conn.prepareStatement(sql);
			
			if(params != null) {
				for(int i = 1; i <= params.length; i++)
					stmt.setObject(i, params[i-1]);
			}
	
			rset = stmt.executeQuery();
			
			if(rset.next())
					return rset.getDouble(0);
			else 
				throw new Exception("Object not found !");
			
		} finally {
			DB.doClose(rset);
			DB.doClose(stmt);
		}
	}

	
	public ArrayList list(String sql, JdbcSelectMapper object) throws Exception
	{
		return list(sql, object, null);
	}
	
	public ArrayList list(String sql, JdbcSelectMapper object, Object[] params) throws Exception
	{
		ArrayList list = new ArrayList();
		
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		try 
		{
			stmt = conn.prepareStatement(sql);
			
			if(params != null) {
				for(int i = 1; i <= params.length; i++)
					stmt.setObject(i, params[i-1]);
			}
	
			rset = stmt.executeQuery();
			
			while(rset.next())
			{
				list.add(object.selectmapper(rset));
			}
		} finally {
			DB.doClose(rset);
			DB.doClose(stmt);
		}
		return list;
	}


	//helper
	public static String completeInsert(String sql) {
		String ext = " VALUES (?";
		for(int i=0; i<sql.length(); i++) 
		{
			if(sql.charAt(i) == ',')
			{
				ext += ",?";
			}
		}
		ext +=  ")"; 
		
		return sql + ext;
	}
	
	public void execute(String sql) throws Exception
	{
		execute(sql, null, null);
	}
	
	/**
	 * Execute SQL with parameter list
	 * @param sql
	 * @param params
	 * @throws Exception
	 */
	public void execute(String sql, Object[] params) throws Exception {
		execute(sql, params, null);
	}

	public void execute(String sql, JdbcParameterMapper object) throws Exception {
		execute(sql, null, object);
	}

	public void execute(String sql, Object[] params, JdbcParameterMapper object) throws Exception
	{
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		try 
		{
			stmt = conn.prepareStatement(sql);
			
			if(params != null) {
				for(int i = 1; i <= params.length; i++)
					stmt.setObject(i, params[i-1]);
			}

			if(object != null) {
				// count number of reqired params (for insert autofield case)
				int paramcount=0;
				for(int i=0; i<sql.length(); i++) {
					if(sql.charAt(i) == '?')
						paramcount++;
				}
				
				StatementDecorator mappedstmt = new StatementDecorator(stmt, paramcount);
				object.parametermapper(mappedstmt);
			}
			
			stmt.execute();
			
		} finally {
			DB.doClose(rset);
			DB.doClose(stmt);
		}
	}
}
