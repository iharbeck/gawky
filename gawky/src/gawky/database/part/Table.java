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
	int defaultconnection = 0;
	boolean found = false;
	
	private final class StaticLocal 
	{
		public String[]            sql  = new String[5];
		public PreparedStatement[] stmt = new PreparedStatement[4];
		public boolean             parameter = false;
		public int[]               descidindex;
		
		public StaticLocal() {
			descidindex = new int[1];
			descidindex[0] = NO_ID;
	
			descIds = new Desc[1];
			descIds[0] = null;
		}
		
		public Desc[] descIds = null;
		
		IDGenerator idgenerator   = null;
		
		Generator generator       = new Generator();
		Dialect dialect           = new MySQL();
	}
	
	public void descAfterInterceptor(Desc[] descs) { 

		StaticLocal local = getStaticLocal();
		local.descIds = new Desc[local.descidindex.length];

		for(int i=0; i < local.descidindex.length; i++)
		{
			if(local.descidindex[i] == NO_ID)
				local.descIds[i] = null;
			else
				local.descIds[i] = descs[local.descidindex[i]];
		}
		//getStaticLocal().descId = descs[getStaticLocal().descidindex];
	}
	
	private static Log log = LogFactory.getLog(Table.class);
	
	private static final int SQL_INSERT = 0;
	private static final int SQL_FIND   = 1;
	private static final int SQL_UPDATE = 2;
	private static final int SQL_DELETE = 3;
	private static final int SQL_SELECT = 4;
	
	public  static final int NO_ID = -1;
	
	//Instance Cache
	StaticLocal staticlocal;
	
	public final static StaticLocal getStaticLocal(Table bean) {
		return bean.getStaticLocal();
	}
	
	public final StaticLocal getStaticLocal() 
	{
		if(staticlocal != null) 
			return staticlocal;

		staticlocal = (StaticLocal)hmStaticLocal.get(getClass());
		
		if(staticlocal == null)
		{
			staticlocal = new StaticLocal();
			hmStaticLocal.put(getClass(), staticlocal);
		}

		return staticlocal;
	}

	public void setParameter(boolean val) 
	{
		getStaticLocal().parameter = val;
	}
	
	public boolean getParameter()
	{
		return getStaticLocal().parameter;
	}
	
	abstract public Desc[] getDesc();
	abstract public String getTableName();
	
	

	private void store(int idindex, IDGenerator idgenerator) 
	{
		StaticLocal local = getStaticLocal();
		
		local.descidindex = new int[1];
		local.descidindex[0] = idindex;
		local.idgenerator = idgenerator;
	}
	
	public void setDescID(int idindex) {
		store(idindex, null);
	}
	
	// Start Multikey 
	public void setDescID(int idindex1, int idindex2) {
		getStaticLocal().descidindex = new int[2];
		getStaticLocal().descidindex[0] = idindex1;
		getStaticLocal().descidindex[1] = idindex2;
	}
	public void setDescID(int idindex1, int idindex2, int idindex3) {
		getStaticLocal().descidindex = new int[3];
		getStaticLocal().descidindex[0] = idindex1;
		getStaticLocal().descidindex[1] = idindex2;
		getStaticLocal().descidindex[2] = idindex3;
	}
	public void setDescID(int idindex1, int idindex2, int idindex3, int idindex4) {
		getStaticLocal().descidindex = new int[4];
		getStaticLocal().descidindex[0] = idindex1;
		getStaticLocal().descidindex[1] = idindex2;
		getStaticLocal().descidindex[2] = idindex3;
		getStaticLocal().descidindex[3] = idindex4;
	}
	// Ende Multikey
	
	public void setDescID(IDGenerator idgenerator) {
		store(0, idgenerator);
	}
	
	public void setDescID(int idindex, IDGenerator idgenerator) {
		store(idindex, idgenerator);
	}
	
	public boolean isPrimary(Desc desc) {
		Desc[] descs = getStaticLocal().descIds;
		
		for(int i=0; i < descs.length; i++)
			if(descs[i] == desc)
				return true;
		return false;
	}
	
	public Desc[] getDescIDs() 
	{
		return getStaticLocal().descIds;
	}
	
	private static HashMap hmStaticLocal    = new HashMap(); 

	public final String[] getQueries() 
	{
		return getStaticLocal().sql; 
	}
	
	public final PreparedStatement[] getStmts() 
	{
		return getStaticLocal().stmt; 
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
		return getStaticLocal().generator.generateInsertSQL(this);
	}
	
	protected String getUpdateSQL() {
		return getStaticLocal().generator.generateUpdateSQL(this);
	}

	protected String getSelectSQL() {
		return getStaticLocal().generator.generateSelectSQL(this);
	}

	protected String getFindSQL() {
		return getStaticLocal().generator.generateFindSQL(this);
	}
	
	protected String getDeleteSQL() {
		return getStaticLocal().generator.generateDeleteSQL(this);
	}
	
	protected void fillPreparedStatement(PreparedStatement stmt, boolean insert) {
		getStaticLocal().generator.fillPreparedStatement(stmt, this, insert);
	}
	
	public int store() throws Exception 
	{
		if(isFound())
			return update();

		insert();
		return 1;
	}
	
	public int store(Connection conn) throws Exception 
	{
		if(isFound())
			return update(conn);
		
		insert(conn);
		return 1;
	}
	
	public void insert() throws SQLException 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection(defaultconnection);
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
				getDescIDs()[0].setValue(this, getIdgenerator().getGeneratedID(conn, this));
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
			
		byte endline = '\n';

		while (rset.next())
		{
			Table table = (Table) this.getClass().newInstance();

			getStaticLocal().generator.fillPart(rset, table);
			
			//out.write(Formatter.getStringC(300, table.toString()).getBytes());
			out.write(table.toString().getBytes());
			out.write('\r');
			out.write(endline);
		}
		DB.doClose(stmt);
	}

	public String getID() throws Exception 
	{
		getCachedDesc();
		Desc[] descids = getDescIDs();

		return descids[0].getValue(this); 
	}
	
	public void setID(String id) throws Exception 
	{
		getCachedDesc();
		Desc[] descids = getDescIDs();

		descids[0].setValue(this, id); 
	}
	
	public void find() throws Exception 
	{
		find(getID());
	}
	
	public void find(long id) throws Exception 
	{
		find(new Long(id));
	}

	public void find(Object id) throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection(defaultconnection);
			find(conn, id);
		} finally {
			DB.doClose(conn);
		}
	}
	
	public void find(Object id1, Object id2) throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection(defaultconnection);
			find(conn, id1, id2);
		} finally {
			DB.doClose(conn);
		}
	}
	
	public void find(Connection conn, long id) throws Exception 
	{
		find(conn, new Long(id));
	}

	public void find(Connection conn, Object id) throws Exception 
	{
		String sql = getQueries()[SQL_FIND];
		if(sql == null)	{
			sql = getFindSQL();
			getQueries()[SQL_FIND] = sql;
		}
		
		PreparedStatement stmt = getStmt(conn, sql, SQL_FIND);
		
		// Find by ID
		stmt.setObject(1, id);
		ResultSet rset = stmt.executeQuery();
		
		found = rset.next(); 
		if (found) {
			getStaticLocal().generator.fillPart(rset, this);
		} else {
			log.error("no result (" + sql + ")");
		}
	}

	public void find(Connection conn, Object id1, Object id2) throws Exception 
	{
		String sql = getQueries()[SQL_FIND];
		if(sql == null)	{
			sql = getFindSQL();
			getQueries()[SQL_FIND] = sql;
		}
		
		PreparedStatement stmt = getStmt(conn, sql, SQL_FIND);
		
		// Find by ID
		stmt.setObject(1, id1);
		stmt.setObject(2, id2);
		ResultSet rset = stmt.executeQuery();
		
		found = rset.next();
		if (found) {
			getStaticLocal().generator.fillPart(rset, this);
		} else {
			log.error("no result (" + sql + ")");
		}
	}

	
	public void find(String where, Object [] params) throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection(defaultconnection);
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
		
		found = rset.next();
		if (found) {
			getStaticLocal().generator.fillPart(rset, this);
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
		return find(clazz, new Long(id));
	}

	public static Table find(Class clazz, Object id) throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			return find(clazz, conn, id);
		} finally {
			DB.doClose(conn);
		}
	}
	
	public static Table find(Class clazz, Connection conn, Object id) throws Exception 
	{
		Table inst = (Table)clazz.newInstance();
		
		String sql = inst.getQueries()[SQL_FIND];
		if(sql == null)	{
			sql = inst.getFindSQL();
			inst.getQueries()[SQL_FIND] = sql;
		}
		
		PreparedStatement stmt = inst.getStmt(conn, sql, SQL_FIND);
		
		// Find by ID
		stmt.setObject(1, id);
		ResultSet rset = stmt.executeQuery();
		
		if (rset.next()) {
			getStaticLocal(inst).generator.fillPart(rset, inst);
		} else {
			log.error("no result (" + sql + ")");
		}
		
		return inst;
	}
	
	
