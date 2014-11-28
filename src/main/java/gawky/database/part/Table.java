package gawky.database.part;

import gawky.database.DB;
import gawky.database.generator.Generator;
import gawky.database.generator.IDGenerator;
import gawky.database.generator.IDGeneratorAUTO;
import gawky.global.Constant;
import gawky.message.part.Desc;
import gawky.message.part.Part;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.LogFactory;

/**
 * Persistence Layer for Part based Objects Insert / Update / Delete / Find
 * 
 * @author Ingo Harbeck
 */

public abstract class Table extends Part
{
	boolean found = false;
	final static int INITIALCAP = 2000;

	private final class StaticLocal
	{
		public String[] sql = new String[6];
		public boolean parameter = false;

		public String nativecolumns;
		public String nativevalues;

		public StaticLocal()
		{
			descIds = new Desc[1];
			descIds[0] = null;
		}

		public Desc[] descIds = null;

		//IDGenerator idgenerator   = null;

		Generator generator = new Generator();
		int defaultconnection = 0;

		boolean primarydefined = false;
		public boolean binary = false;
	}

	private Connection getConnection() throws Exception
	{
		if(!loop)
		{
			return DB.getConnection(getStaticLocal().defaultconnection);
		}
		else
		{
			return this.conn;
		}
	}

	static HashMap<String, Integer> hsConn = new HashMap<String, Integer>();

	public static <T extends Table> Connection getConnection(Class<T> clazz) throws Exception
	{
		String key = clazz.getName();
		Integer connid = hsConn.get(key);

		if(connid == null)
		{
			Table inst = clazz.newInstance();
			connid = inst.getStaticLocal().defaultconnection;
			hsConn.put(key, connid);
		}

		return DB.getConnection(connid);
	}

	private final void doClose(Connection conn)
	{
		if(!loop)
		{
			DB.doClose(conn);
		}
	}

	private final void doClose(ResultSet rset)
	{
		DB.doClose(rset);
	}

	private final void doClose(Statement stmt)
	{
		if(!loop)
		{
			DB.doClose(stmt);
		}
	}

	private final static void doSClose(Connection conn)
	{
		DB.doClose(conn);
	}

	private final static void doSClose(ResultSet rset)
	{
		DB.doClose(rset);
	}

	private final static void doSClose(Statement stmt)
	{
		DB.doClose(stmt);
	}

	// SQL Generator
	public void buildTableCreate()
	{
		System.out.println(Generator.generateCreateSQL(this).toString());
	}

	public void buildTableAlter()
	{
		System.out.println(Generator.generateAlterSQL(this).toString());
	}

	@Override
	public synchronized void descAfterInterceptor(Desc[] descs)
	{
		StaticLocal local = getStaticLocal();

		if(local.descIds[0] != null)
		{
			return;
		}

		// Anzahl Prim�rfelder
		int c = 0;
		for(Desc desc : descs)
		{
			if(desc.isPrimary())
			{
				c++;
			}
		}

		if(c > 0)
		{
			local.primarydefined = true;
		}

		// Array mit ids erstellen
		local.descIds = new Desc[c];

		for(int i = 0, a = 0; i < descs.length; i++)
		{
			if(descs[i].isPrimary())
			{
				local.descIds[a] = descs[i];
				a++;
			}
		}

	}

	public static final int SQL_INSERT = 0;
	public static final int SQL_FIND = 1;
	public static final int SQL_UPDATE = 2;
	public static final int SQL_DELETE = 3;
	public static final int SQL_SELECT = 4;
	public static final int SQL_MERGE = 5;

	public static final int NO_ID = -1;

	//Instance Cache
	StaticLocal staticlocal;

	private final static StaticLocal getStaticLocal(Table bean)
	{
		return bean.getStaticLocal();
	}

	private final StaticLocal getStaticLocal()
	{
		if(staticlocal != null)
		{
			return staticlocal;
		}

		String key = getClass().getName();

		staticlocal = hmStaticLocal.get(key);

		if(staticlocal == null)
		{
			synchronized(hmStaticLocal)
			{
				if(!hmStaticLocal.containsKey(key))
				{
					staticlocal = new StaticLocal();
					hmStaticLocal.put(key, staticlocal);
				}
				else
				{
					staticlocal = hmStaticLocal.get(key);
				}
			}
		}

		return staticlocal;
	}

