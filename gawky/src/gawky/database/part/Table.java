package gawky.database.part;

import gawky.database.DB;
import gawky.database.dialect.Dialect;
import gawky.database.dialect.MySQL;
import gawky.database.generator.Generator;
import gawky.database.generator.IDGenerator;
import gawky.message.part.Desc;
import gawky.message.part.Part;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Persistence Layer for Part based Objects
 * 
 * Insert / Update / Delete / Find
 * 
 * TODO query mit custom where
 * @author Ingo Harbeck
 *
 */

public abstract class Table extends Part 
{
	private static Log log = LogFactory.getLog(Table.class);
	
	private static final int SQL_INSERT = 0;
	private static final int SQL_FIND   = 1;
	private static final int SQL_UPDATE = 2;
	private static final int SQL_DELETE = 3;
	
	Generator generator = new Generator();
	
	int idindex = -1;
	IDGenerator idgenerator = null;
	
	abstract public Desc[] getDesc();
	abstract public String getTableName();
	
	Dialect dialect = new MySQL();

	public void setDescID(int idindex) {
		this.idindex = idindex;
		this.idgenerator = null;
	}
	
	public void setDescID(int idindex, IDGenerator idgenerator) {
		this.idindex = idindex;
		this.idgenerator = idgenerator;
	}
	
	public Desc getDescID() {
		if(idindex == -1)
			return null;
		return getCachedDesc()[idindex];
	}
	
	
	// Cache Query Arrays
	static HashMap hsQueries = new HashMap(); 
	static HashMap hsStmts   = new HashMap(); 
	
	public final String[] getQueries() 
	{
		String key = this.getClass().getName();
		String[] sql = (String[])hsQueries.get(key); 
		if(sql == null) {
			sql = new String[4];
			hsQueries.put(key, sql);
		}
		
		return sql;
	}
	
	public final PreparedStatement[] getStmts() 
	{
		String key = this.getClass().getName();
		PreparedStatement[] stmt = (PreparedStatement[])hsStmts.get(key); 
		if(stmt == null) {
			stmt = new PreparedStatement[4];
			hsStmts.put(key, stmt);
		}
		
		return stmt;
	}
	
	/**
	 * Read from or fill Cache
	 * @param conn
	 * @param sql
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public final PreparedStatement getStmt(Connection conn, String sql, int type) throws SQLException 
	{
		PreparedStatement stmt = getStmts()[type];		
		
		if(stmt == null || stmt.getConnection() != conn)
		{	
			try { stmt.close(); } catch (Exception e) {}
			stmt = conn.prepareStatement(sql);
			getStmts()[type] = stmt;
		}
		
		return stmt;
	}
	
	public String getInsertSQL() {
		return generator.generateInsertSQL(this);
	}
	
	public String getUpdateSQL() {
		return generator.generateUpdateSQL(this);
	}
	
	public String getFindSQL() {
		return generator.generateFindSQL(this);
	}
	
	public String getDeleteSQL() {
		return generator.generateDeleteSQL(this);
	}
	
	public void fillPreparedStatement(PreparedStatement stmt) {
		generator.fillPreparedStatement(stmt, this);
	}
	
	public void insert (Connection conn) throws SQLException 
	{
		String sql = getQueries()[SQL_INSERT];
		if(sql == null)	{
			sql = getInsertSQL();
			getQueries()[SQL_INSERT] = sql;
		}
		
		PreparedStatement stmt = getStmt(conn, sql, SQL_INSERT);
		
		fillPreparedStatement(stmt);
		
		stmt.execute();
		
		try {
			// versuche to generierte ID zu ermitteln und im Object abzulegen
			getDescID().setValue(this, getIdgenerator().getGeneratedID(conn, this));
//			PropertyUtils.setSimpleProperty(
//					this, 
//					this.getDescID().name, 
//					getIdgenerator().getGeneratedID(conn, this)
//			);
		} catch (Exception e) {
			log.error("insert Record", e);
		}
	}
	
	public void queryToStream(Connection conn, String where, OutputStream out) throws Exception 
	{
		PreparedStatement stmt = conn.prepareStatement("select * from " + getTableName() + " " + where);
		 
		ResultSet rset = stmt.executeQuery();
			
		Desc[] descs = this.getOptDesc();  
		
		byte endline = '\n';
		
		while (rset.next())
		{
			Part part = (Part) this.getClass().newInstance();

			for(int i=0; i < descs.length; i++)
			{
				descs[i].setValue(part, rset.getString(descs[i].dbname) );
				
			 	out.write(part.toString().getBytes());
			 	out.write(endline);
			}
		}
		DB.doClose(stmt);
	}

	public void find(Connection conn, long id) throws Exception 
	{
		String sql = getQueries()[SQL_FIND];
		if(sql == null)	{
			sql = getFindSQL();
			getQueries()[SQL_FIND] = sql;
		}
		
		PreparedStatement stmt = getStmt(conn, sql, SQL_FIND);
		
		// Find by ID
		stmt.setLong(1, id);
		ResultSet rset = stmt.executeQuery();
		
		Desc[] descs = this.getOptDesc();  
		
		if (rset.next())
		{
// RESULTSET BASED
//			ResultSetMetaData md = stmt.getMetaData();
			
//			for (int i = md.getColumnCount(); i > 0; i --) {
//				if(log.isInfoEnabled())
//					log.info(md.getColumnName(i) + " -- " + rset.getString(i));
//				
//				PropertyUtils.setSimpleProperty(this, md.getColumnName(i), rset.getString(i));
//			}
			
// OBJECT BASED TODO compare performance
// alternative generat
			for(int i=0; i < descs.length; i++)
			{
				//descs[i].setValue(this, rset.getString(descs[i].dbname) );
				descs[i].setValue(this, rset.getString(i+1) );
			}
			
		} else {
			log.error("no result (" + sql + ")");
		}
	}
	
	
	public void delete(Connection conn, long id) throws Exception 
	{
		String sql = getQueries()[SQL_DELETE];
		if(sql == null)	{
			sql = getDeleteSQL();
			getQueries()[SQL_DELETE] = sql;
		}
		
		PreparedStatement stmt = getStmt(conn, sql, SQL_DELETE);
		
		// Delete by ID
		stmt.setLong(1, id);
		stmt.execute();
	}

	public void delete(Connection conn) throws Exception 
	{
		// Delete current Object
		//delete(conn, Long.parseLong((String)PropertyUtils.getSimpleProperty(this, this.getDescID().name)));
		delete(conn, Long.parseLong( getDescID().getValue(this) ));
	}
	
	public void update (Connection conn) throws SQLException 
	{
		String sql = getQueries()[SQL_UPDATE];
		if(sql == null)	{
			sql = getUpdateSQL();
			getQueries()[SQL_UPDATE] = sql;
		}
		
		PreparedStatement stmt = getStmt(conn, sql, SQL_UPDATE);		

		fillPreparedStatement(stmt);
		
		stmt.execute();
	}
	
	public IDGenerator getIdgenerator() {
		return idgenerator;
	}
	public Dialect getDialect() {
		return dialect;
	}
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}
}
