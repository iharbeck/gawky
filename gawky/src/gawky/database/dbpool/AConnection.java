package gawky.database.dbpool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;


public class AConnection implements Connection 
{
    private AConnectionPool pool;
    private Connection conn;
    private boolean inuse;
    private long timestamp;


    public AConnection(Connection conn, AConnectionPool pool)
    {
        this.conn=conn;
        this.pool=pool;
        this.inuse=false;
        this.timestamp=0;
    }

    /**
     * Check status of connection.
     *
     * @return false connection is currently in use,
     *         true connection is available status and timestamp is set.
     */

    public synchronized boolean lease()
    {
       if(inuse)  {
           return false;
       } else {
          inuse=true;
          timestamp=System.currentTimeMillis();
          return true;
       }
    }

    /**
     * Validate a connection.
     * @return <b>true</b> for active connection, <b>false</b> if we lost connection
     */

    public boolean validate()
    {
      //PreparedStatement pstmt = null;
      
      try
      {
      	conn.getMetaData();
      	//pstmt = conn.prepareStatement("select * from pp_user");
      	//pstmt.execute();
      } catch (Exception e) {
    	  return false;
      } finally {
    	//try { pstmt.close(); } catch(Exception e) {}
      }
      return true;
    }

    public boolean inUse() {
        return inuse;
    }

    public long getLastUse() {
        return timestamp;
    }

    public void close() throws SQLException {
        pool.returnConnection(this);
    }

    public void finalclose() throws SQLException {
        conn.close();
        //pool.returnConnection(this);
    }

    protected void expireLease()
    {
    	inuse=false;
    }

    protected Connection getConnection() {
        return conn;
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        return conn.prepareCall(sql);
    }

    public Statement createStatement() throws SQLException {
        return conn.createStatement();
    }

    public String nativeSQL(String sql) throws SQLException {
        return conn.nativeSQL(sql);
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        conn.setAutoCommit(autoCommit);
    }

    public boolean getAutoCommit() throws SQLException {
        return conn.getAutoCommit();
    }

    public void commit() throws SQLException {
        conn.commit();
    }

    public void rollback() throws SQLException {
        conn.rollback();
    }

    public boolean isClosed() throws SQLException {
        return conn.isClosed();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return conn.getMetaData();
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        conn.setReadOnly(readOnly);
    }

    public boolean isReadOnly() throws SQLException {
        return conn.isReadOnly();
    }

    public void setCatalog(String catalog) throws SQLException {
        conn.setCatalog(catalog);
    }

    public String getCatalog() throws SQLException {
        return conn.getCatalog();
    }

    public void setTransactionIsolation(int level) throws SQLException {
        conn.setTransactionIsolation(level);
    }

    public int getTransactionIsolation() throws SQLException {
        return conn.getTransactionIsolation();
    }

    public SQLWarning getWarnings() throws SQLException {
        return conn.getWarnings();
    }

    public void clearWarnings() throws SQLException {
        conn.clearWarnings();
    }

    public void setTypeMap(Map m) throws SQLException {
      conn.setTypeMap(m);
    }

    public Map getTypeMap() throws SQLException {
      return conn.getTypeMap();
    }

    public PreparedStatement prepareStatement(String sql, int i, int a) throws SQLException
    {
        return conn.prepareStatement(sql, i, a);
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
    {
      return conn.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    public Statement createStatement(int i, int a) throws SQLException
    {
        return conn.createStatement(i, a);
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }    

    public int getHoldability() throws SQLException {
        return getHoldability();
    }    

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return prepareStatement(sql, columnNames);
    }
    
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return prepareStatement(sql, autoGeneratedKeys);
    }
    
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return prepareStatement(sql, columnIndexes);
    }
    
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        releaseSavepoint(savepoint);
    }
    
    public void rollback(Savepoint savepoint) throws SQLException {
        rollback(savepoint);       
    }
    
    public void setHoldability(int holdability) throws SQLException {
        setHoldability(holdability);
    }
    
    public Savepoint setSavepoint() throws SQLException {
        return setSavepoint();
    }
    
    public Savepoint setSavepoint(String name) throws SQLException {
        return setSavepoint(name);
    }
    
}