package gawky.database.dbpool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class AConnection implements Connection
{
	//private static Log log = LogFactory.getLog(AConnection.class);

	private AConnectionPool pool;
	private Connection conn;
	private boolean inuse;
	private long timestamp;

	//ArrayList<Statement> statements = new ArrayList<Statement>();

	public AConnection(Connection conn, AConnectionPool pool)
	{
		this.conn = conn;
		this.pool = pool;
		this.inuse = false;
		this.timestamp = 0;
	}

	/**
	 * Check status of connection.
	 * 
	 * @return false connection is currently in use, true connection is available status and timestamp is set.
	 */

	public synchronized boolean lease()
	{
		if(inuse)
		{
			return false;
		}
		else
		{
			inuse = true;
			timestamp = System.currentTimeMillis();
			return true;
		}
	}

	/**
	 * Validate a connection.
	 * 
	 * @return <b>true</b> for active connection, <b>false</b> if we lost connection
	 */

	public boolean validate()
	{
		return true;
		
//		try
//		{
//			ResultSet rset = conn.getMetaData().getSchemas();
//			rset.next();
//			DB.doClose(rset);
//		}
//		catch(Exception e)
//		{
//			DB.doClose(conn);
//			return false;
//		}
//		return true;
	}

	public boolean inUse()
	{
		return inuse;
	}

	public long getLastUse()
	{
		return timestamp;
	}

	public void close() throws SQLException
	{
		//log.info("closing [" + this + "]");

		//    	log.info("close statements[" + statements.size() + "]");
		//         	
		//    	for(int i=0; i < statements.size(); i++)
		//    	{
		//    		statements.get(i).close();
		//    	}
		//    	
		//    	statements = new ArrayList<Statement>();

		pool.returnConnection(this);
	}

	public void finalclose() throws SQLException
	{
		System.out.println("closing: " + conn);
		conn.close();
		System.out.println("closed:  " + conn);
	}

	protected void expireLease()
	{
		inuse = false;
	}

	public Connection getConnection()
	{
		System.out.println("getConn: " + conn);
		return conn;
	}

	//    private Statement store(Statement val) {
	//    	statements.add(val);
	//    	return val;
	//    }
	public PreparedStatement prepareStatement(String sql) throws SQLException
	{

		return new APreparedStatement(conn.prepareStatement(sql));
	}

	public CallableStatement prepareCall(String sql) throws SQLException
	{
		return new ACallableStatement(conn.prepareCall(sql));
	}

	public Statement createStatement() throws SQLException
	{
		return new AStatement(conn.createStatement());
	}

	public String nativeSQL(String sql) throws SQLException
	{
		return conn.nativeSQL(sql);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException
	{
		conn.setAutoCommit(autoCommit);
	}

	public boolean getAutoCommit() throws SQLException
	{
		return conn.getAutoCommit();
	}

	public void commit() throws SQLException
	{
		conn.commit();
	}

	public void rollback() throws SQLException
	{
		conn.rollback();
	}

	public boolean isClosed() throws SQLException
	{
		return conn.isClosed();
	}

	public DatabaseMetaData getMetaData() throws SQLException
	{
		return conn.getMetaData();
	}

	public void setReadOnly(boolean readOnly) throws SQLException
	{
		conn.setReadOnly(readOnly);
	}

	public boolean isReadOnly() throws SQLException
	{
		return conn.isReadOnly();
	}

	public void setCatalog(String catalog) throws SQLException
	{
		conn.setCatalog(catalog);
	}

	public String getCatalog() throws SQLException
	{
		return conn.getCatalog();
	}

	public void setTransactionIsolation(int level) throws SQLException
	{
		conn.setTransactionIsolation(level);
	}

	public int getTransactionIsolation() throws SQLException
	{
		return conn.getTransactionIsolation();
	}

	public SQLWarning getWarnings() throws SQLException
	{
		return conn.getWarnings();
	}

	public void clearWarnings() throws SQLException
	{
		conn.clearWarnings();
	}

	public Map getTypeMap() throws SQLException
	{
		return conn.getTypeMap();
	}

	public PreparedStatement prepareStatement(String sql, int i, int a) throws SQLException
	{
		return new APreparedStatement(conn.prepareStatement(sql, i, a));
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
	{
		return new ACallableStatement(conn.prepareCall(sql, resultSetType, resultSetConcurrency));
	}

	public Statement createStatement(int i, int a) throws SQLException
	{
		return new AStatement(conn.createStatement(i, a));
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		return new AStatement(conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
	}

	public int getHoldability() throws SQLException
	{
		return conn.getHoldability();
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		return new ACallableStatement(conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException
	{
		return new APreparedStatement(conn.prepareStatement(sql, columnNames));
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException
	{
		return new APreparedStatement(conn.prepareStatement(sql, autoGeneratedKeys));
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException
	{
		return new APreparedStatement(conn.prepareStatement(sql, columnIndexes));
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		return new APreparedStatement(conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException
	{
		conn.releaseSavepoint(savepoint);
	}

	public void rollback(Savepoint savepoint) throws SQLException
	{
		conn.rollback(savepoint);
	}

	public void setHoldability(int holdability) throws SQLException
	{
		conn.setHoldability(holdability);
	}

	public Savepoint setSavepoint() throws SQLException
	{
		return conn.setSavepoint();
	}

	public Savepoint setSavepoint(String name) throws SQLException
	{
		return conn.setSavepoint(name);
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException
	{
		return null;
	}

	public Blob createBlob() throws SQLException
	{
		return null;
	}

	public Clob createClob() throws SQLException
	{
		return null;
	}

	public NClob createNClob() throws SQLException
	{
		return null;
	}

	public SQLXML createSQLXML() throws SQLException
	{
		return null;
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException
	{
		return null;
	}

	public Properties getClientInfo() throws SQLException
	{
		return null;
	}

	public String getClientInfo(String name) throws SQLException
	{
		return null;
	}

	public boolean isValid(int timeout) throws SQLException
	{
		return false;
	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException
	{
	}

	public void setClientInfo(String name, String value) throws SQLClientInfoException
	{
	}

	@Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return false;
    }

	@Override
    public <T> T unwrap(Class<T> arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

    public void abort(Executor arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

    public int getNetworkTimeout() throws SQLException
    {
	    // TODO Auto-generated method stub
	    return 0;
    }

    public String getSchema() throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

    public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

    public void setSchema(String arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

    public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

}