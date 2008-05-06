package gawky.database.part;

import gawky.database.DB;
import gawky.database.dbpool.AConnection;
import gawky.database.dialect.Dialect;
import gawky.database.dialect.MySQL;
import gawky.database.generator.Generator;
import gawky.database.generator.IDGenerator;
import gawky.global.Constant;
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
 * Insert / Update / Delete / Find
 *
 * @author Ingo Harbeck
 */

public abstract class Table extends Part
{
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
		int defaultconnection     = 0;
	}

	public void descAfterInterceptor(Desc[] descs) {

		StaticLocal local = getStaticLocal();
		
		//**  über primary attribute ermittlen 
		//if(getStaticLocal().descIds[0] == null) 
		if(getStaticLocal().descidindex[0] == -1) 
		{
			int c=0;
			for(int i=0; i < descs.length; i++)
				if(descs[i].isPrimary())
					c++;
			
			getStaticLocal().descidindex = new int[c];
			for(int i=0; i < descs.length; i++)
				if(descs[i].isPrimary())
					getStaticLocal().descidindex[i] = i;
			
		}
		//**
		
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

	
	public void setIDGenerator(IDGenerator idgenerator) {
		StaticLocal local = getStaticLocal();
		local.idgenerator = idgenerator;
	}

	public final boolean isPrimary(Desc desc) {
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

	public final String getQuery(int type)
	{
		String sql = getQueries()[type];
		if(sql == null)	{
			switch (type) {
				case SQL_DELETE:
					sql = getDeleteSQL();
					break;
				case SQL_FIND:
					sql = getFindSQL();
					break;
				case SQL_INSERT:
					sql = getInsertSQL();
					break;
				case SQL_UPDATE:
					sql = getUpdateSQL();
					break;
				case SQL_SELECT:
					sql = getSelectSQL();
					break;
			}
			getQueries()[type] = sql;
		}

		return sql;
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

		Connection c = null;
		if(conn instanceof AConnection)
			c = ((AConnection)conn).getConnection();
		else
			c = conn;
		
		if(stmt == null || stmt.getConnection() != c)
		{
			try { stmt.close(); } catch (Exception e) {}
			stmt = conn.prepareStatement(sql);
			getStmts()[type] = stmt;
		}

		return stmt;
	}

	protected final String getInsertSQL() {
		return getStaticLocal().generator.generateInsertSQL(this).toString();
	}

	protected final String getUpdateSQL() {
		return getStaticLocal().generator.generateUpdateSQL(this).toString();
	}

	protected final String getSelectSQL() {
		return getStaticLocal().generator.generateSelectSQL(this).toString();
	}

	protected final String getFindSQL() {
		return getStaticLocal().generator.generateFindSQL(this).toString();
	}

	protected final String getDeleteSQL() {
		return getStaticLocal().generator.generateDeleteSQL(this).toString();
	}

	protected final void fillPreparedStatement(PreparedStatement stmt, boolean insert) {
		getStaticLocal().generator.fillPreparedStatement(stmt, this, insert);
	}

	public int store() throws Exception
	{
		if(isFound())
			return update();

		insert();

		setFound(true);

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
			conn = DB.getConnection(getStaticLocal().defaultconnection);
			insert(conn);
		} finally {
			DB.doClose(conn);
		}
	}


	public void insert (Connection conn) throws SQLException
	{
		String sql = getQuery(SQL_INSERT);

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
		queryToStream(conn, where, out, Constant.ENCODE_UTF8);
	}
	
	public void queryToStream(Connection conn, String where, OutputStream out, String encoding) throws Exception
	{
		String sql = getQuery(SQL_SELECT);

		PreparedStatement stmt = conn.prepareStatement(sql + " " + where);

		ResultSet rset = stmt.executeQuery();

		byte endline = '\n';

		while (rset.next())
		{
			Table table = (Table) this.getClass().newInstance();

			getStaticLocal().generator.fillPart(rset, table);

			//out.write(Formatter.getStringC(300, table.toString()).getBytes());
			out.write(table.toString().getBytes(encoding));
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
		getCachedDesc();
		Desc[] descids = getDescIDs();

		if(descids.length == 1)
			find(getID());
		else {
			Object[] val = new Object[descids.length];
			for(int i=0; i < descids.length; i++) {
				val[i] = descids[i].getValue(this);
			}
			find(val);
		}
	}

	public void find(long id) throws Exception
	{
		find(new Long(id));
	}

	public void find(Connection conn, long id) throws Exception
	{
		find(conn, new Long(id));
	}

	public void find(Object id) throws Exception
	{
		Connection conn = null;
		try {
			conn = DB.getConnection(getStaticLocal().defaultconnection);
			find(conn, id);
		} finally {
			DB.doClose(conn);
		}
	}

	public void find(Connection conn, Object id) throws Exception
	{
		find(conn, new Object[] {id});
	}

	public void find(Object ids[]) throws Exception
	{
		Connection conn = null;
		try {
			conn = DB.getConnection(getStaticLocal().defaultconnection);
			find(conn, ids);
		} finally {
			DB.doClose(conn);
		}
	}

	public void find(Connection conn, Object[] ids) throws Exception
	{
		String sql = getQuery(SQL_FIND);

		PreparedStatement stmt = getStmt(conn, sql, SQL_FIND);

		// Find by IDs
		for(int i=0; i < ids.length; i++)
			stmt.setObject(i+1, ids[i]);

		ResultSet rset = stmt.executeQuery();

		found = rset.next();
		if (found) {
			getStaticLocal().generator.fillPart(rset, this);
		} else {
			log.error("no result (" + sql + ")");
		}
	}
	
	public void fillByResultSet(ResultSet rset) { 
		try {
			getStaticLocal().generator.fillPartPartial(rset, this);
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	public void find(String where, Object[] params) throws Exception
	{
		Connection conn = null;
		try {
			conn = DB.getConnection(getStaticLocal().defaultconnection);
			find(conn, where, params);
		} finally {
			DB.doClose(conn);
		}
	}

	public void find(Connection conn, String where, Object[] params) throws Exception
	{
		String sql = getQuery(SQL_SELECT);

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

		inst.find(conn, id);

		return inst;
	}

	public static List find(Class clazz, String where, Object[] params) throws Exception
	{
		Connection conn = null;
		try {
			conn = DB.getConnection();
			return find(clazz, conn, where, params);
		} finally {
			DB.doClose(conn);
		}
	}

	public static List find(Class clazz, Connection conn, String where, Object[] params) throws Exception
	{
		Table inst = (Table)clazz.newInstance();

		String sql = inst.getQuery(SQL_SELECT);

		sql += " " + where;

		PreparedStatement stmt = conn.prepareStatement(sql); // getStmt(conn, sql, SQL_FIND);

		for(int i=0; params != null && i < params.length; i++)
			stmt.setObject(i+1, params[i]);

		ResultSet rset = stmt.executeQuery();

		ArrayList list = new ArrayList();

		while (rset.next())
		{
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
		return delete(clazz, conn, new Object[] { id } );
	}

	public static int delete(Class clazz, Connection conn, Object[] ids) throws Exception
	{
		Table inst = (Table)clazz.newInstance();

		String sql = inst.getQuery(SQL_DELETE);

		PreparedStatement stmt = inst.getStmt(conn, sql, SQL_DELETE);

		// Delete by ID
		for(int i=0; i < ids.length; i++)
			stmt.setObject(i+1, ids[i]);

		return stmt.executeUpdate();
	}

	public int delete() throws Exception
	{
		Connection conn = null;
		try {
			conn = DB.getConnection(getStaticLocal().defaultconnection);
			return delete(conn);
		} finally {
			DB.doClose(conn);
		}
	}


	public int delete(Connection conn) throws Exception
	{
		String sql = getQuery(SQL_DELETE);

		PreparedStatement stmt = getStmt(conn, sql, SQL_DELETE);

		// Delete by combined ID
		Desc[] descs = getDescIDs();

		for(int i=0; i < descs.length; i++)
			stmt.setObject(i+1, descs[i].getValue(this));

		return stmt.executeUpdate();
	}

	public int update() throws Exception
	{
		Connection conn = null;
		try {
			conn = DB.getConnection(getStaticLocal().defaultconnection);
			return update(conn);
		} finally {
			DB.doClose(conn);
		}
	}

	public int update (Connection conn) throws SQLException
	{
		if(!isDirty())
			return 0;
		
		String sql = getQuery(SQL_UPDATE);

		PreparedStatement stmt = getStmt(conn, sql, SQL_UPDATE);

		fillPreparedStatement(stmt, false);

		return stmt.executeUpdate();
	}
	
	public void update_batch (Connection conn) throws SQLException
	{
		if(!isDirty())
			return;

		String sql = getQuery(SQL_UPDATE);

		PreparedStatement stmt = getStmt(conn, sql, SQL_UPDATE);

		fillPreparedStatement(stmt, false);

		stmt.addBatch();
	}
	
	public void execute_updatebatch (Connection conn) throws SQLException
	{
		PreparedStatement stmt = getStmt(conn, "", SQL_UPDATE);
		stmt.executeBatch();
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
		return getStaticLocal().defaultconnection;
	}

	public void setDefaultconnection(int defaultconnection) {
		getStaticLocal().defaultconnection = defaultconnection;
	}

	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}
}