	public void setBinary(boolean val)
	{
		getStaticLocal().binary = val;
	}

	public boolean isBinary()
	{
		return getStaticLocal().binary;
	}

	public void setParameter(boolean val)
	{
		getStaticLocal().parameter = val;
	}

	public boolean getParameter()
	{
		return getStaticLocal().parameter;
	}

	@Override
	abstract protected Desc[] getDesc();

	abstract public String getTableName();

	public Desc[] getDescIDs()
	{
		return getStaticLocal().descIds;
	}

	private static volatile HashMap<String, StaticLocal> hmStaticLocal = new HashMap<String, StaticLocal>();

	protected final String[] getQueries()
	{
		return getStaticLocal().sql;
	}

	protected final String getQuery(int type)
	{
		String sql = getQueries()[type];

		if(sql == null)
		{
			switch(type)
			{
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
				case SQL_MERGE:
					sql = getMergeSQL();
					break;
			}
			getQueries()[type] = sql;
		}

		return sql;
	}

	public final PreparedStatement getStmt(Connection conn, int type) throws Exception
	{
		if(loop && this.stmt[type] != null)
		{
			return this.stmt[type];
		}

		String sql = getQuery(type);

		PreparedStatement stmt = conn.prepareStatement(sql);

		// Store statement in loop mode
		if(loop)
		{
			this.stmt[type] = stmt;
		}

		return stmt;
	}

	public final PreparedStatement getStmtScroll(Connection conn, int type) throws Exception
	{
		String sql = getQuery(type);
		return conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}

	protected final String getInsertSQL()
	{
		return getStaticLocal().generator.generateInsertSQL(this).toString();
	}

	protected final String getUpdateSQL()
	{
		return getStaticLocal().generator.generateUpdateSQL(this).toString();
	}

	protected final String getSelectSQL()
	{
		return getStaticLocal().generator.generateSelectSQL(this).toString();
	}

	protected final String getMergeSQL()
	{
		return getStaticLocal().generator.generateMergeSQL(this).toString();
	}

	protected final String getFindSQL()
	{
		return getStaticLocal().generator.generateFindSQL(this).toString();
	}

	protected final String getDeleteSQL()
	{
		return getStaticLocal().generator.generateDeleteSQL(this).toString();
	}

	protected final void fillPreparedStatement(PreparedStatement stmt) throws Exception
	{
		getStaticLocal().generator.fillPreparedStatement(stmt, this);
	}

	public int store() throws Exception
	{
		if(isFound())
		{
			return update();
		}

		insert();

		setFound(true);

		return 1;
	}

	public int store(Connection conn) throws Exception
	{
		if(isFound())
		{
			return update(conn);
		}

		insert(conn);
		return 1;
	}

