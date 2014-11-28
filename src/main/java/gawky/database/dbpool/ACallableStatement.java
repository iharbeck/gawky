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

public class ACallableStatement implements CallableStatement
{

	CallableStatement stmt;

	public ACallableStatement(CallableStatement stmt)
	{
		this.stmt = stmt;
	}

	@Override
	public Array getArray(int parameterIndex) throws SQLException
	{
		return stmt.getArray(parameterIndex);
	}

	@Override
	public Array getArray(String parameterName) throws SQLException
	{
		return stmt.getArray(parameterName);
	}

	@Override
	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException
	{
		return stmt.getBigDecimal(parameterIndex);
	}

	@Override
	public BigDecimal getBigDecimal(String parameterName) throws SQLException
	{
		return stmt.getBigDecimal(parameterName);
	}

	@Override
	public BigDecimal getBigDecimal(int parameterIndex, int scale)
	        throws SQLException
	{
		return stmt.getBigDecimal(parameterIndex, scale);
	}

	@Override
	public Blob getBlob(int parameterIndex) throws SQLException
	{
		return stmt.getBlob(parameterIndex);
	}

	@Override
	public Blob getBlob(String parameterName) throws SQLException
	{
		return stmt.getBlob(parameterName);
	}

	@Override
	public boolean getBoolean(int parameterIndex) throws SQLException
	{
		return stmt.getBoolean(parameterIndex);
	}

	@Override
	public boolean getBoolean(String parameterName) throws SQLException
	{
		return stmt.getBoolean(parameterName);
	}

	@Override
	public byte getByte(int parameterIndex) throws SQLException
	{
		return stmt.getByte(parameterIndex);
	}

	@Override
	public byte getByte(String parameterName) throws SQLException
	{
		return stmt.getByte(parameterName);
	}

	@Override
	public byte[] getBytes(int parameterIndex) throws SQLException
	{
		return stmt.getBytes(parameterIndex);
	}

	@Override
	public byte[] getBytes(String parameterName) throws SQLException
	{
		return stmt.getBytes(parameterName);
	}

	@Override
	public Reader getCharacterStream(int parameterIndex) throws SQLException
	{
		return stmt.getCharacterStream(parameterIndex);
	}

	@Override
	public Reader getCharacterStream(String parameterName) throws SQLException
	{
		return stmt.getCharacterStream(parameterName);
	}

	@Override
	public Clob getClob(int parameterIndex) throws SQLException
	{
		return stmt.getClob(parameterIndex);
	}

	@Override
	public Clob getClob(String parameterName) throws SQLException
	{
		return stmt.getClob(parameterName);
	}

	@Override
	public Date getDate(int parameterIndex) throws SQLException
	{
		return stmt.getDate(parameterIndex);
	}

	@Override
	public Date getDate(String parameterName) throws SQLException
	{
		return stmt.getDate(parameterName);
	}

	@Override
	public Date getDate(int parameterIndex, Calendar cal) throws SQLException
	{
		return stmt.getDate(parameterIndex, cal);
	}

	@Override
	public Date getDate(String parameterName, Calendar cal) throws SQLException
	{
		return stmt.getDate(parameterName, cal);
	}

	@Override
	public double getDouble(int parameterIndex) throws SQLException
	{
		return stmt.getDouble(parameterIndex);
	}

	@Override
	public double getDouble(String parameterName) throws SQLException
	{
		return stmt.getDouble(parameterName);
	}

	@Override
	public float getFloat(int parameterIndex) throws SQLException
	{
		return stmt.getFloat(parameterIndex);
	}

	@Override
	public float getFloat(String parameterName) throws SQLException
	{
		return stmt.getFloat(parameterName);
	}

	@Override
	public int getInt(int parameterIndex) throws SQLException
	{
		return stmt.getInt(parameterIndex);
	}

	@Override
	public int getInt(String parameterName) throws SQLException
	{
		return stmt.getInt(parameterName);
	}

	@Override
	public long getLong(int parameterIndex) throws SQLException
	{
		return stmt.getLong(parameterIndex);
	}

	@Override
	public long getLong(String parameterName) throws SQLException
	{
		return stmt.getLong(parameterName);
	}

