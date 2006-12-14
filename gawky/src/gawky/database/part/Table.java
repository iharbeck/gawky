package gawky.database.part;

import gawky.database.DB;
import gawky.database.dialect.Dialect;
import gawky.database.dialect.MySQL;
import gawky.database.generator.Generator;
import gawky.database.generator.IDGenerator;
import gawky.message.Formatter;
import gawky.message.part.Desc;
import gawky.message.part.Part;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	private final class Staticdata 
	{
		public String[]            sql  = new String[5];
		public PreparedStatement[] stmt = new PreparedStatement[4];
		public boolean             parameter = false;
		public int                 descidindex;
		
		public Desc getDescId() { return getCachedDesc()[descidindex]; }
		
		IDGenerator idgenerator   = null;
	}
	
	private static Log log = LogFactory.getLog(Table.class);
	
	private static final int SQL_INSERT = 0;
	private static final int SQL_FIND   = 1;
	private static final int SQL_UPDATE = 2;
	private static final int SQL_DELETE = 3;
	private static final int SQL_SELECT = 4;
	
	public  static final int NO_ID = -1;
	
	static Generator generator = new Generator();

	Staticdata staticdata;
	
	public Staticdata getStaticdata() 
	{
		if(staticdata != null) 
			return staticdata;

		staticdata = (Staticdata)hmStatic.get(getClass());
		
		if(staticdata == null)
		{
			staticdata = new Staticdata();
			hmStatic.put(getClass(), staticdata);
		}

		return staticdata;
	}

	public void setParameter(boolean val) 
	{
		getStaticdata().parameter = val;
	}
	
	public boolean getParameter()
	{
		return getStaticdata().parameter;
	}
	
	abstract public Desc[] getDesc();
	abstract public String getTableName();
	
	Dialect dialect = new MySQL();

	private void store(int idindex, IDGenerator idgenerator) {
		getStaticdata().descidindex = idindex;
		getStaticdata().idgenerator = idgenerator;
	}
	
	public void setDescID(int idindex) {
		store(idindex, null);
	}
	
	public void setDescID(String idindex) {
		// multikey 
		store(NO_ID, null);
	}
	
	public void setDescID(IDGenerator idgenerator) {
		store(0, idgenerator);
	}
	
	public void setDescID(int idindex, IDGenerator idgenerator) {
		store(idindex, idgenerator);
	}
	
	public Desc getDescID() 
	{
		return getStaticdata().getDescId();
		// viel zu langsam
//		try {
//			idindex = ((Integer)hsDescID.get(key)).intValue();
//		} catch (Exception e) {
//			return null;
//		}
//		if(idindex == NO_ID)
//			return null;
//		
//		return getCachedDesc()[idindex];
	}
	
	private static HashMap hmStatic    = new HashMap(); 

	public final String[] getQueries() 
	{
		return getStaticdata().sql; 
	}
	
	public final PreparedStatement[] getStmts() 
	{
		return getStaticdata().stmt; 
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
	
	protected String getInsertSQL() {
		return Table.generator.generateInsertSQL(this);
	}
	
	protected String getUpdateSQL() {
		return Table.generator.generateUpdateSQL(this);
	}

	protected String getSelectSQL() {
		return Table.generator.generateSelectSQL(this);
	}

	protected String getFindSQL() {
		return Table.generator.generateFindSQL(this);
	}
	
	protected String getDeleteSQL() {
		return Table.generator.generateDeleteSQL(this);
	}
	
	protected void fillPreparedStatement(PreparedStatement stmt, boolean insert) {
		Table.generator.fillPreparedStatement(stmt, this, insert);
	}
	
	public void insert() throws SQLException 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			insert(conn);
		} finally {
			DB.doClose(conn);
		}
	}

	
	public void insert (Connection conn) throws SQLException 
	{
		String sql = getQueries()[SQL_INSERT];
		if(sql == null)	{
			sql = getInsertSQL();
			getQueries()[SQL_INSERT] = sql;
		}
		
		System.out.println(sql);
		
		PreparedStatement stmt = getStmt(conn, sql, SQL_INSERT);
		
		fillPreparedStatement(stmt, true);
		
		stmt.execute();
		
		try {
			// versuche to generierte ID zu ermitteln und im Object abzulegen
			if(getIdgenerator() != null)
				getDescID().setValue(this, getIdgenerator().getGeneratedID(conn, this));
		} catch (Exception e) {
			log.error("insert Record", e);
		}
	}
	
	public void queryToStream(Connection conn, String where, OutputStream out) throws Exception 
	{
		String sql = getQueries()[SQL_SELECT];
		if(sql == null)	{
			sql = getSelectSQL();
			getQueries()[SQL_SELECT] = sql;
		}
		
		PreparedStatement stmt = conn.prepareStatement(sql + " " + where);
		 
		ResultSet rset = stmt.executeQuery();
			
		//Desc[] descs = this.getOptDesc();  
		
		byte endline = '\n';

		while (rset.next())
		{
			Table table = (Table) this.getClass().newInstance();

			Table.generator.fillPart(rset, table);
			
//			Desc[] descs = this.getOptDesc(); 
//			for(int i=0; i < descs.length; i++)
//			{
//				descs[i].setValue(table, rset.getString(i+1));
//			}	
			
				
			//out.write(Formatter.getStringC(300, table.toString()).getBytes());
			out.write(table.toString().getBytes());
			out.write('\r');
			out.write(endline);
		}
		DB.doClose(stmt);
	}

	public void find(long id) throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			find(conn, id);
		} finally {
			DB.doClose(conn);
		}
	}

	public void find(String id) throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			find(conn, id);
		} finally {
			DB.doClose(conn);
		}
	}
	
	
	public void find(Connection conn, String id) throws Exception 
	{
		String sql = getQueries()[SQL_FIND];
		if(sql == null)	{
			sql = getFindSQL();
			getQueries()[SQL_FIND] = sql;
		}
		
		PreparedStatement stmt = getStmt(conn, sql, SQL_FIND);
		
		// Find by ID
		stmt.setString(1, id);
		ResultSet rset = stmt.executeQuery();
		
//		Desc[] descs = this.getOptDesc();  
		
		if (rset.next())
		{
//			for(int i=0; i < descs.length; i++)
//			{
//				descs[i].setValue(this, rset.getString(i+1) );
//			
			Table.generator.fillPart(rset, this);
			
		} else {
			log.error("no result (" + sql + ")");
		}
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
		
//		Desc[] descs = this.getOptDesc();  
		
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
//			for(int i=0; i < descs.length; i++)
//			{
				//descs[i].setValue(this, rset.getString(descs[i].dbname) );
				//descs[i].setValue(this, rset.getString(i+1) );
//			}
			
			Table.generator.fillPart(rset, this);
		} else {
			log.error("no result (" + sql + ")");
		}
	}
	
	public void find(String where, Object [] params) throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			find(conn, where, params);
		} finally {
			DB.doClose(conn);
		}
	}
	
	public void find(Connection conn, String where, Object [] params) throws Exception 
	{
		String sql = getQueries()[SQL_SELECT];
		if(sql == null)	{
			sql = getSelectSQL();
			getQueries()[SQL_SELECT] = sql;
		}
		
		sql += " " + where;

		PreparedStatement stmt = conn.prepareStatement(sql); // getStmt(conn, sql, SQL_FIND);
		
		for(int i = 1; params != null && i <= params.length; i++)
			stmt.setObject(i, params[i-1]);
		
		ResultSet rset = stmt.executeQuery();
		
//		Desc[] descs = this.getOptDesc();   
		
		if (rset.next())
		{
			Table.generator.fillPart(rset, this);
//			for(int i=0; i < descs.length; i++)
//			{
//				descs[i].setValue(this, rset.getString(i+1) );
//			}
		} else {
			log.error("no result (" + sql + ")");
		}
		
		DB.doClose(stmt);
	}
	
	/*
	 * static Find Methods
	 */
	
	public static Table find(Class clazz, long id) throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			return find(clazz, conn, id);
		} finally {
			DB.doClose(conn);
		}
	}

	public static Table find(Class clazz, String id) throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			return find(clazz, conn, id);
		} finally {
			DB.doClose(conn);
		}
	}

	
	public static Table find(Class clazz, Connection conn, long id) throws Exception 
	{
		Table inst = (Table)clazz.newInstance();
		
		String sql = inst.getQueries()[SQL_FIND];
		if(sql == null)	{
			sql = inst.getFindSQL();
			inst.getQueries()[SQL_FIND] = sql;
		}
		
		PreparedStatement stmt = inst.getStmt(conn, sql, SQL_FIND);
		
		// Find by ID
		stmt.setLong(1, id);
		ResultSet rset = stmt.executeQuery();
		
//		Desc[] descs = inst.getOptDesc();  
		
		if (rset.next())
		{
//			for(int i=0; i < descs.length; i++)
//			{
//				descs[i].setValue(inst, rset.getString(i+1) );
//			}
			Table.generator.fillPart(rset, inst);
		} else {
			log.error("no result (" + sql + ")");
		}
		
		return inst;
	}
	
	
	public static Table find(Class clazz, Connection conn, String id) throws Exception 
	{
		Table inst = (Table)clazz.newInstance();
		
		String sql = inst.getQueries()[SQL_FIND];
		if(sql == null)	{
			sql = inst.getFindSQL();
			inst.getQueries()[SQL_FIND] = sql;
		}
		
		PreparedStatement stmt = inst.getStmt(conn, sql, SQL_FIND);
		
		// Find by ID
		stmt.setString(1, id);
		ResultSet rset = stmt.executeQuery();
		
//		Desc[] descs = inst.getOptDesc();  
		
		if (rset.next())
		{
//			for(int i=0; i < descs.length; i++)
//			{
//				descs[i].setValue(inst, rset.getString(i+1) );
//			}
			Table.generator.fillPart(rset, inst);
			
		} else {
			log.error("no result (" + sql + ")");
		}
		
		return inst;
	}
	
	
	public static List find(Class clazz, String where, Object [] params) throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			return find(clazz, conn, where, params);
		} finally {
			DB.doClose(conn);
		}
	}
	
	public static List find(Class clazz, Connection conn, String where, Object [] params) throws Exception 
	{
		Table inst = (Table)clazz.newInstance();
		
		String sql = inst.getQueries()[SQL_SELECT];
		if(sql == null)	{
			sql = inst.getSelectSQL();
			inst.getQueries()[SQL_SELECT] = sql;
		}
		
		sql += " " + where;

		System.out.println(sql);
		
		PreparedStatement stmt = conn.prepareStatement(sql); // getStmt(conn, sql, SQL_FIND);
		
		for(int i = 1; params != null && i <= params.length; i++)
			stmt.setObject(i, params[i-1]);
			
		ResultSet rset = stmt.executeQuery();
		
//		Desc[] descs = inst.getOptDesc();   
		
		ArrayList list = new ArrayList();

		while (rset.next())
		{
//			for(int i=0; i < descs.length; i++)
//			{
//				descs[i].setValue(inst, rset.getString(i+1) );
//			}
			Table.generator.fillPart(rset, inst);
			
			list.add(inst);
			
			inst = (Table)clazz.newInstance();
		} 
		
		DB.doClose(stmt);
		
		return list;
	}
	
	public static void delete(Class clazz, long id) throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			delete(clazz, conn, id);
		} finally {
			DB.doClose(conn);
		}
	}
	
	public static void delete(Class clazz, Connection conn, long id) throws Exception 
	{
		Table inst = (Table)clazz.newInstance();
		
		String sql = inst.getQueries()[SQL_DELETE];
		if(sql == null)	{
			sql = inst.getDeleteSQL();
			inst.getQueries()[SQL_DELETE] = sql;
		}
		
		PreparedStatement stmt = inst.getStmt(conn, sql, SQL_DELETE);
		
		// Delete by ID
		stmt.setLong(1, id);
		stmt.execute();
	}

	public void delete() throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			delete(conn);
		} finally {
			DB.doClose(conn);
		}
	}

	
	public void delete(Connection conn) throws Exception 
	{
		// Delete current Object
		delete(getClass(), conn, Long.parseLong( getDescID().getValue(this) ));
	}
	
	public void update() throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			update(conn); 
		} finally {
			DB.doClose(conn);
		}
	}
	
	public void update (Connection conn) throws SQLException 
	{
		String sql = getQueries()[SQL_UPDATE];
		if(sql == null)	{
			sql = getUpdateSQL();
			getQueries()[SQL_UPDATE] = sql;
		}
		
		PreparedStatement stmt = getStmt(conn, sql, SQL_UPDATE);		

		fillPreparedStatement(stmt, false);
		
		stmt.execute();
	}
	
	public IDGenerator getIdgenerator() {
		return getStaticdata().idgenerator;
	}
	public Dialect getDialect() {
		return dialect;
	}
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}


}