//	public static Table find(Class clazz, Connection conn, Object id) throws Exception 
//	{
//		Table inst = (Table)clazz.newInstance();
//		
//		String sql = inst.getQueries()[SQL_FIND];
//		if(sql == null)	{
//			sql = inst.getFindSQL();
//			inst.getQueries()[SQL_FIND] = sql;
//		}
//		
//		PreparedStatement stmt = inst.getStmt(conn, sql, SQL_FIND);
//		
//		// Find by ID
//		stmt.setString(1, id);
//		ResultSet rset = stmt.executeQuery();
//		
////		Desc[] descs = inst.getOptDesc();  
//		
//		if (rset.next())
//		{
////			for(int i=0; i < descs.length; i++)
////			{
////				descs[i].setValue(inst, rset.getString(i+1) );
////			}
//			getStaticLocal(inst).generator.fillPart(rset, inst);
//			
//		} else {
//			log.error("no result (" + sql + ")");
//		}
//		
//		return inst;
//	}
	
	
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
			getStaticLocal(inst).generator.fillPart(rset, inst);
			
			list.add(inst);
			
			inst = (Table)clazz.newInstance();
		} 
		
		DB.doClose(stmt);
		
		return list;
	}
	
	public static int delete(Class clazz, long id) throws Exception 
	{
		return delete(clazz, new Long(id));
	}
	
	public static int delete(Class clazz, Object id) throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			return delete(clazz, conn, id);
		} finally {
			DB.doClose(conn);
		}
	}
	
	public static int delete(Class clazz, Connection conn, long id) throws Exception 
	{
		return delete(clazz, conn, new Long(id));
	}
	
	public static int delete(Class clazz, Connection conn, Object id) throws Exception 
	{
		Table inst = (Table)clazz.newInstance();
		
		String sql = inst.getQueries()[SQL_DELETE];
		if(sql == null)	{
			sql = inst.getDeleteSQL();
			inst.getQueries()[SQL_DELETE] = sql;
		}
		
		PreparedStatement stmt = inst.getStmt(conn, sql, SQL_DELETE);
		
		// Delete by ID
		stmt.setObject(1, id);
		return stmt.executeUpdate();
	}
	
	public static int delete_combinedid(Class clazz, Connection conn, Table table) throws Exception 
	{
		Table inst = (Table)clazz.newInstance();
		
		String sql = inst.getQueries()[SQL_DELETE];
		if(sql == null)	{
			sql = inst.getDeleteSQL();
			inst.getQueries()[SQL_DELETE] = sql;
		}
		
		PreparedStatement stmt = inst.getStmt(conn, sql, SQL_DELETE);
		
		// Delete by combined ID
		Desc[] descs = table.getDescIDs();
		
		for(int i=0; i < descs.length; i++)
			stmt.setObject(i+1, descs[i].getValue(table));
		
		return stmt.executeUpdate();
	}

	public int delete() throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection(defaultconnection);
			return delete(conn);
		} finally {
			DB.doClose(conn);
		}
	}

	
	public int delete(Connection conn) throws Exception 
	{
		// Delete current Object
		if(getDescIDs().length == 1)
			return delete(getClass(), conn, getDescIDs()[0].getValue(this) );
		else 
			return delete_combinedid(getClass(), conn, this);
	}
	
	public int update() throws Exception 
	{
		Connection conn = null;
		try {
			conn = DB.getConnection(defaultconnection);
			return update(conn); 
		} finally {
			DB.doClose(conn);
		}
	}
	
	public int update (Connection conn) throws SQLException 
	{
		String sql = getQueries()[SQL_UPDATE];
		if(sql == null)	{
			sql = getUpdateSQL();
			getQueries()[SQL_UPDATE] = sql;
		}
		
		PreparedStatement stmt = getStmt(conn, sql, SQL_UPDATE);		

		fillPreparedStatement(stmt, false);
		
		return stmt.executeUpdate();
	}
	
	public IDGenerator getIdgenerator() {
		return getStaticLocal().idgenerator;
	}
	public Dialect getDialect() {
		return getStaticLocal().dialect;
	}
	public void setDialect(Dialect dialect) {
		getStaticLocal().dialect = dialect;
	}

	public int getDefaultconnection() {
		return defaultconnection;
	}

	public void setDefaultconnection(int defaultconnection) {
		this.defaultconnection = defaultconnection;
	}

	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}
}
