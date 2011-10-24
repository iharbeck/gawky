package gawky.database.generator;

import gawky.database.part.Table;
import gawky.message.Formatter;
import gawky.message.part.Desc;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Generator
{
	private static Log log = LogFactory.getLog(Generator.class);

	private static boolean dotrim = false;
	private static boolean doclone = false;

	public static SimpleDateFormat df_YYYYMMDD       = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat df_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");

	Locale locale = new Locale("de", "DE");

	String customcolumns = "";
	String customparams = "";

	NumberFormat fmt;

	
	boolean debug = false;
	
	public Generator() {
		fmt = NumberFormat.getInstance(Locale.ENGLISH);
		fmt.setGroupingUsed(false);
	}

	public void setDateFormat(String format) {
		df_YYYYMMDD = new SimpleDateFormat(format);
	}

	public void setTimeFormat(String format) {
		df_YYYYMMDDHHMMSS   = new SimpleDateFormat(format);
	}

	public void setLocale(Locale locale)
	{   // EN für ,.  DE für .,
		this.locale = locale;
	}

	public double parseNumber(String val) throws Exception
	{
		return new BigDecimal(val).doubleValue();
	}
	public String formatNumber(double val) throws Exception
	{
		return fmt.format(val);
	}

	public final void fillPart(ResultSet rset, Table part)
	{
		Desc[] descs = part.getCachedDesc();
		Desc   desc;

		Object val = null;

		for(int i=0, x = 1; i < descs.length; i++, x++)
		{
			desc = descs[i];
			
			if(desc.dbname == null || desc.nostore)
				continue;

			try
			{   
			    val = mapGetDBToObjectType(desc, rset, x);

			    if(dotrim && val instanceof String)
			    	val = Formatter.rtrim((String)val);

			    descs[i].setValue(part, val);
			} 
			catch(Exception e) 
			{
				try  { descs[i].setValue(part, ""); } catch(Exception ee) {}
			}
		}

		part.afterFill();

		if(doclone)
			part.doclone();
		
		part.setFound(true);
	}
	
	
	private final Object mapGetDBToObjectType(Desc desc, ResultSet rset, int x) throws Exception 
	{
		Object val = null;
		
	    switch (desc.format) 
	    {
			case Desc.FMT_ASCII :
			case Desc.FMT_BLANK :
			case Desc.FMT_BLANK_ZERO :
			case Desc.FMT_UPPER :
			case Desc.FMT_LOWER :
			case Desc.FMT_BLANK_LETTER :
				val = rset.getString(x);
				break;
			case Desc.FMT_DIGIT :
				//val = formatNumber(rset.getDouble(x));
				val = rset.getDouble(x);
				break;
			case Desc.FMT_DATE :
				val =  df_YYYYMMDD.format( rset.getDate(x) );
				break;
			case Desc.FMT_TIME :
				val =  df_YYYYMMDDHHMMSS.format( rset.getTimestamp(x) );
				break;
			case Desc.FMT_BINARY :
				val = rset.getBytes(x);
				break;
		}

	    return val;
	}

	private final Object mapGetDBToObjectType(Desc desc, ResultSet rset, String name) throws Exception 
	{
		Object val = null;
		
	    switch (desc.format) 
	    {
			case Desc.FMT_ASCII :
			case Desc.FMT_BLANK :
			case Desc.FMT_BLANK_ZERO :
			case Desc.FMT_UPPER :
			case Desc.FMT_LOWER :
			case Desc.FMT_BLANK_LETTER :
				val = rset.getString(name);
				break;
			case Desc.FMT_DIGIT :
				//val = formatNumber(rset.getDouble(name));
				val = rset.getDouble(name);
				break;
			case Desc.FMT_DATE :
				val =  df_YYYYMMDD.format( rset.getDate(name) );
				break;
			case Desc.FMT_TIME :
				val =  df_YYYYMMDDHHMMSS.format( rset.getTimestamp(name) );
				break;
			case Desc.FMT_BINARY :
				val = rset.getBytes(name);
				break;
		}

	    return val;
	}

	
	public final void fillPartPartial(ResultSet rset, Table part) throws Exception
	{
		ResultSetMetaData meta = rset.getMetaData();
		
		int c = meta.getColumnCount();
		
		Desc desc;

		for(int i = 1; i <= c; i++)
		{
			String name = meta.getColumnName(i).toLowerCase();
			desc = part.getDescByName(name);
		
			Object val = null;

			try
			{
				val = mapGetDBToObjectType(desc, rset, name);
				
			    if(dotrim && val instanceof String)  // studip
			    	val = Formatter.rtrim((String)val);

			    desc.setValue(part, val);

			} catch(Exception e) {
				try  { desc.setValue(part, ""); } catch(Exception ee) {}
			}
		}
	}
	

	public StringBuilder generateInsertSQL(Table bean)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;

		StringBuilder sql = new StringBuilder(2000);
		sql.append("INSERT INTO ").append(bean.getTableName()).append(" ( ").append(customcolumns);

		StringBuilder params = new StringBuilder(1000);

		int par = 0;
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];

			if(desc.dbname == null || desc.nostore)
				continue;

			if(desc.isPrimary()) // && bean.getIdgenerator() != null) // ID Element überspringen
				continue;

			sql.append(desc.dbname);  // column name
			params.append("?");		  // parameter

			sql.append(",");
			params.append(",");

			par++;
		}

		if(par > 0) {
			sql.deleteCharAt(sql.length()-1);
			params.deleteCharAt(params.length()-1);
		}

		Desc[] descids = bean.getDescIDs();
		
		if(descids.length > 0) // parameter
		{
			for(int i=0; i < descids.length; i++)
		    {
				if(par > 0)
				{
					sql.append(",");
					params.append(",");
				}
				sql.append(descids[i].dbname);
				params.append("?"); // manuell
				par++;
		    }

		    bean.setParameter(true);
		} else {
			bean.setParameter(false);
		}

		sql.append(" ) VALUES ( ");
		sql.append(customparams).append(params);
		sql.append(" ) ");

		if(log.isDebugEnabled())
			log.debug(sql.toString());

		return sql;
	}

	public static StringBuilder generateCreateSQL(Table bean)
	{
		StringBuilder sql = new StringBuilder(2000);
		
		sql.append("CREATE TABLE ").append(bean.getTableName());

		return generateBASESQL(bean, sql);
	}

	public static StringBuilder generateAlterSQL(Table bean)
	{
		StringBuilder sql = new StringBuilder(2000);
		
		sql.append("ALTER TABLE ").append(bean.getTableName()).append(" ADD ");

		return generateBASESQL(bean, sql);
	}

	private static StringBuilder generateBASESQL(Table bean, StringBuilder sql)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;

		sql.append("(\n");
		
		int par = 0;
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];

			if(desc.dbname == null || desc.nostore)
				continue;

			sql.append(desc.dbname);  // column name

			int len = desc.len;
			
			switch (desc.format) {
				case Desc.FMT_ASCII :
				case Desc.FMT_BLANK :
				case Desc.FMT_BLANK_ZERO :
				case Desc.FMT_BINARY :
				case Desc.FMT_UPPER :
				case Desc.FMT_LOWER :
				case Desc.FMT_BLANK_LETTER :
					
					if(len == 0) 
						len = 20;
										
					sql.append(" VARCHAR2(").append(len).append(")");
					break;
				case Desc.FMT_DIGIT :
					
					if(len > 0) 
						sql.append(" NUMBER(").append(desc.len).append(")");
					else
						sql.append(" NUMBER");
					break;
				case Desc.FMT_DATE :
					sql.append(" DATE ");
					break;
				case Desc.FMT_TIME :
					sql.append(" DATE ");
					break;
				default:
					sql.append(" VARCHAR2(").append(desc.len).append(")");
			}

			sql.append(",\n");
			par++;
		}

		// letztes Komma loeschen
		if(par > 0)
			sql.deleteCharAt(sql.length()-2);

		sql.append(" ) ");

		if(log.isDebugEnabled())
			log.debug(sql.toString());

		return sql;
	}
	
	public StringBuilder generateSelectSQL(Table bean)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;

		StringBuilder sql = new StringBuilder(2000);

		sql.append("SELECT ");

		int par = 0;
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];

			if(desc.dbname == null || desc.nostore)
				continue;

			sql.append("a.").append(desc.dbname);  // column name
			sql.append(",");
			par++;
		}

		if(par > 0)
			sql.deleteCharAt(sql.length()-1);

		sql.append(" FROM ");
		sql.append(bean.getTableName());
		sql.append(" a ");

		return sql;
	}

	public StringBuilder generateFindSQL(Table bean)
	{
		StringBuilder sql = generateSelectSQL(bean);

		// ID Spalte für UPDATE zwingend
		sql.append( generateSelectWHERE(bean) );

		log.debug(sql);

		return sql;
	}

	public StringBuilder generateDeleteSQL(Table bean)
	{
		try {
			StringBuilder sql = new StringBuilder(2000);

			sql.append("DELETE FROM ").append(bean.getTableName());

			// ID Spalte für UPDATE zwingend
			sql.append( generateUpdateWHERE(bean) );

			return sql;
		}
		catch(Exception e) {
			log.debug(bean.getTableName() + " ohne primary key", e);
			return null;
		}
	}

	public StringBuilder generateUpdateSQL(Table bean)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;

		StringBuilder sql = new StringBuilder(2000);
		sql.append("UPDATE ").append(bean.getTableName()).append(" SET ");

		int par = 0;
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];

			if(desc.dbname == null || desc.nostore)
				continue;

			if(desc.isPrimary())
				continue;

			sql.append(desc.dbname).append("=?,");  // column name
			par++;
		}

		if(par > 0)
		 sql.deleteCharAt(sql.length()-1);

		try {
			// ID Spalte für UPDATE zwingend
			sql.append( generateUpdateWHERE(bean) );
		} catch (Exception e) {
			log.error("GAWKY: Primarykey is not defined");
		}

		if(log.isDebugEnabled())
			log.debug(sql.toString());

		return sql;
	}

	
	protected final StringBuilder generateSelectWHERE(Table bean) 
	{
		Desc[] descids = bean.getDescIDs();

		StringBuilder sql = new StringBuilder(2000);

		if(descids.length == 0) 
			return sql;

		sql.append(" WHERE ");
		sql.append("a.")
		   .append(descids[0].dbname)
		   .append("=?");

		for(int i=1; i < descids.length; i++)
	    {
			sql.append(" AND a.").append(descids[i].dbname).append("=?");
	    }
		
		return sql;
	}

	protected final StringBuilder generateUpdateWHERE(Table bean) {

		StringBuilder sql = new StringBuilder(2000);

		sql.append(" WHERE ");

		Desc[] descids = bean.getDescIDs();
		sql.append(descids[0].dbname).append("=?");

		for(int i=1; i < descids.length; i++)
	    {
			sql.append(" AND ").append(descids[i].dbname).append("=?");
	    }

		return sql;
	}
	
	public void fillPreparedStatement(PreparedStatement stmt, Table bean) throws Exception
	{
		fillPreparedStatement(stmt, bean, true);
	}

	public void fillPreparedStatement(PreparedStatement stmt, Table bean, boolean insert) throws Exception
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;

		bean.beforeStore();

		int setter = 1;

		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];

			if(desc.dbname == null || desc.nostore)
				continue;

			if(desc.isPrimary())  // ID überspringen
				continue;

			mapSetObjectToDBType(desc, stmt, setter++, desc.getValue(bean));
		}

		try 
		{
			Desc[] descids = bean.getDescIDs();
			
			for(int i=0; i < descids.length; i++)
		    {
				mapSetObjectToDBType(descids[i], stmt, setter + i, descids[i].getValue(bean));
		    }
		} 
		catch(Exception e) 
		{
			log.error(e);
		}
	}
	
	private final void mapSetObjectToDBType(Desc desc, PreparedStatement stmt, int setter, Object val) throws Exception 
	{
		try 
		{
			switch (desc.format) 
			{
				case Desc.FMT_ASCII :
				case Desc.FMT_BLANK :
				case Desc.FMT_BLANK_ZERO :
				case Desc.FMT_UPPER :
				case Desc.FMT_LOWER :
				case Desc.FMT_BLANK_LETTER :
					stmt.setString(setter, (String)val);
					break;
				case Desc.FMT_DIGIT :
					stmt.setDouble(setter, parseNumber((String)val));
					break;
				case Desc.FMT_DATE :
					stmt.setDate(setter, new Date(df_YYYYMMDD.parse((String)val).getTime()));
					break;
				case Desc.FMT_TIME :
					stmt.setTimestamp(setter, new Timestamp(df_YYYYMMDDHHMMSS.parse((String)val).getTime()));
					break;
				case Desc.FMT_BINARY :
					stmt.setBytes(setter, Formatter.bpad(desc.len, (byte[])val));
					break;
			}
		} catch (Exception e) {
			if(desc.format == Desc.FMT_DIGIT)
				stmt.setDouble(setter, 0);
			else
				stmt.setString(setter, "");
		}
	}

	public String getCustomcolumns() {
		return customcolumns;
	}

	public void setCustomcolumns(String customcolumns) {
		this.customcolumns = customcolumns;
	}

	public String getCustomparams() {
		return customparams;
	}

	public void setCustomparams(String customparams) {
		this.customparams = customparams;
	}

	public static void setDotrim(boolean dotrim) {
		Generator.dotrim = dotrim;
	}

	public static void setDoclone(boolean doclone) {
		Generator.doclone = doclone;
	}
}