	@Override
	public Reader getNCharacterStream(int parameterIndex) throws SQLException
	{
		return stmt.getNCharacterStream(parameterIndex);
	}

	@Override
	public Reader getNCharacterStream(String parameterName) throws SQLException
	{
		return stmt.getNCharacterStream(parameterName);
	}

	@Override
	public NClob getNClob(int parameterIndex) throws SQLException
	{
		return stmt.getNClob(parameterIndex);
	}

	@Override
	public NClob getNClob(String parameterName) throws SQLException
	{
		return stmt.getNClob(parameterName);
	}

	@Override
	public String getNString(int parameterIndex) throws SQLException
	{
		return stmt.getNString(parameterIndex);
	}

	@Override
	public String getNString(String parameterName) throws SQLException
	{
		return stmt.getNString(parameterName);
	}

	@Override
	public Object getObject(int parameterIndex) throws SQLException
	{
		return stmt.getObject(parameterIndex);
	}

	@Override
	public Object getObject(String parameterName) throws SQLException
	{
		return stmt.getObject(parameterName);
	}

	@Override
	public Object getObject(int parameterIndex, Map<String, Class<?>> map)
	        throws SQLException
	{
		return stmt.getObject(parameterIndex, map);
	}

	@Override
	public Object getObject(String parameterName, Map<String, Class<?>> map)
	        throws SQLException
	{
		return stmt.getObject(parameterName, map);
	}

	@Override
	public Ref getRef(int parameterIndex) throws SQLException
	{
		return stmt.getRef(parameterIndex);
	}

	@Override
	public Ref getRef(String parameterName) throws SQLException
	{
		return stmt.getRef(parameterName);
	}

	@Override
	public RowId getRowId(int parameterIndex) throws SQLException
	{
		return stmt.getRowId(parameterIndex);
	}

	@Override
	public RowId getRowId(String parameterName) throws SQLException
	{
		return stmt.getRowId(parameterName);
	}

	@Override
	public SQLXML getSQLXML(int parameterIndex) throws SQLException
	{
		return stmt.getSQLXML(parameterIndex);
	}

	@Override
	public SQLXML getSQLXML(String parameterName) throws SQLException
	{
		return stmt.getSQLXML(parameterName);
	}

	@Override
	public short getShort(int parameterIndex) throws SQLException
	{
		return stmt.getShort(parameterIndex);
	}

	@Override
	public short getShort(String parameterName) throws SQLException
	{
		return stmt.getShort(parameterName);
	}

	@Override
	public String getString(int parameterIndex) throws SQLException
	{
		return stmt.getString(parameterIndex);
	}

	@Override
	public String getString(String parameterName) throws SQLException
	{
		return stmt.getString(parameterName);
	}

	@Override
	public Time getTime(int parameterIndex) throws SQLException
	{
		return stmt.getTime(parameterIndex);
	}

	@Override
	public Time getTime(String parameterName) throws SQLException
	{
		return stmt.getTime(parameterName);
	}

	@Override
	public Time getTime(int parameterIndex, Calendar cal) throws SQLException
	{
		return stmt.getTime(parameterIndex, cal);
	}

	@Override
	public Time getTime(String parameterName, Calendar cal) throws SQLException
	{
		return stmt.getTime(parameterName, cal);
	}

	@Override
	public Timestamp getTimestamp(int parameterIndex) throws SQLException
	{
		return stmt.getTimestamp(parameterIndex);
	}

	@Override
	public Timestamp getTimestamp(String parameterName) throws SQLException
	{
		return stmt.getTimestamp(parameterName);
	}

	@Override
	public Timestamp getTimestamp(int parameterIndex, Calendar cal)
	        throws SQLException
	{
		return stmt.getTimestamp(parameterIndex, cal);
	}

	@Override
	public Timestamp getTimestamp(String parameterName, Calendar cal)
	        throws SQLException
	{
		return stmt.getTimestamp(parameterName, cal);
	}

	@Override
	public URL getURL(int parameterIndex) throws SQLException
	{
		return stmt.getURL(parameterIndex);
	}