	public void merge() throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			merge(conn);
		}
		finally
		{
			doClose(conn);
		}
	}

	public void merge(Connection conn) throws Exception
	{
		PreparedStatement stmt = getStmt(conn, SQL_MERGE);

		/*
		 * 
		 * 
		 * MERGE INTO oak_translation USING dual ON ( locale='de' and messagekey = 'ingotest' ) WHEN MATCHED THEN
		 * 
		 * UPDATE SET value='ingo2' --, value2='ingo'
		 * 
		 * WHEN NOT MATCHED THEN
		 * 
		 * INSERT (locale, messagekey, value) VALUES ('de','ingotest', 'ingo')
		 */

		try
		{
			Desc prim = getPrimdesc();
			IDGenerator gen = null;

			if(prim != null)
			{
				gen = prim.getIDGenerator();
				if(gen != null)
				{
					try
					{
						prim.setValue(this, gen.nextVal(conn, this));
					}
					catch(Exception e)
					{
					}
				}
			}

			fillPreparedStatement(stmt);

			stmt.execute();

			if(gen instanceof IDGeneratorAUTO)
			{
				try
				{
					prim.setValue(this, gen.lastVal(conn, this));
				}
				catch(Exception e)
				{
				}
			}

			/*
			 * try { // versuche to generierte ID zu ermitteln und im Object abzulegen if(getPrimdesc().getIDGenerator() != null) getPrimdesc().setValue(this, getPrimdesc().getIDGenerator().getGeneratedID(conn, this)); } catch (Exception e) { log.error("insert Record", e); }
			 */

			setFound(true);
		}
		finally
		{
			doClose(stmt);
		}
	}

	public void insert() throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			insert(conn);
		}
		finally
		{
			doClose(conn);
		}
	}

	public void insert(Connection conn) throws Exception
	{
		PreparedStatement stmt = getStmt(conn, SQL_INSERT);

		try
		{
			Desc prim = getPrimdesc();
			IDGenerator gen = null;

			if(prim != null)
			{
				gen = prim.getIDGenerator();
				if(gen != null)
				{
					try
					{
						prim.setValue(this, gen.nextVal(conn, this));
					}
					catch(Exception e)
					{
					}
				}
			}

			fillPreparedStatement(stmt);

			stmt.execute();

			if(gen instanceof IDGeneratorAUTO)
			{
				try
				{
					prim.setValue(this, gen.lastVal(conn, this));
				}
				catch(Exception e)
				{
				}
			}

			/*
			 * try { // versuche to generierte ID zu ermitteln und im Object abzulegen if(getPrimdesc().getIDGenerator() != null) getPrimdesc().setValue(this, getPrimdesc().getIDGenerator().getGeneratedID(conn, this)); } catch (Exception e) { log.error("insert Record", e); }
			 */

			setFound(true);
		}
		finally
		{
			doClose(stmt);
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

			Generator generator = getStaticLocal(this).generator;

			while(rset.next())
			{
				Table table = this.getClass().newInstance();

				generator.fillPart(rset, table);

				//out.write(Formatter.getStringC(300, table.buildBytes());
				out.write(table.buildBytes(encoding));
				out.write('\r');
				out.write(endline);
			}
		}
		finally
		{
			doClose(rset);
			doClose(stmt);
		}
	}

	Desc primdesc = null;

	public Desc getPrimdesc()
	{
		if(primdesc == null && getDescIDs().length > 0)
		{
			primdesc = getDescIDs()[0];
		}

		return primdesc;
	}

	public void find() throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			find(conn);
		}
		finally
		{
			doClose(conn);
		}
	}

	public void find(Connection conn) throws Exception
	{
		getCachedDesc();
		Desc[] descids = getDescIDs();

		if(descids.length == 1)
		{
			find(conn, getPrimdesc().getValue(this));
		}
		else
		{
			Object[] val = new Object[descids.length];
			for(int i = 0; i < descids.length; i++)
			{
				val[i] = descids[i].getValue(this);
			}
			find(conn, val);
		}
	}

	public void find(Object id) throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			find(conn, id);
		}
		finally
		{
			doClose(conn);
		}
	}

	public void find(Connection conn, Object id) throws Exception
	{
		find(conn, new Object[] { id });
	}

	public void find(Object ids[]) throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			find(conn, ids);
		}
		finally
		{
			doClose(conn);
		}
	}

	Connection conn = null;
	PreparedStatement stmt[] = null;
	boolean loop = false;

	public void loop_init() throws Exception
	{
		loop_init(getConnection());
	}

	public void loop_init(Connection conn) throws Exception
	{
		loop = true;
		this.conn = conn;

		stmt = new PreparedStatement[6];
	}

	public void loop_exit()
	{
		loop_exit(true);
	}

	public void loop_exit(boolean close)
	{
		loop = false;
		for(PreparedStatement element : stmt)
		{
			doClose(element);
		}

		if(close)
		{
			doClose(this.conn);
		}
	}

	public void find(Connection conn, Object[] ids) throws Exception
	{
		PreparedStatement stmt = getStmt(conn, SQL_FIND);

		if(!this.hasPrimary())
		{
			throw new NoPrimaryColumnException(this);
		}

		ResultSet rset = null;
		try
		{
			// Find by IDs
			fillParameter(stmt, ids);

			rset = stmt.executeQuery();
			rset.setFetchSize(1);

			found = rset.next();

			if(found)
			{
				getStaticLocal().generator.fillPart(rset, this);
			}
			else
			{
				LogFactory.getLog(this.getClass()).warn("no result (" + getQuery(SQL_FIND) + ")");
			}
		}
		finally
		{
			doClose(rset);
			doClose(stmt);
		}
	}

	public void findRow(int row) throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			findRow(conn, row);
		}
		finally
		{
			doClose(conn);
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

			if(found)
			{
				getStaticLocal().generator.fillPart(rset, this);
			}
			else
			{
				LogFactory.getLog(this.getClass()).warn("no result (" + getQuery(SQL_FIND) + ")");
			}
		}
		finally
		{
			doClose(rset);
			doClose(stmt);
		}
	}

	public void fillByResultSet(ResultSet rset)
	{
		try
		{
			getStaticLocal().generator.fillPartPartial(rset, this);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void find(String where, Object[] params) throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			find(conn, where, params);
		}
		finally
		{
			doClose(conn);
		}
	}

	public void find(Connection conn, String where, Object[] params) throws Exception
	{
		String sql = getQuery(SQL_SELECT);

		sql += " " + where;

		PreparedStatement stmt = conn.prepareStatement(sql); // getStmt(conn, sql, SQL_FIND);

		fillParameter(stmt, params);

		ResultSet rset = null;

		try
		{
			rset = stmt.executeQuery();

			found = rset.next();

			if(found)
			{
				getStaticLocal().generator.fillPart(rset, this);
			}
			else
			{
				LogFactory.getLog(this.getClass()).warn("no result (" + sql + ")");
			}
		}
		finally
		{
			doClose(rset);
			doClose(stmt);
		}
	}

	/*
	 * static Find Methods
	 */

	public static <T extends Table> T find(Class<T> clazz, Object id) throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection(clazz);
			return find(clazz, conn, id);
		}
		finally
		{
			doSClose(conn);
		}
	}

	public static <T extends Table> T find(Class<T> clazz, Object[] id) throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection(clazz);
			return find(clazz, conn, id);
		}
		finally
		{
			doSClose(conn);
		}
	}

	public static <T extends Table> T find(Class<T> clazz, Connection conn, Object id) throws Exception
	{
		T inst = clazz.newInstance();

		inst.find(conn, new Object[] { id });

		return inst;
	}

	public static <T extends Table> T find(Class<T> clazz, Connection conn, Object[] id) throws Exception
	{
		T inst = clazz.newInstance();

		inst.find(conn, id);

		return inst;
	}

	public static <T extends Table> List<T> find(Class<T> clazz, String where, Object[] params) throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection(clazz);
			return find(clazz, conn, where, params);
		}
		finally
		{
			doSClose(conn);
		}
	}

	public static <T extends Table> List<T> find(Class<T> clazz, Connection conn, String where, Object[] params) throws Exception
	{
		T inst = clazz.newInstance();

		String sql = inst.getQuery(SQL_SELECT);

		if(where != null)
		{
			sql += " " + where;
		}

		PreparedStatement stmt = conn.prepareStatement(sql); // getStmt(conn, sql, SQL_FIND);

		fillParameter(stmt, params);

		ResultSet rset = null;

		List<T> list = new ArrayList<T>(INITIALCAP);

		try
		{
			rset = stmt.executeQuery();
			//rset.setFetchSize(100);

			Generator generator = getStaticLocal(inst).generator;

			while(rset.next())
			{
				generator.fillPart(rset, inst);

				list.add(inst);

				inst = clazz.newInstance();
			}
		}
		finally
		{
			doSClose(rset);
			doSClose(stmt);
		}

		return list;
	}

	public static <T extends Table> void find(Class<T> clazz, Connection conn, String where, Object[] params, TableProcessor resulter) throws Exception
	{
		T inst = clazz.newInstance();

		String sql = inst.getQuery(SQL_SELECT);

		if(where != null)
		{
			sql += " " + where;
		}

		PreparedStatement stmt = conn.prepareStatement(sql); // getStmt(conn, sql, SQL_FIND);

		fillParameter(stmt, params);

		ResultSet rset = null;

		try
		{
			rset = stmt.executeQuery();
			//rset.setFetchSize(100);

			Generator generator = getStaticLocal(inst).generator;

			while(rset.next())
			{
				generator.fillPart(rset, inst);

				resulter.process(inst);
			}
		}
		finally
		{
			doSClose(rset);
			doSClose(stmt);
		}
	}

	public static <T extends Table> int delete(Class<T> clazz, Object id) throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection(clazz);
			return delete(clazz, conn, id);
		}
		finally
		{
			doSClose(conn);
		}
	}

	public static <T extends Table> int delete(Class<T> clazz, Connection conn, Object id) throws Exception
	{
		return delete(clazz, conn, new Object[] { id });
	}

	public static <T extends Table> int delete(Class<T> clazz, Connection conn, Object[] ids) throws Exception
	{
		Table inst = clazz.newInstance();

		PreparedStatement stmt = inst.getStmt(conn, SQL_DELETE);

		if(!inst.hasPrimary())
		{
			throw new NoPrimaryColumnException(inst);
		}

		try
		{
			// Delete by ID
			fillParameter(stmt, ids);

			return stmt.executeUpdate();
		}
		finally
		{
			doSClose(stmt);
		}
	}

	public int delete() throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			return delete(conn);
		}
		finally
		{
			doClose(conn);
		}
	}

	public int delete(Connection conn) throws Exception
	{
		PreparedStatement stmt = getStmt(conn, SQL_DELETE);

		try
		{
			// Delete by combined ID
			Desc[] descs = getDescIDs();

			for(int i = 0; i < descs.length; i++)
			{
				if(descs[i].getValue(this) instanceof byte[])
				{
					stmt.setBytes(i + 1, (byte[])descs[i].getValue(this));
				}
				else
				{
					stmt.setObject(i + 1, descs[i].getValue(this));
				}
			}

			return stmt.executeUpdate();
		}
		finally
		{
			doClose(stmt);
		}
	}

	public int update() throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection();
			return update(conn);
		}
		finally
		{
			doClose(conn);
		}
	}

	public int update(Connection conn) throws Exception
	{
		//		if(!isDirty())
		//			return 0;

		PreparedStatement stmt = getStmt(conn, SQL_UPDATE);

		if(!this.hasPrimary())
		{
			throw new NoPrimaryColumnException(this);
		}

		try
		{
			fillPreparedStatement(stmt);

			return stmt.executeUpdate();
		}
		finally
		{
			doClose(stmt);
		}
	}

	PreparedStatement batch_stmt;
	int batch_type;

	public void batch_init(Connection conn) throws Exception
	{
		batch_init(conn, SQL_INSERT);
	}

	public void batch_init(Connection conn, int type) throws Exception
	{
		batch_type = type;
		batch_stmt = getStmt(conn, type);

	}

	public void batch_add_insert() throws Exception
	{
		batch_add();
	}

	public void batch_add_update() throws Exception
	{
		batch_add();
	}

	public void batch_add() throws Exception
	{
		fillPreparedStatement(batch_stmt);
		batch_stmt.addBatch();
	}

	public void batch_add(String sql) throws Exception
	{
		batch_stmt.addBatch(sql);
	}

	public void batch_close() throws Exception
	{
		doClose(batch_stmt);
	}

	public void batch_execute() throws Exception
	{
		batch_stmt.executeBatch();
	}

	public void batch_clear() throws Exception
	{
		batch_stmt.clearBatch();
	}

	public int getDefaultconnection()
	{
		return getStaticLocal().defaultconnection;
	}

	public void setDefaultconnection(int defaultconnection)
	{
		getStaticLocal().defaultconnection = defaultconnection;
	}

	public boolean isFound()
	{
		return found;
	}

	public void setFound(boolean found)
	{
		this.found = found;
	}

	public static <T extends Table> List<T> query(Class<T> clazz, String sql, Object[] params) throws Exception
	{
		Connection conn = null;
		try
		{
			conn = getConnection(clazz);
			return query(clazz, conn, sql, params);
		}
		finally
		{
			doSClose(conn);
		}
	}

	public final static void fillParameter(PreparedStatement stmt, Object[] params) throws Exception
	{
		for(int i = 0; params != null && i < params.length; i++)
		{
			if(params[i] instanceof byte[])
			{
				stmt.setBytes(i + 1, (byte[])params[i]);
			}
			else
			{
				stmt.setObject(i + 1, params[i]);
			}
		}
	}

	public static <T extends Table> List<T> query(Class<T> clazz, Connection conn, String sql, Object[] params) throws Exception
	{
		ArrayList<T> list = new ArrayList<T>(INITIALCAP);

		PreparedStatement stmt = conn.prepareStatement(sql);

		fillParameter(stmt, params);

		ResultSet rset = null;

		try
		{
			rset = stmt.executeQuery(sql);

			while(rset.next())
			{
				T row = clazz.newInstance();
				row.fillByResultSet(rset);
				list.add(row);
			}

		}
		finally
		{
			doSClose(rset);
			doSClose(stmt);
		}

		return list;
	}

	public boolean hasPrimary()
	{
		return getStaticLocal().primarydefined;
	}

	public void addNative(String column, String value)
	{
		StaticLocal staticlocal = getStaticLocal();

		if(staticlocal.nativecolumns == null)
		{
			staticlocal.nativecolumns = column + ",";
			staticlocal.nativevalues = value + ",";
		}
		else
		{
			staticlocal.nativecolumns += column + ",";
			staticlocal.nativevalues += value + ",";
		}
	}

	public void clearNative()
	{
		StaticLocal staticlocal = getStaticLocal();

		staticlocal.nativecolumns = null;
		staticlocal.nativevalues = null;
	}

	public String lookupNativeColumns()
	{
		return getStaticLocal().nativecolumns;
	}

	public String lookupNativeValues()
	{
		return getStaticLocal().nativevalues;
	}

	public static <T extends Table> void batchinsert(List<T> list, int batchsize) throws Exception
	{
		batchinsert(0, list, batchsize);
	}

	public static <T extends Table> void batchinsert(int db, List<T> list, int batchsize) throws Exception
	{
		Connection conn = DB.getConnection(db);

		batchinsert(conn, list, batchsize);

		DB.doClose(conn);
	}

	public static <T extends Table> void batchinsert(Connection conn, List<T> list, int batchsize) throws Exception
	{
		int size = list.size();

		if(size == 0)
		{
			return;
		}

		Generator sqlGenerator = new Generator();

		Table element = list.get(0);

		PreparedStatement batch_stmt = element.getStmt(conn, Table.SQL_INSERT);

		boolean batchrows = false;

		for(int i = 0; i < size; i++)
		{
			sqlGenerator.fillPreparedStatement(batch_stmt, list.get(i));
			batch_stmt.addBatch();
			batchrows = true;

			if(i > 0 && i % batchsize == 0)
			{
				batch_stmt.executeBatch();
				batchrows = false;
			}
		}

		if(batchrows)
		{
			batch_stmt.executeBatch();
		}

		batch_stmt.close();
	}

	public static <T extends Table> void batchupdate(List<T> list, int batchsize) throws Exception
	{
		batchupdate(0, list, batchsize);
	}

	public static <T extends Table> void batchupdate(int db, List<T> list, int batchsize) throws Exception
	{
		Connection conn = DB.getConnection(db);

		batchupdate(conn, list, batchsize);

		DB.doClose(conn);
	}

	public static <T extends Table> void batchupdate(Connection conn, List<T> list, int batchsize) throws Exception
	{
		int size = list.size();

		if(size == 0)
		{
			return;
		}

		Generator sqlGenerator = new Generator();

		Table element = list.get(0);

		PreparedStatement batch_stmt = element.getStmt(conn, Table.SQL_UPDATE);

		boolean batchrows = false;

		for(int i = 0; i < size; i++)
		{
			sqlGenerator.fillPreparedStatement(batch_stmt, list.get(i));
			batch_stmt.addBatch();

			batchrows = true;

			if(i > 0 && i % batchsize == 0)
			{
				batch_stmt.executeBatch();
				batchrows = false;
			}
		}

		if(batchrows)
		{
			batch_stmt.executeBatch();
		}

		batch_stmt.close();
	}
}
