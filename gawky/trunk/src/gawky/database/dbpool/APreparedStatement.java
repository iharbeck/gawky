package gawky.database.dbpool;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class APreparedStatement implements PreparedStatement {

	PreparedStatement stmt;
	
	public APreparedStatement(PreparedStatement stmt)
	{
		this.stmt = stmt;
	}
	
	public void addBatch() throws SQLException {
		stmt.addBatch();
	}

	public void clearParameters() throws SQLException {
		stmt.clearParameters();		
	}

	public boolean execute() throws SQLException {
		return stmt.execute();
	}

	public ResultSet executeQuery() throws SQLException {
		return stmt.executeQuery();
	}

	public int executeUpdate() throws SQLException {
		return stmt.executeUpdate();
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return stmt.getMetaData();
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		return stmt.getParameterMetaData();
	}

	public void setArray(int parameterIndex, Array x) throws SQLException {
		stmt.setArray(parameterIndex, x);
	}

	public void setAsciiStream(int parameterIndex, InputStream x)
			throws SQLException {
		stmt.setAsciiStream(parameterIndex, x);
	}

	public void setAsciiStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		stmt.setAsciiStream(parameterIndex, x, length);
	}

	public void setAsciiStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		stmt.setAsciiStream(parameterIndex, x, length);
	}

	public void setBigDecimal(int parameterIndex, BigDecimal x)
			throws SQLException {
		stmt.setBigDecimal(parameterIndex, x);
	}

	public void setBinaryStream(int parameterIndex, InputStream x)
			throws SQLException {
		stmt.setBinaryStream(parameterIndex, x); 
	}

	public void setBinaryStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		stmt.setBinaryStream(parameterIndex, x, length); 
	}

	public void setBinaryStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		stmt.setBinaryStream(parameterIndex, x, length); 
	}

	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		stmt.setBlob(parameterIndex, x); 
	}

	public void setBlob(int parameterIndex, InputStream inputStream)
			throws SQLException {
		stmt.setBlob(parameterIndex, inputStream); 
	}

	public void setBlob(int parameterIndex, InputStream inputStream, long length)
			throws SQLException {
		stmt.setBlob(parameterIndex, inputStream, length); 
	}

	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		stmt.setBoolean(parameterIndex, x);		
	}

	public void setByte(int parameterIndex, byte x) throws SQLException {
		stmt.setByte(parameterIndex, x); 
	}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		stmt.setBytes(parameterIndex, x); 
	}

	public void setCharacterStream(int parameterIndex, Reader reader)
			throws SQLException {
		stmt.setCharacterStream(parameterIndex, reader); 
	}

	public void setCharacterStream(int parameterIndex, Reader reader, int length)
			throws SQLException {
		stmt.setCharacterStream(parameterIndex, reader, length); 
	}

	public void setCharacterStream(int parameterIndex, Reader reader,
			long length) throws SQLException {
		stmt.setCharacterStream(parameterIndex, reader, length); 
	}

	public void setClob(int parameterIndex, Clob x) throws SQLException {
		stmt.setClob(parameterIndex, x); 
	}

	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		stmt.setClob(parameterIndex, reader); 
	}

	public void setClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		stmt.setClob(parameterIndex, reader, length); 
	}

	public void setDate(int parameterIndex, Date x) throws SQLException {
		stmt.setDate(parameterIndex, x); 
	}

	public void setDate(int parameterIndex, Date x, Calendar cal)
			throws SQLException {
		stmt.setDate(parameterIndex, x); 
	}

	public void setDouble(int parameterIndex, double x) throws SQLException {
		stmt.setDouble(parameterIndex, x); 
	}

	public void setFloat(int parameterIndex, float x) throws SQLException {
		stmt.setFloat(parameterIndex, x); 
	}

	public void setInt(int parameterIndex, int x) throws SQLException {
		stmt.setInt(parameterIndex, x); 
	}

	public void setLong(int parameterIndex, long x) throws SQLException {
		stmt.setLong(parameterIndex, x); 
	}

	public void setNCharacterStream(int parameterIndex, Reader value)
			throws SQLException {
		stmt.setNCharacterStream(parameterIndex, value); 
	}

	public void setNCharacterStream(int parameterIndex, Reader value,
			long length) throws SQLException {
		stmt.setNCharacterStream(parameterIndex, value, length); 
	}

	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		stmt.setNClob(parameterIndex, value); 
	}

	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		stmt.setNClob(parameterIndex, reader); 
	}

	public void setNClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		stmt.setNClob(parameterIndex, reader, length); 
	}

	public void setNString(int parameterIndex, String value)
			throws SQLException {
		stmt.setNString(parameterIndex, value); 
	}

	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		stmt.setNull(parameterIndex, sqlType); 
	}

	public void setNull(int parameterIndex, int sqlType, String typeName)
			throws SQLException {
		stmt.setNull(parameterIndex, sqlType, typeName); 
	}

	public void setObject(int parameterIndex, Object x) throws SQLException {
		stmt.setObject(parameterIndex, x); 		
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType)
			throws SQLException {
		stmt.setObject(parameterIndex, x); 
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType,
			int scaleOrLength) throws SQLException {
		stmt.setObject(parameterIndex, x, targetSqlType, scaleOrLength); 
	}

	public void setRef(int parameterIndex, Ref x) throws SQLException {
		stmt.setRef(parameterIndex, x);		
	}

	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		stmt.setRowId(parameterIndex, x);		
	}

	public void setSQLXML(int parameterIndex, SQLXML xmlObject)
			throws SQLException {
		stmt.setSQLXML(parameterIndex, xmlObject);		
	}

	public void setShort(int parameterIndex, short x) throws SQLException {
		stmt.setShort(parameterIndex, x);
	}

	public void setString(int parameterIndex, String x) throws SQLException {
		stmt.setString(parameterIndex, x);
	}

	public void setTime(int parameterIndex, Time x) throws SQLException {
		stmt.setTime(parameterIndex, x);		
	}

	public void setTime(int parameterIndex, Time x, Calendar cal)
			throws SQLException {
		stmt.setTime(parameterIndex, x, cal);		
	}

	public void setTimestamp(int parameterIndex, Timestamp x)
			throws SQLException {
		stmt.setTimestamp(parameterIndex, x);		
	}

	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
			throws SQLException {
		stmt.setTimestamp(parameterIndex, x, cal);		
	}

	public void setURL(int parameterIndex, URL x) throws SQLException {
		stmt.setURL(parameterIndex, x);		
	}

	@Deprecated
	public void setUnicodeStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		stmt.setUnicodeStream(parameterIndex, x, length);
	}

	public void addBatch(String sql) throws SQLException {
		stmt.addBatch();		
	}

	public void cancel() throws SQLException {
		stmt.cancel();		
	}

	public void clearBatch() throws SQLException {
		stmt.clearBatch();		
	}

	public void clearWarnings() throws SQLException {
		stmt.clearWarnings();		
	}

	public void close() throws SQLException {
		stmt.close();		
	}

	public boolean execute(String sql) throws SQLException {
		return stmt.execute();		
	}

	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		return stmt.execute(sql, autoGeneratedKeys);		
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		return stmt.execute(sql, columnIndexes);		
	}

	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		return stmt.execute(sql, columnNames);		
	}

	public int[] executeBatch() throws SQLException {
		return stmt.executeBatch();		
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		return stmt.executeQuery(sql);		
	}

	public int executeUpdate(String sql) throws SQLException {
		return stmt.executeUpdate(sql);		
	}

	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		return stmt.executeUpdate(sql, autoGeneratedKeys);		
	}

	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		return stmt.executeUpdate(sql, columnIndexes);		
	}

	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		return stmt.executeUpdate(sql, columnNames);		
	}

	public Connection getConnection() throws SQLException {
		return stmt.getConnection();		
	}

	public int getFetchDirection() throws SQLException {
		return stmt.getFetchDirection();		
	}

	public int getFetchSize() throws SQLException {
		return stmt.getFetchSize();		
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		return stmt.getGeneratedKeys();		
	}

	public int getMaxFieldSize() throws SQLException {
		return stmt.getMaxFieldSize();		
	}

	public int getMaxRows() throws SQLException {
		return stmt.getMaxRows();		
	}

	public boolean getMoreResults() throws SQLException {
		return stmt.getMoreResults();		
	}

	public boolean getMoreResults(int current) throws SQLException {
		return stmt.getMoreResults(current);		
	}

	public int getQueryTimeout() throws SQLException {
		return stmt.getQueryTimeout();		
	}

	public ResultSet getResultSet() throws SQLException {
		return stmt.getResultSet();		
	}

	public int getResultSetConcurrency() throws SQLException {
		return stmt.getResultSetConcurrency();		
	}

	public int getResultSetHoldability() throws SQLException {
		return stmt.getResultSetHoldability();		
	}

	public int getResultSetType() throws SQLException {
		return stmt.getResultSetType();		
	}

	public int getUpdateCount() throws SQLException {
		return stmt.getUpdateCount();		
	}

	public SQLWarning getWarnings() throws SQLException {
		return stmt.getWarnings();		
	}

	public boolean isClosed() throws SQLException {
		return stmt.isClosed();		
	}

	public boolean isPoolable() throws SQLException {
		return stmt.isPoolable();		
	}

	public void setCursorName(String name) throws SQLException {
		stmt.setCursorName(name);		
	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
		stmt.setEscapeProcessing(enable);		
	}

	public void setFetchDirection(int direction) throws SQLException {
		stmt.setFetchDirection(direction);		
	}

	public void setFetchSize(int rows) throws SQLException {
		stmt.setFetchSize(rows);
	}

	public void setMaxFieldSize(int max) throws SQLException {
		stmt.setMaxFieldSize(max);		
	}

	public void setMaxRows(int max) throws SQLException {
		stmt.setMaxRows(max);		
	}

	public void setPoolable(boolean poolable) throws SQLException {
		stmt.setPoolable(poolable);		
	}

	public void setQueryTimeout(int seconds) throws SQLException {
		stmt.setQueryTimeout(seconds);		
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return stmt.isWrapperFor(iface);		
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return stmt.unwrap(iface);		
	}

	@Override
    public void closeOnCompletion() throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public boolean isCloseOnCompletion() throws SQLException
    {
	    // TODO Auto-generated method stub
	    return false;
    }
}