	@Override
	public URL getURL(String parameterName) throws SQLException
	{
		return stmt.getURL(parameterName);
	}

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType)
	        throws SQLException
	{
		stmt.registerOutParameter(parameterIndex, sqlType);
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType)
	        throws SQLException
	{
		stmt.registerOutParameter(parameterName, sqlType);
	}

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType, int scale)
	        throws SQLException
	{
		stmt.registerOutParameter(parameterIndex, sqlType, scale);
	}

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType,
	        String typeName) throws SQLException
	{
		stmt.registerOutParameter(parameterIndex, sqlType, typeName);
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType,
	        int scale) throws SQLException
	{
		stmt.registerOutParameter(parameterName, sqlType, scale);
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType,
	        String typeName) throws SQLException
	{
		stmt.registerOutParameter(parameterName, sqlType, typeName);
	}

	@Override
	public void setAsciiStream(String parameterName, InputStream x)
	        throws SQLException
	{
		stmt.setAsciiStream(parameterName, x);
	}

	@Override
	public void setAsciiStream(String parameterName, InputStream x, int length)
	        throws SQLException
	{
		stmt.setAsciiStream(parameterName, x, length);
	}

	@Override
	public void setAsciiStream(String parameterName, InputStream x, long length)
	        throws SQLException
	{
		stmt.setAsciiStream(parameterName, x, length);
	}

	@Override
	public void setBigDecimal(String parameterName, BigDecimal x)
	        throws SQLException
	{
		stmt.setBigDecimal(parameterName, x);
	}

	@Override
	public void setBinaryStream(String parameterName, InputStream x)
	        throws SQLException
	{
		stmt.setBinaryStream(parameterName, x);
	}

	@Override
	public void setBinaryStream(String parameterName, InputStream x, int length)
	        throws SQLException
	{
		stmt.setBinaryStream(parameterName, x, length);
	}

	@Override
	public void setBinaryStream(String parameterName, InputStream x, long length)
	        throws SQLException
	{
		stmt.setBinaryStream(parameterName, x, length);
	}

	@Override
	public void setBlob(String parameterName, Blob x) throws SQLException
	{
		stmt.setBlob(parameterName, x);
	}

	@Override
	public void setBlob(String parameterName, InputStream inputStream)
	        throws SQLException
	{
		stmt.setBlob(parameterName, inputStream);
	}

	@Override
	public void setBlob(String parameterName, InputStream inputStream,
	        long length) throws SQLException
	{
		stmt.setBlob(parameterName, inputStream, length);
	}

	@Override
	public void setBoolean(String parameterName, boolean x) throws SQLException
	{
		stmt.setBoolean(parameterName, x);
	}

	@Override
	public void setByte(String parameterName, byte x) throws SQLException
	{
		stmt.setByte(parameterName, x);
	}

	@Override
	public void setBytes(String parameterName, byte[] x) throws SQLException
	{
		stmt.setBytes(parameterName, x);
	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader)
	        throws SQLException
	{
		stmt.setCharacterStream(parameterName, reader);
	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader,
	        int length) throws SQLException
	{
		stmt.setCharacterStream(parameterName, reader, length);
	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader,
	        long length) throws SQLException
	{
		stmt.setCharacterStream(parameterName, reader, length);
	}

	@Override
	public void setClob(String parameterName, Clob x) throws SQLException
	{
		stmt.setClob(parameterName, x);
	}

	@Override
	public void setClob(String parameterName, Reader reader)
	        throws SQLException
	{
		stmt.setClob(parameterName, reader);
	}

	@Override
	public void setClob(String parameterName, Reader reader, long length)
	        throws SQLException
	{
		stmt.setClob(parameterName, reader, length);
	}

	@Override
	public void setDate(String parameterName, Date x) throws SQLException
	{
		stmt.setDate(parameterName, x);
	}

	@Override
	public void setDate(String parameterName, Date x, Calendar cal)
	        throws SQLException
	{
		stmt.setDate(parameterName, x, cal);
	}

	@Override
	public void setDouble(String parameterName, double x) throws SQLException
	{
		stmt.setDouble(parameterName, x);
	}

	@Override
	public void setFloat(String parameterName, float x) throws SQLException
	{
		stmt.setFloat(parameterName, x);
	}

	@Override
	public void setInt(String parameterName, int x) throws SQLException
	{
		stmt.setInt(parameterName, x);
	}

	@Override
	public void setLong(String parameterName, long x) throws SQLException
	{
		stmt.setLong(parameterName, x);
	}

	@Override
	public void setNCharacterStream(String parameterName, Reader value)
	        throws SQLException
	{
		stmt.setNCharacterStream(parameterName, value);
	}

	@Override
	public void setNCharacterStream(String parameterName, Reader value,
	        long length) throws SQLException
	{
		stmt.setNCharacterStream(parameterName, value, length);
	}

	@Override
	public void setNClob(String parameterName, NClob value) throws SQLException
	{
		stmt.setNClob(parameterName, value);
	}

	@Override
	public void setNClob(String parameterName, Reader reader)
	        throws SQLException
	{
		stmt.setNClob(parameterName, reader);
	}

	@Override
	public void setNClob(String parameterName, Reader reader, long length)
	        throws SQLException
	{
		stmt.setNClob(parameterName, reader, length);
	}

	@Override
	public void setNString(String parameterName, String value)
	        throws SQLException
	{
		stmt.setNString(parameterName, value);
	}

	@Override
	public void setNull(String parameterName, int sqlType) throws SQLException
	{
		stmt.setNull(parameterName, sqlType);
	}

	@Override
	public void setNull(String parameterName, int sqlType, String typeName)
	        throws SQLException
	{
		stmt.setNull(parameterName, sqlType, typeName);
	}

	@Override
	public void setObject(String parameterName, Object x) throws SQLException
	{
		stmt.setObject(parameterName, x);
	}

	@Override
	public void setObject(String parameterName, Object x, int targetSqlType)
	        throws SQLException
	{
		stmt.setObject(parameterName, x, targetSqlType);
	}

	@Override
	public void setObject(String parameterName, Object x, int targetSqlType,
	        int scale) throws SQLException
	{
		stmt.setObject(parameterName, x, targetSqlType, scale);
	}

	@Override
	public void setRowId(String parameterName, RowId x) throws SQLException
	{
		stmt.setRowId(parameterName, x);
	}

	@Override
	public void setSQLXML(String parameterName, SQLXML xmlObject)
	        throws SQLException
	{
		stmt.setSQLXML(parameterName, xmlObject);
	}

	@Override
	public void setShort(String parameterName, short x) throws SQLException
	{
		stmt.setShort(parameterName, x);
	}

	@Override
	public void setString(String parameterName, String x) throws SQLException
	{
		stmt.setString(parameterName, x);
	}

	@Override
	public void setTime(String parameterName, Time x) throws SQLException
	{
		stmt.setTime(parameterName, x);
	}

	@Override
	public void setTime(String parameterName, Time x, Calendar cal)
	        throws SQLException
	{
		stmt.setTime(parameterName, x, cal);
	}

	@Override
	public void setTimestamp(String parameterName, Timestamp x)
	        throws SQLException
	{
		stmt.setTimestamp(parameterName, x);
	}

	@Override
	public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
	        throws SQLException
	{
		stmt.setTimestamp(parameterName, x, cal);
	}

	@Override
	public void setURL(String parameterName, URL val) throws SQLException
	{
		stmt.setURL(parameterName, val);
	}

	@Override
	public boolean wasNull() throws SQLException
	{
		return stmt.wasNull();
	}

	@Override
	public void addBatch() throws SQLException
	{
		stmt.addBatch();
	}

	@Override
	public void clearParameters() throws SQLException
	{
		stmt.clearParameters();
	}

	@Override
	public boolean execute() throws SQLException
	{
		return stmt.execute();
	}

	@Override
	public ResultSet executeQuery() throws SQLException
	{
		return stmt.executeQuery();
	}

	@Override
	public int executeUpdate() throws SQLException
	{
		return stmt.executeUpdate();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException
	{
		return stmt.getMetaData();
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException
	{
		return stmt.getParameterMetaData();
	}

	@Override
	public void setArray(int parameterIndex, Array x) throws SQLException
	{
		stmt.setArray(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x)
	        throws SQLException
	{
		stmt.setAsciiStream(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, int length)
	        throws SQLException
	{
		stmt.setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length)
	        throws SQLException
	{
		stmt.setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x)
	        throws SQLException
	{
		stmt.setBigDecimal(parameterIndex, x);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x)
	        throws SQLException
	{
		stmt.setBinaryStream(parameterIndex, x);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, int length)
	        throws SQLException
	{
		stmt.setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length)
	        throws SQLException
	{
		stmt.setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public void setBlob(int parameterIndex, Blob x) throws SQLException
	{
		stmt.setBlob(parameterIndex, x);
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream)
	        throws SQLException
	{
		stmt.setBlob(parameterIndex, inputStream);
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length)
	        throws SQLException
	{
		stmt.setBlob(parameterIndex, inputStream, length);
	}

	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException
	{
		stmt.setBoolean(parameterIndex, x);
	}

	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException
	{
		stmt.setByte(parameterIndex, x);
	}

	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SQLException
	{
		stmt.setBytes(parameterIndex, x);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader)
	        throws SQLException
	{
		stmt.setCharacterStream(parameterIndex, reader);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, int length)
	        throws SQLException
	{
		stmt.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader,
	        long length) throws SQLException
	{
		stmt.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setClob(int parameterIndex, Clob x) throws SQLException
	{
		stmt.setClob(parameterIndex, x);
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException
	{
		stmt.setClob(parameterIndex, reader);
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length)
	        throws SQLException
	{
		stmt.setClob(parameterIndex, reader, length);
	}

	@Override
	public void setDate(int parameterIndex, Date x) throws SQLException
	{
		stmt.setDate(parameterIndex, x);
	}

	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal)
	        throws SQLException
	{
		stmt.setDate(parameterIndex, x, cal);
	}

	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException
	{
		stmt.setDouble(parameterIndex, x);
	}

	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException
	{
		stmt.setFloat(parameterIndex, x);
	}

	@Override
	public void setInt(int parameterIndex, int x) throws SQLException
	{
		stmt.setInt(parameterIndex, x);
	}

	@Override
	public void setLong(int parameterIndex, long x) throws SQLException
	{
		stmt.setLong(parameterIndex, x);
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value)
	        throws SQLException
	{
		stmt.setNCharacterStream(parameterIndex, value);
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value,
	        long length) throws SQLException
	{
		stmt.setNCharacterStream(parameterIndex, value, length);
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException
	{
		stmt.setNClob(parameterIndex, value);
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException
	{
		stmt.setNClob(parameterIndex, reader);
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length)
	        throws SQLException
	{
		stmt.setNClob(parameterIndex, reader, length);
	}

	@Override
	public void setNString(int parameterIndex, String value)
	        throws SQLException
	{
		stmt.setNString(parameterIndex, value);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException
	{
		stmt.setNull(parameterIndex, sqlType);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName)
	        throws SQLException
	{
		stmt.setNull(parameterIndex, sqlType, typeName);
	}

	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException
	{
		stmt.setObject(parameterIndex, x);
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType)
	        throws SQLException
	{
		stmt.setObject(parameterIndex, x, targetSqlType);
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType,
	        int scaleOrLength) throws SQLException
	{
		stmt.setObject(parameterIndex, x, targetSqlType, scaleOrLength);

	}

	@Override
	public void setRef(int parameterIndex, Ref x) throws SQLException
	{
		stmt.setRef(parameterIndex, x);
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException
	{
		stmt.setRowId(parameterIndex, x);
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject)
	        throws SQLException
	{
		stmt.setSQLXML(parameterIndex, xmlObject);
	}

	@Override
	public void setShort(int parameterIndex, short x) throws SQLException
	{
		stmt.setShort(parameterIndex, x);
	}

	@Override
	public void setString(int parameterIndex, String x) throws SQLException
	{
		stmt.setString(parameterIndex, x);
	}

	@Override
	public void setTime(int parameterIndex, Time x) throws SQLException
	{
		stmt.setTime(parameterIndex, x);
	}

	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal)
	        throws SQLException
	{
		stmt.setTime(parameterIndex, x, cal);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x)
	        throws SQLException
	{
		stmt.setTimestamp(parameterIndex, x);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
	        throws SQLException
	{
		stmt.setTimestamp(parameterIndex, x, cal);
	}

	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException
	{
		stmt.setURL(parameterIndex, x);
	}

	@Override
	@Deprecated
	public void setUnicodeStream(int parameterIndex, InputStream x, int length)
	        throws SQLException
	{
		stmt.setUnicodeStream(parameterIndex, x, length);
	}

	@Override
	public void addBatch(String sql) throws SQLException
	{
		stmt.addBatch(sql);
	}

	@Override
	public void cancel() throws SQLException
	{
		stmt.cancel();
	}

	@Override
	public void clearBatch() throws SQLException
	{
		stmt.clearBatch();
	}

	@Override
	public void clearWarnings() throws SQLException
	{
		stmt.clearWarnings();
	}

	@Override
	public void close() throws SQLException
	{
		stmt.close();
	}

	@Override
	public boolean execute(String sql) throws SQLException
	{
		return stmt.execute(sql);
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys)
	        throws SQLException
	{
		return stmt.execute(sql, autoGeneratedKeys);
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException
	{
		return stmt.execute(sql, columnIndexes);
	}

	@Override
	public boolean execute(String sql, String[] columnNames)
	        throws SQLException
	{
		return stmt.execute(sql, columnNames);
	}

	@Override
	public int[] executeBatch() throws SQLException
	{
		return stmt.executeBatch();
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException
	{
		return stmt.executeQuery(sql);
	}

	@Override
	public int executeUpdate(String sql) throws SQLException
	{
		return stmt.executeUpdate(sql);
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys)
	        throws SQLException
	{
		return stmt.executeUpdate(sql, autoGeneratedKeys);
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes)
	        throws SQLException
	{
		return stmt.executeUpdate(sql, columnIndexes);
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames)
	        throws SQLException
	{
		return stmt.executeUpdate();
	}

	@Override
	public Connection getConnection() throws SQLException
	{
		return stmt.getConnection();
	}

	@Override
	public int getFetchDirection() throws SQLException
	{
		return stmt.getFetchDirection();
	}

	@Override
	public int getFetchSize() throws SQLException
	{
		return stmt.getFetchSize();
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException
	{
		return stmt.getGeneratedKeys();
	}

	@Override
	public int getMaxFieldSize() throws SQLException
	{
		return stmt.getMaxFieldSize();
	}

	@Override
	public int getMaxRows() throws SQLException
	{
		return stmt.getMaxRows();
	}

	@Override
	public boolean getMoreResults() throws SQLException
	{
		return stmt.getMoreResults();
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException
	{
		return stmt.getMoreResults(current);
	}

	@Override
	public int getQueryTimeout() throws SQLException
	{
		return stmt.getQueryTimeout();
	}

	@Override
	public ResultSet getResultSet() throws SQLException
	{
		return stmt.getResultSet();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException
	{
		return stmt.getResultSetConcurrency();
	}

	@Override
	public int getResultSetHoldability() throws SQLException
	{
		return stmt.getResultSetHoldability();
	}

	@Override
	public int getResultSetType() throws SQLException
	{
		return stmt.getResultSetType();
	}

	@Override
	public int getUpdateCount() throws SQLException
	{
		return stmt.getUpdateCount();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException
	{
		return stmt.getWarnings();
	}

	@Override
	public boolean isClosed() throws SQLException
	{
		return stmt.isClosed();
	}

	@Override
	public boolean isPoolable() throws SQLException
	{
		return stmt.isPoolable();
	}

	@Override
	public void setCursorName(String name) throws SQLException
	{
		stmt.setCursorName(name);
	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException
	{
		stmt.setEscapeProcessing(enable);
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException
	{
		stmt.setFetchDirection(direction);
	}

	@Override
	public void setFetchSize(int rows) throws SQLException
	{
		stmt.setFetchSize(rows);
	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException
	{
		stmt.setMaxFieldSize(max);
	}

	@Override
	public void setMaxRows(int max) throws SQLException
	{
		stmt.setMaxRows(max);
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException
	{
		stmt.setPoolable(poolable);
	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException
	{
		stmt.setQueryTimeout(seconds);

	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException
	{
		return stmt.isWrapperFor(iface);

	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException
	{
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
