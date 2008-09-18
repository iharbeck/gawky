package gawky.database.generator;

import gawky.database.DB;
import gawky.database.part.Table;
import gawky.global.Option;
import gawky.message.Formatter;
import gawky.message.part.Desc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
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
	{   // EN f�r ,.  DE f�r .,
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

		String val = null;

		int x = 0;
		for(int i=0; i < descs.length; i++)
		{
			desc = descs[i];

			if(desc.dbname == null || desc.nostore)
				continue;

			try
			{
			    switch (desc.format) {
					case Desc.FMT_ASCII :
					case Desc.FMT_BLANK :
					case Desc.FMT_BLANK_ZERO :
					case Desc.FMT_BINARY :
					case Desc.FMT_UPPER :
					case Desc.FMT_LOWER :
					case Desc.FMT_BLANK_LETTER :
						val = rset.getString(x+1);
						break;
					case Desc.FMT_DIGIT :
						val = formatNumber(rset.getDouble(x+1));
						break;
					case Desc.FMT_DATE :
						val =  df_YYYYMMDD.format( rset.getDate(x+1) );
						break;
					case Desc.FMT_TIME :
						val =  df_YYYYMMDDHHMMSS.format( rset.getTimestamp(x+1) );
						break;
				}

			    if(dotrim)
			    	val = Formatter.rtrim(val);

			    descs[i].setValue(part, val);

			} catch(Exception e) {
				try  { descs[i].setValue(part, ""); } catch(Exception ee) {}
			}
			x++;
		}

		part.afterFill();

		if(doclone)
			part.doclone();
	}
	
	
	public final void fillPartPartial(ResultSet rset, Table part) throws Exception
	{
		//Desc[] descs = part.getCachedDesc();
		Desc   desc;
		
		ResultSetMetaData meta = rset.getMetaData();
		
		int c = meta.getColumnCount();
		
		for(int i = 1; i <= c; i++)
		{
			String name = meta.getColumnName(i).toLowerCase();
			desc = part.getDescByName(name);
		
			String val = null;

			try
			{
			    switch (desc.format) {
					case Desc.FMT_ASCII :
					case Desc.FMT_BLANK :
					case Desc.FMT_BLANK_ZERO :
					case Desc.FMT_BINARY :
					case Desc.FMT_UPPER :
					case Desc.FMT_LOWER :
					case Desc.FMT_BLANK_LETTER :
						val = rset.getString(name);
						break;
					case Desc.FMT_DIGIT :
						val = formatNumber(rset.getDouble(name));
						break;
					case Desc.FMT_DATE :
						val =  df_YYYYMMDD.format( rset.getDate(name) );
						break;
					case Desc.FMT_TIME :
						val =  df_YYYYMMDDHHMMSS.format( rset.getTimestamp(name) );
						break;
				}

			    if(dotrim)
			    	val = Formatter.rtrim(val);

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

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ").append(bean.getTableName()).append(" ( ").append(customcolumns);

		StringBuilder params = new StringBuilder();

		int par = 0;
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];

			if(desc.dbname == null || desc.nostore)
				continue;

			if(bean.isPrimary(desc)) // desc == descid) // && bean.getIdgenerator() != null) // ID Element �berspringen
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

		if(bean.getDescIDs().length > 0) // descid != null)
		{
			if(bean.getIdgenerator() != null && bean.getIdgenerator().getSequence() != null) {
				// ID definiert

				// ## bean.getIdgenerator() == null 			 --> manual id
				// ## bean.getIdgenerator().getSequence == null  --> autocolumn

				// id generated by sequence named here
				if(par > 0)
				{	sql.append(",");
					params.append(",");
				}
			    sql.append(bean.getDescIDs()[0].dbname);
			    params.append(bean.getIdgenerator().getSequence()); // generate Methode einf�gen

			    bean.setParameter(false);
			} else { // if(descid != null) /* && bean.getIdgenerator().getSequence() != null) */ {
				// manual parameter
				Desc[] descids = bean.getDescIDs();
				for(int i=0; i < descids.length; i++)
			    {
					if(par > 0)
					{
						sql.append(",");
						params.append(",");
					}
					sql.append(descids[i].dbname);
					params.append("?"); // manuel
					par++;
			    }

			    bean.setParameter(true);
			}
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

	public static void main(String[] args) throws Exception {
		Option.init();
		System.out.println(Generator.generateDesc("kunde"));
	}

	public static String generateDesc(String table)
	{
		String descstr = "";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			conn = DB.getConnection();

			stmt = conn.createStatement();
			rset = stmt.executeQuery("select * from " + table);

			ResultSetMetaData md = rset.getMetaData();
			int c = md.getColumnCount();

			descstr += "	// Record definition\n";
			descstr += "	public Desc[] getDesc()\n";
			descstr += "	{\n";
			descstr += "		//setDescID(0);  // Manual set ID\n";

			descstr += "		return new Desc[]  {\n";

			for(int i=1; i <= c; i++){
				System.out.println(i + " : " + md.getColumnType(i));
				if(md.getColumnType(i) == Types.NUMERIC || md.getColumnType(i) == Types.INTEGER)
					descstr += "			new NColumn(\"" + md.getColumnName(i).toLowerCase() + "\"), //" + md.getPrecision(i) + "." + md.getScale(i) + "\n";
				else
					descstr += "			new Column(\"" + md.getColumnName(i).toLowerCase() + "\"), //" + md.getPrecision(i) + "." + md.getScale(i) + "\n";
			}

			descstr += "		};\n";
			descstr += "	}\n";

			for(int i=1; i <= c; i++){
				descstr += "	private String " + md.getColumnName(i).toLowerCase() + ";\n";
			}

			descstr += "\n";

			for(int i=1; i <= c; i++){
				descstr += buildGetter(md.getColumnName(i).toLowerCase());
				descstr += "\n";
			}

		} catch(Exception e) {
			System.out.println(e);
		} finally {
			DB.doClose(rset);
			DB.doClose(stmt);
			DB.doClose(conn);
		}
		return descstr;
	}

	public static String buildGetter(String name) {

		String uname = name.substring(0, 1).toUpperCase() + name.substring(1);

		String
		buf =  "	public String get" + uname + "() {\n";
		buf += "		return " + name + ";\n";
		buf += "	}\n\n";

		buf += "	public void set" + uname + "(String " + name + ") {\n";
		buf += "		this." + name + " = " + name + ";\n";
		buf += "	}\n";

		return buf;
	}


	/**
	 * 		TODO Dialect einf�gen
	 */
	public StringBuilder generateCreateSQL(Table bean)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;

		StringBuilder sql = new StringBuilder();

		sql.append("CREATE TABLE ").append(bean.getTableName()).append(" ( ");

		int par = 0;
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];

			if(desc.dbname == null || desc.nostore)
				continue;

			sql.append(desc.dbname);  // column name

			switch (desc.format) {
				case Desc.FMT_ASCII :
				case Desc.FMT_BLANK :
				case Desc.FMT_BLANK_ZERO :
				case Desc.FMT_BINARY :
				case Desc.FMT_UPPER :
				case Desc.FMT_LOWER :
				case Desc.FMT_BLANK_LETTER :
					sql.append(" VARCHAR2(").append(desc.len).append(")");
					break;
				case Desc.FMT_DIGIT :
					sql.append(" NUMBER(").append(desc.len).append(")");
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

			sql.append(",");
			par++;
		}

		// letztes Komma loeschen
		if(par > 0)
			sql.deleteCharAt(sql.length()-1);

		sql.append(" ) ");

		if(log.isDebugEnabled())
			log.debug(sql.toString());

		return sql;
	}

	public StringBuilder generateSelectSQL(Table bean)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;

		StringBuilder sql = new StringBuilder();

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

		// ID Spalte f�r UPDATE zwingend
		sql.append( generatePsWHERE(bean) );

		log.debug(sql);

		return sql;
	}

	public StringBuilder generateDeleteSQL(Table bean)
	{
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("DELETE FROM ").append(bean.getTableName());

			// ID Spalte f�r UPDATE zwingend
			sql.append( generatePuWHERE(bean) );

			return sql;
		}
		catch(Exception e) {
			log.debug(bean.getTableName() + " ohne primary key", e);
			return null;
		}
	}

	public final StringBuilder generatePsWHERE(Table bean) 
	{
		Desc[] descids = bean.getDescIDs();

		StringBuilder sql = new StringBuilder();

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

	public final StringBuilder generatePuWHERE(Table bean) {

		StringBuilder sql = new StringBuilder();

		sql.append(" WHERE ");

		Desc[] descids = bean.getDescIDs();
		sql.append(descids[0].dbname).append("=?");

		for(int i=1; i < descids.length; i++)
	    {
			sql.append(" AND ").append(descids[i].dbname).append("=?");
	    }

		return sql;
	}


	public StringBuilder generateUpdateSQL(Table bean)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ").append(bean.getTableName()).append(" SET ");

		int par = 0;
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];

			if(desc.dbname == null || desc.nostore)
				continue;

			if(bean.isPrimary(desc))
				continue;

			sql.append(desc.dbname).append("=?,");  // column name
			par++;
		}

		if(par > 0)
		 sql.deleteCharAt(sql.length()-1);

		try {
			// ID Spalte f�r UPDATE zwingend
			sql.append( generatePuWHERE(bean) );
		} catch (Exception e) {
			log.error("GAWKY: Primarykey is not defined");
		}

		if(log.isDebugEnabled())
			log.debug(sql.toString());

		return sql;
	}

	public void fillPreparedStatement(PreparedStatement stmt, Table bean)
	{
		fillPreparedStatement(stmt, bean, true);
	}

	public void fillPreparedStatement(PreparedStatement stmt, Table bean, boolean insert)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;

		bean.beforeStore();

		int setter = 0;

		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];

			if(desc.dbname == null || desc.nostore)
				continue;

			try
			{
				if(bean.isPrimary(desc)) // desc == descid) // ID �berspringen
					continue;

				setter++;

				String val = desc.getValue(bean);

			    switch (desc.format) {
					case Desc.FMT_ASCII :
					case Desc.FMT_BLANK :
					case Desc.FMT_BLANK_ZERO :
					case Desc.FMT_BINARY :
					case Desc.FMT_UPPER :
					case Desc.FMT_LOWER :
					case Desc.FMT_BLANK_LETTER :
						stmt.setString(setter, val);
						break;
					case Desc.FMT_DIGIT :
						stmt.setDouble(setter,  parseNumber(val));
						break;
					case Desc.FMT_DATE :
						stmt.setDate(setter, new Date( df_YYYYMMDD.parse(val).getTime()));
						break;
					case Desc.FMT_TIME :
						stmt.setTimestamp(setter, new Timestamp(df_YYYYMMDDHHMMSS.parse(val).getTime()));
						break;
				}
			} catch(Exception e) {

				try {
					if(desc.format == Desc.FMT_DIGIT)
						stmt.setDouble(setter, 0);
					else
						stmt.setString(setter, "");
				} catch (Exception ee) {
				}
			}
		}

		try {
			// Sorry not implemented by Oracle !!!
			//int c = stmt.getParameterMetaData().getParameterCount();

			// fehlt noch einer muss es wohl die ID sein.
			//if(c == setter+1);

			if(!insert || (insert && bean.getParameter()))
			{
				Desc[] descids = bean.getDescIDs();

				for(int i=0; i < descids.length; i++)
			    {
					stmt.setString(setter + i +1, descids[i].getValue(bean));
			    }
			}
		} catch(Exception e) {
			log.error(e);
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
