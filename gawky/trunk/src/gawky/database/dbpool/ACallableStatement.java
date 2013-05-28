package gawky.database.dbpool;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
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
import java.util.Map;

public class ACallableStatement implements CallableStatement {

	CallableStatement stmt;
	
	public ACallableStatement(CallableStatement stmt) {
		this.stmt = stmt;
	}
	
	public Array getArray(int parameterIndex) throws SQLException {
		return stmt.getArray(parameterIndex);
	}

	public Array getArray(String parameterName) throws SQLException {
		return stmt.getArray(parameterName);
	}

	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		return stmt.getBigDecimal(parameterIndex);
	}

	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
		return stmt.getBigDecimal(parameterName);
	}

	public BigDecimal getBigDecimal(int parameterIndex, int scale)
			throws SQLException {
		return stmt.getBigDecimal(parameterIndex, scale);
	}

	public Blob getBlob(int parameterIndex) throws SQLException {
		return stmt.getBlob(parameterIndex);
	}

	public Blob getBlob(String parameterName) throws SQLException {
		return stmt.getBlob(parameterName);
	}

	public boolean getBoolean(int parameterIndex) throws SQLException {
		return stmt.getBoolean(parameterIndex);
	}

	public boolean getBoolean(String parameterName) throws SQLException {
		return stmt.getBoolean(parameterName);
	}

	public byte getByte(int parameterIndex) throws SQLException {
		return stmt.getByte(parameterIndex);
	}

	public byte getByte(String parameterName) throws SQLException {
		return stmt.getByte(parameterName);
	}

	public byte[] getBytes(int parameterIndex) throws SQLException {
		return stmt.getBytes(parameterIndex);
	}

	public byte[] getBytes(String parameterName) throws SQLException {
		return stmt.getBytes(parameterName);
	}

	public Reader getCharacterStream(int parameterIndex) throws SQLException {
		return stmt.getCharacterStream(parameterIndex);
	}

	public Reader getCharacterStream(String parameterName) throws SQLException {
		return stmt.getCharacterStream(parameterName);
	}

	public Clob getClob(int parameterIndex) throws SQLException {
		return stmt.getClob(parameterIndex);
	}

	public Clob getClob(String parameterName) throws SQLException {
		return stmt.getClob(parameterName);
	}

	public Date getDate(int parameterIndex) throws SQLException {
		return stmt.getDate(parameterIndex);
	}

	public Date getDate(String parameterName) throws SQLException {
		return stmt.getDate(parameterName);
	}

	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		return stmt.getDate(parameterIndex, cal);
	}

	public Date getDate(String parameterName, Calendar cal) throws SQLException {
		return stmt.getDate(parameterName, cal);
	}

	public double getDouble(int parameterIndex) throws SQLException {
		return stmt.getDouble(parameterIndex);
	}

	public double getDouble(String parameterName) throws SQLException {
		return stmt.getDouble(parameterName);
	}

	public float getFloat(int parameterIndex) throws SQLException {
		return stmt.getFloat(parameterIndex);
	}

	public float getFloat(String parameterName) throws SQLException {
		return stmt.getFloat(parameterName);
	}

	public int getInt(int parameterIndex) throws SQLException {
		return stmt.getInt(parameterIndex);
	}

	public int getInt(String parameterName) throws SQLException {
		return stmt.getInt(parameterName);
	}

	public long getLong(int parameterIndex) throws SQLException {
		return stmt.getLong(parameterIndex);
	}

	public long getLong(String parameterName) throws SQLException {
		return stmt.getLong(parameterName);
	}

	public Reader getNCharacterStream(int parameterIndex) throws SQLException {
		return stmt.getNCharacterStream(parameterIndex);
	}

	public Reader getNCharacterStream(String parameterName) throws SQLException {
		return stmt.getNCharacterStream(parameterName);
	}

	public NClob getNClob(int parameterIndex) throws SQLException {
		return stmt.getNClob(parameterIndex);
	}

	public NClob getNClob(String parameterName) throws SQLException {
		return stmt.getNClob(parameterName);
	}

	public String getNString(int parameterIndex) throws SQLException {
		return stmt.getNString(parameterIndex);
	}

	public String getNString(String parameterName) throws SQLException {
		return stmt.getNString(parameterName);
	}

	public Object getObject(int parameterIndex) throws SQLException {
		return stmt.getObject(parameterIndex);
	}

	public Object getObject(String parameterName) throws SQLException {
		return stmt.getObject(parameterName);
	}

	public Object getObject(int parameterIndex, Map<String, Class<?>> map)
			throws SQLException {
		return stmt.getObject(parameterIndex, map);
	}

	public Object getObject(String parameterName, Map<String, Class<?>> map)
			throws SQLException {
		return stmt.getObject(parameterName, map);
	}

	public Ref getRef(int parameterIndex) throws SQLException {
		return stmt.getRef(parameterIndex);
	}

	public Ref getRef(String parameterName) throws SQLException {
		return stmt.getRef(parameterName);
	}

	public RowId getRowId(int parameterIndex) throws SQLException {
		return stmt.getRowId(parameterIndex);
	}

	public RowId getRowId(String parameterName) throws SQLException {
		return stmt.getRowId(parameterName);
	}

	public SQLXML getSQLXML(int parameterIndex) throws SQLException {
		return stmt.getSQLXML(parameterIndex);
	}

	public SQLXML getSQLXML(String parameterName) throws SQLException {
		return stmt.getSQLXML(parameterName);
	}

	public short getShort(int parameterIndex) throws SQLException {
		return stmt.getShort(parameterIndex);
	}

	public short getShort(String parameterName) throws SQLException {
		return stmt.getShort(parameterName);
	}

	public String getString(int parameterIndex) throws SQLException {
		return stmt.getString(parameterIndex);
	}

	public String getString(String parameterName) throws SQLException {
		return stmt.getString(parameterName);
	}

	public Time getTime(int parameterIndex) throws SQLException {
		return stmt.getTime(parameterIndex);
	}

	public Time getTime(String parameterName) throws SQLException {
		return stmt.getTime(parameterName);
	}

	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		return stmt.getTime(parameterIndex, cal);
	}

	public Time getTime(String parameterName, Calendar cal) throws SQLException {
		return stmt.getTime(parameterName, cal);
	}

	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		return stmt.getTimestamp(parameterIndex);
	}

	public Timestamp getTimestamp(String parameterName) throws SQLException {
		return stmt.getTimestamp(parameterName);
	}

	public Timestamp getTimestamp(int parameterIndex, Calendar cal)
			throws SQLException {
		return stmt.getTimestamp(parameterIndex, cal);
	}

	public Timestamp getTimestamp(String parameterName, Calendar cal)
			throws SQLException {
		return stmt.getTimestamp(parameterName, cal);
	}

	public URL getURL(int parameterIndex) throws SQLException {
		return stmt.getURL(parameterIndex);
	}

	public URL getURL(String parameterName) throws SQLException {
		return stmt.getURL(parameterName);
	}

	public void registerOutParameter(int parameterIndex, int sqlType)
			throws SQLException {
		stmt.registerOutParameter(parameterIndex, sqlType);
	}

	public void registerOutParameter(String parameterName, int sqlType)
			throws SQLException {
		stmt.registerOutParameter(parameterName, sqlType);		
	}

	public void registerOutParameter(int parameterIndex, int sqlType, int scale)
			throws SQLException {
		stmt.registerOutParameter(parameterIndex, sqlType, scale);		
	}

	public void registerOutParameter(int parameterIndex, int sqlType,
			String typeName) throws SQLException {
		stmt.registerOutParameter(parameterIndex, sqlType, typeName);		
	}

	public void registerOutParameter(String parameterName, int sqlType,
			int scale) throws SQLException {
		stmt.registerOutParameter(parameterName, sqlType, scale);
	}

	public void registerOutParameter(String parameterName, int sqlType,
			String typeName) throws SQLException {
		stmt.registerOutParameter(parameterName, sqlType, typeName);		
	}

	public void setAsciiStream(String parameterName, InputStream x)
			throws SQLException {
		stmt.setAsciiStream(parameterName, x);		
	}

	public void setAsciiStream(String parameterName, InputStream x, int length)
			throws SQLException {
		stmt.setAsciiStream(parameterName, x, length);		
	}

	public void setAsciiStream(String parameterName, InputStream x, long length)
			throws SQLException {
		stmt.setAsciiStream(parameterName, x, length);		
	}

	public void setBigDecimal(String parameterName, BigDecimal x)
			throws SQLException {
		stmt.setBigDecimal(parameterName, x);		
	}

	public void setBinaryStream(String parameterName, InputStream x)
			throws SQLException {
		stmt.setBinaryStream(parameterName, x);		
	}

	public void setBinaryStream(String parameterName, InputStream x, int length)
			throws SQLException {
		stmt.setBinaryStream(parameterName, x, length);
	}

	public void setBinaryStream(String parameterName, InputStream x, long length)
			throws SQLException {
		stmt.setBinaryStream(parameterName, x, length);		
	}

	public void setBlob(String parameterName, Blob x) throws SQLException {
		stmt.setBlob(parameterName, x);		
	}

	public void setBlob(String parameterName, InputStream inputStream)
			throws SQLException {
		stmt.setBlob(parameterName, inputStream);		
	}

	public void setBlob(String parameterName, InputStream inputStream,
			long length) throws SQLException {
		stmt.setBlob(parameterName, inputStream, length);		
	}

	public void setBoolean(String parameterName, boolean x) throws SQLException {
		stmt.setBoolean(parameterName, x);		
	}

	public void setByte(String parameterName, byte x) throws SQLException {
		stmt.setByte(parameterName, x);
	}

	public void setBytes(String parameterName, byte[] x) throws SQLException {
		stmt.setBytes(parameterName, x);		
	}

	public void setCharacterStream(String parameterName, Reader reader)
			throws SQLException {
		stmt.setCharacterStream(parameterName, reader);		
	}

	public void setCharacterStream(String parameterName, Reader reader,
			int length) throws SQLException {
		stmt.setCharacterStream(parameterName, reader, length);		
	}

	public void setCharacterStream(String parameterName, Reader reader,
			long length) throws SQLException {
		stmt.setCharacterStream(parameterName, reader, length);		
	}

	public void setClob(String parameterName, Clob x) throws SQLException {
		stmt.setClob(parameterName, x);		
	}

	public void setClob(String parameterName, Reader reader)
			throws SQLException {
		stmt.setClob(parameterName, reader);		
	}

	public void setClob(String parameterName, Reader reader, long length)
			throws SQLException {
		stmt.setClob(parameterName, reader, length);		
	}

	public void setDate(String parameterName, Date x) throws SQLException {
		stmt.setDate(parameterName, x);		
	}

	public void setDate(String parameterName, Date x, Calendar cal)
			throws SQLException {
		stmt.setDate(parameterName, x, cal);		
	}

	public void setDouble(String parameterName, double x) throws SQLException {
		stmt.setDouble(parameterName, x);		
	}

	public void setFloat(String parameterName, float x) throws SQLException {
		stmt.setFloat(parameterName, x);		
	}

	public void setInt(String parameterName, int x) throws SQLException {
		stmt.setInt(parameterName, x);		
	}

	public void setLong(String parameterName, long x) throws SQLException {
		stmt.setLong(parameterName, x);		
	}

	public void setNCharacterStream(String parameterName, Reader value)
			throws SQLException {
		stmt.setNCharacterStream(parameterName, value);		
	}

	public void setNCharacterStream(String parameterName, Reader value,
			long length) throws SQLException {
		stmt.setNCharacterStream(parameterName, value, length);		
	}

	public void setNClob(String parameterName, NClob value) throws SQLException {
		stmt.setNClob(parameterName, value);		
	}

	public void setNClob(String parameterName, Reader reader)
			throws SQLException {
		stmt.setNClob(parameterName, reader);		
	}

	public void setNClob(String parameterName, Reader reader, long length)
			throws SQLException {
		stmt.setNClob(parameterName, reader, length);
	}

	public void setNString(String parameterName, String value)
			throws SQLException {
		stmt.setNString(parameterName, value);
	}

	public void setNull(String parameterName, int sqlType) throws SQLException {
		stmt.setNull(parameterName, sqlType);
	}

	public void setNull(String parameterName, int sqlType, String typeName)
			throws SQLException {
		stmt.setNull(parameterName, sqlType, typeName);
	}

	public void setObject(String parameterName, Object x) throws SQLException {
		stmt.setObject(parameterName, x);
	}

	public void setObject(String parameterName, Object x, int targetSqlType)
			throws SQLException {
		stmt.setObject(parameterName, x, targetSqlType);
	}

	public void setObject(String parameterName, Object x, int targetSqlType,
			int scale) throws SQLException {
		stmt.setObject(parameterName, x, targetSqlType, scale);
	}

	public void setRowId(String parameterName, RowId x) throws SQLException {
		stmt.setRowId(parameterName, x);
	}

	public void setSQLXML(String parameterName, SQLXML xmlObject)
			throws SQLException {
		stmt.setSQLXML(parameterName, xmlObject);
	}

	public void setShort(String parameterName, short x) throws SQLException {
		stmt.setShort(parameterName, x);
	}

	public void setString(String parameterName, String x) throws SQLException {
		stmt.setString(parameterName, x);
	}

	public void setTime(String parameterName, Time x) throws SQLException {
		stmt.setTime(parameterName, x);
	}

	public void setTime(String parameterName, Time x, Calendar cal)
			throws SQLException {
		stmt.setTime(parameterName, x, cal);
	}

	public void setTimestamp(String parameterName, Timestamp x)
			throws SQLException {
		stmt.setTimestamp(parameterName, x);
	}

	public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
			throws SQLException {
		stmt.setTimestamp(parameterName, x, cal);
	}

	public void setURL(String parameterName, URL val) throws SQLException {
		stmt.setURL(parameterName, val);
	}

	public boolean wasNull() throws SQLException {
		return stmt.wasNull();
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
		stmt.setDate(parameterIndex, x, cal);
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
		stmt.setObject(parameterIndex, x, targetSqlType);
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
		stmt.addBatch(sql);
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
		return stmt.execute(sql);		
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
		return stmt.executeUpdate();		
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

	@Override
    public <T> T getObject(int arg0, Class<T> arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public <T> T getObject(String arg0, Class<T> arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

}
