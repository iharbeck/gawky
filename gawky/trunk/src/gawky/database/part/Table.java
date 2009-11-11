package gawky.database.part;

import gawky.database.DB;
import gawky.database.generator.Generator;
import gawky.database.generator.IDGenerator;
import gawky.database.generator.IDGeneratorAUTO;
import gawky.global.Constant;
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
		//public PreparedStatement[] stmt = new PreparedStatement[4];
		public boolean             parameter = false;

		public StaticLocal() {
			descIds = new Desc[1];
			descIds[0] = null;
		}

		public Desc[] descIds = null;

		//IDGenerator idgenerator   = null;

		Generator generator       = new Generator();
		int defaultconnection     = 0;
	}

	public void descAfterInterceptor(Desc[] descs) {

		StaticLocal local = getStaticLocal();

		// Anzahl Prim�rfelder
		int c=0;
		for(int i=0; i < descs.length; i++) {
			if(descs[i].isPrimary())
				c++;
		}
		
		// Array mit ids erstellen
		local.descIds = new Desc[c];
	
		for(int i=0, a=0; i < descs.length; i++) {
			if(descs[i].isPrimary()) {
				local.descIds[a] = descs[i];
				a++;
			}
		}
		
	}

	private static Log log = LogFactory.getLog(Table.class);

	public  static final int SQL_INSERT = 0;
	private static final int SQL_FIND   = 1;
	public  static final int SQL_UPDATE = 2;
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

	abstract protected Desc[] getDesc();
	abstract public String getTableName();


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

	protected final String[] getQueries()
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

	public final PreparedStatement getStmt(Connection conn, int type) throws SQLException
	{
		String sql = getQuery(type);
		return conn.prepareStatement(sql);
	}
	
	public final PreparedStatement getStmtScroll(Connection conn, int type) throws SQLException
	{
		String sql = getQuery(type);
		return conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
		PreparedStatement stmt = getStmt(conn, SQL_INSERT);

		try 
		{
			Desc prim = getPrimdesc();
			IDGenerator gen = null;
			
			if(prim != null)
			{
				gen = prim.getIDGenerator();
				if(gen != null) {
					try { 
						getPrimdesc().setValue(this, gen.nextVal(conn, this)); 
					} catch (Exception e) {
					}
				}
			}
			
			fillPreparedStatement(stmt, true);
			
			stmt.execute();
	
			if(gen instanceof IDGeneratorAUTO) {
				try { 
					getPrimdesc().setValue(this, gen.lastVal(conn, this)); 
				} catch (Exception e) {
				}
			}
			
/*
			try {
				// versuche to generierte ID zu ermitteln und im Object abzulegen
				if(getPrimdesc().getIDGenerator() != null)
					getPrimdesc().setValue(this, getPrimdesc().getIDGenerator().getGeneratedID(conn, this));
			} catch (Exception e) {
				log.error("insert Record", e);
			}
*/
			
			setFound(true);
		} finally {
			DB.doClose(stmt);
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
		ResultSet rset = null;
		
		try 
		{
			rset = stmt.executeQuery();
	
			byte endline = '\n';
	
			while (rset.next())
			{
				Table table = (Table) this.getClass().newInstance();
	
				getStaticLocal().generator.fillPart(rset, table);
	
				//out.write(Formatter.getStringC(300, table.buildBytes());
				out.write(table.buildBytes(encoding));
				out.write('\r');
				out.write(endline);
			}
		} finally {
			DB.doClose(rset);
			DB.doClose(stmt);
		}
	}

	Desc primdesc = null;
	
	public Desc getPrimdesc() 
	{
		if(primdesc == null && getDescIDs().length > 0)
			primdesc = getDescIDs()[0];

		return primdesc;	
	}
	
	public void find() throws Exception
	{
		getCachedDesc();
		Desc[] descids = getDescIDs();

		if(descids.length == 1)
			find(getPrimdesc().getValue(this));
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
		PreparedStatement stmt = getStmt(conn, SQL_FIND);
		ResultSet rset = null;
		try 
		{
			// Find by IDs
			for(int i=0; i < ids.length; i++)
			{
				if(ids[i] instanceof byte[])
					stmt.setBytes(i+1, (byte[])ids[i]);
				else
					stmt.setObject(i+1, ids[i]);
			}
			
			rset = stmt.executeQuery();
			rset.setFetchSize(1);
			
			found = rset.next();
			if (found) {
				getStaticLocal().generator.fillPart(rset, this);
			} else {
				log.error("no result (" + getQuery(SQL_FIND) + ")");
			}
		} finally {
			DB.doClose(rset);
			DB.doClose(stmt);
		}
	}
	

	public void findRow(int row) throws Exception
	{
		Connection conn = null;
		try {
			conn = DB.getConnection(getStaticLocal().defaultconnection);
			findRow(conn, row);
		} finally {
			DB.doClose(conn);
		}
	}
	
	public void findRow(Connection conn, int row) throws Exception
	{
		PreparedStatement stmt = getStmtScroll(conn, SQL_FIND);
		
		ResultSet rset = null;
		try 
		{
			rset = stmt.executeQuery();
			rset.setFetchSize(1);
			
			rset.next();
			found = rset.absolute(row);
			if (found) {
				getStaticLocal().generator.fillPart(rset, this);
			} else {
				log.error("no result (" + getQuery(SQL_FIND) + ")");
			}
		} finally {
			DB.doClose(rset);
			DB.doClose(stmt);
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
		{
			if(params[i-1] instanceof byte[])
				stmt.setBytes(i, (byte[])params[i-1]);
			else
				stmt.setObject(i, params[i-1]);
		}
		ResultSet rset = null;
		
		try {
			rset = stmt.executeQuery();
	
			found = rset.next();
			if (found) {
				getStaticLocal().generator.fillPart(rset, this);
			} else {
				log.error("no result (" + sql + ")");
			}
		} finally {
			DB.doClose(rset);
			DB.doClose(stmt);
		}
	}

	/*
	 * static Find Methods
	 */

	
	public static <T extends Table> T find(Class<T> clazz, long id) throws Exception 
//	public static Table find(Class clazz, long id) throws Exception
	{
		return find(clazz, new Long(id));
	}

	public static <T extends Table> T find(Class<T> clazz, Object id) throws Exception 
//	public static Table find(Class clazz, Object id) throws Exception
	{
		Connection conn = null;
		try {
			Table inst = (Table)clazz.newInstance();
			conn = DB.getConnection(inst.getStaticLocal().defaultconnection);
			return find(clazz, conn, id);
		} finally {
			DB.doClose(conn);
		}
	}

	public static <T extends Table> T find(Class<T> clazz, Connection conn, Object id) throws Exception 
//	public static Table find(Class clazz, Connection conn, Object id) throws Exception
	{
		T inst = clazz.newInstance();

		inst.find(conn, id);

		return inst;
	}

	public static <T extends Table> List<T> find(Class<T> clazz, String where, Object[] params) throws Exception
	{
		Connection conn = null;
		try {
			T inst = clazz.newInstance();
			conn = DB.getConnection(inst.getStaticLocal().defaultconnection);
			return find(clazz, conn, where, params);
		} finally {
			DB.doClose(conn);
		}
	}

	public static <T extends Table> List<T> find(Class<T> clazz, Connection conn, String where, Object[] params) throws Exception
	{
		T inst = clazz.newInstance();

		String sql = inst.getQuery(SQL_SELECT);

		if(where != null)
			sql += " " + where;

		PreparedStatement stmt = conn.prepareStatement(sql); // getStmt(conn, sql, SQL_FIND);

		
		
		for(int i=0; params != null && i < params.length; i++)
		{
			if(params[i] instanceof byte[])
				stmt.setBytes(i+1, (byte[])params[i]);
			else
				stmt.setObject(i+1, params[i]);
		}
		ResultSet rset = null;
		
		ArrayList list = new ArrayList();

		try 
		{
			rset = stmt.executeQuery();
			rset.setFetchSize(30);
			
			while (rset.next())
			{
				getStaticLocal(inst).generator.fillPart(rset, inst);
	
				list.add(inst);
	
				inst = clazz.newInstance();
			}
		} finally {
			DB.doClose(rset);
			DB.doClose(stmt);
		}
		
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
			Table inst = (Table)clazz.newInstance();
			conn = DB.getConnection(inst.getStaticLocal().defaultconnection);
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

		PreparedStatement stmt = inst.getStmt(conn, SQL_DELETE);

		try 
		{
			// Delete by ID
			for(int i=0; i < ids.length; i++)
			{
				if(ids[i] instanceof byte[])
					stmt.setBytes(i+1, (byte[])ids[i]);
				else
					stmt.setObject(i+1, ids[i]);
			}
	
			return stmt.executeUpdate();
		} finally {
			DB.doClose(stmt);
		}
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
		PreparedStatement stmt = getStmt(conn, SQL_DELETE);

		try 
		{
			// Delete by combined ID
			Desc[] descs = getDescIDs();
	
			for(int i=0; i < descs.length; i++)
				stmt.setObject(i+1, descs[i].getValue(this));
	
			return stmt.executeUpdate();
		} finally {
			DB.doClose(stmt);
		}
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
		
		PreparedStatement stmt = getStmt(conn, SQL_UPDATE);

		try 
		{
			fillPreparedStatement(stmt, false);
	
			return stmt.executeUpdate();
		} finally {
			DB.doClose(stmt);
		}
	}
	
	PreparedStatement batch_stmt;
	int batch_type;
	
	public void batch_init(Connection conn, int type) throws Exception {
		batch_type = type;
		batch_stmt = getStmt(conn, type);
	}

	public void batch_add() throws Exception {
		fillPreparedStatement(batch_stmt, batch_type == SQL_INSERT);
		batch_stmt.addBatch();
	}
	
	public void batch_add(String sql) throws Exception {
		batch_stmt.addBatch(sql);
	}
	
	public void batch_execute() throws Exception {
		batch_stmt.executeBatch();
		DB.doClose(batch_stmt);
	}
	
	

	public int getDefaultconnection() {
		return getStaticLocal().defaultconnection;
	}

	public void setDefaultconnection(int defaultconnection) {
		getStaticLocal().defaultconnection = defaultconnection;
	}

	public boolean isFound() {
		if(found)
			return true;
		
//		try {
//			String val = getPrimdesc().getValue(this);
//			return val != null && val.length() > 0;
//		} catch (Exception e) {
//		}
		
		return false;
	}

	public void setFound(boolean found) {
		this.found = found;
	}
	

	public static <T extends Table> List<T> query(Class<T> clazz, String sql, Object[] params) throws Exception
	{
		Connection conn = null;
		try {
			T inst = clazz.newInstance();
			conn = DB.getConnection(inst.getStaticLocal().defaultconnection);
			return query(clazz, conn, sql, params);
		} finally {
			DB.doClose(conn);
		}
	}
	
	public static <T extends Table> List<T> query(Class<T> clazz, Connection conn, String sql, Object[] params) throws Exception
	{
        ArrayList<T> list = new ArrayList<T>();

        PreparedStatement stmt = conn.prepareStatement(sql);
     
        ResultSet rset = null;
        
        try 
        {
        	rset = stmt.executeQuery(sql);
            
        	while(rset.next()) {
	   			T row = clazz.newInstance();                           
	            row.fillByResultSet(rset);
	            list.add(row);
            }
        	
        } finally {
        	DB.doClose(rset);
        	DB.doClose(stmt);
        }
              
        return list;
	}
	
}