package gawky.database.generator;

import gawky.database.DB;
import gawky.database.part.Column;
import gawky.database.part.Table;
import gawky.global.Option;
import gawky.message.part.Desc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Generator
{
	private static Log log = LogFactory.getLog(Generator.class);	

	SimpleDateFormat df_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat df_HHMMSS = new SimpleDateFormat("HHmmss");
	
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
		df_HHMMSS   = new SimpleDateFormat(format); 
	}
	
	public void setLocale(Locale locale)
	{   // EN f�r ,.  DE f�r .,
		this.locale = locale;
	}
	
	
	public double parseNumber(String val) throws Exception
	{
		return fmt.parse(val).doubleValue();
	}
	public String formatNumber(double val) throws Exception
	{
		return fmt.format(val);
	}
	
	public void fillPart(ResultSet rset, Table part)
	{
		Desc[] descs = part.getCachedDesc();
		Desc   desc;
		
		String val = null;

		for(int i=0; i < descs.length; i++)
		{
			desc = descs[i];
			
			if(desc.dbname == null)
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
						val = rset.getString(i+1); 
						break;
					case Desc.FMT_DIGIT :
						val = formatNumber(rset.getDouble(i+1));
						break;
					case Desc.FMT_DATE :
						val =  df_YYYYMMDD.format( rset.getDate(i+1) );
						break;
					case Desc.FMT_TIME :
						val =  df_HHMMSS.format( rset.getTimestamp(i+1) );
						break;
				}
	
			    descs[i].setValue(part, val);

			} catch(Exception e) {
				try  { descs[i].setValue(part, ""); } catch(Exception ee) {}
			}
		}
		
		part.afterFill();
	}
	public String generateInsertSQL(Table bean)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;
		
		String sql  = "INSERT INTO " + bean.getTableName() + " ( " + customcolumns;
		String params = "";
		
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];
		
			if(desc.dbname == null)
				continue;

			if(bean.isPrimary(desc)) // desc == descid) // && bean.getIdgenerator() != null) // ID Element �berspringen
				continue;
			
			sql    += desc.dbname;  // column name
			params += "?";		    // parameter
			
			sql    += ",";
			params += ",";
		}
		
		sql = sql.substring(0, sql.length()-1);
		params = params.substring(0, params.length()-1);
		
		if(bean.getDescIDs()[0] != null) // descid != null)
		{
			if(bean.getIdgenerator() != null && bean.getIdgenerator().getSequence() != null) { 
				// ID definiert
				
				// ## bean.getIdgenerator() == null 			 --> manual id
				// ## bean.getIdgenerator().getSequence == null  --> autocolumn
				
				// id generated by sequence named here
			    sql    += "," + bean.getDescIDs()[0].dbname;
			    params += "," + bean.getIdgenerator().getSequence(); // generate Methode einf�gen
			    
			    bean.setParameter(false);
			} else { // if(descid != null) /* && bean.getIdgenerator().getSequence() != null) */ {
				// manual parameter
				Desc[] descids = bean.getDescIDs();
				for(int i=0; i < descids.length; i++)
			    {
					sql    += "," + descids[i].dbname;
					params += ",?"; // manuel
			    }
			    
			    bean.setParameter(true);
			}
		} else {
			 bean.setParameter(false);
		}
		
		sql += " ) VALUES ( ";
		sql += customparams + params ;
		sql += " ) ";
		
		log.debug(sql);
		
		return sql;
	}

	public static void main(String[] args) throws Exception {
		Option.init();
		System.out.println(new Generator().generateDesc("kunde"));
	}
	
	public String generateDesc(String table)
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
				descstr += "			new Column(\"" + md.getColumnName(i).toLowerCase() + "\"),\n";
			}
			
			descstr += "		};\n";
			descstr += "	}\n";

			for(int i=1; i <= c; i++){
				descstr += "	private String " + md.getColumnName(i).toLowerCase() + ";\n";
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

	
	
	/**
	 * 		TODO Dialect einf�gen
	 */
	public String generateCreateSQL(Table bean)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;
		
		String sql  = "CREATE TABLE " + bean.getTableName() + " ( ";
		
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];
		
			if(desc.dbname == null) 
				continue;

			sql    += desc.dbname;  // column name
			
			switch (desc.format) { 
				case Desc.FMT_ASCII :
				case Desc.FMT_BLANK :
				case Desc.FMT_BLANK_ZERO :
				case Desc.FMT_BINARY :
				case Desc.FMT_UPPER :
				case Desc.FMT_LOWER :
				case Desc.FMT_BLANK_LETTER :
					sql    += " VARCHAR2(" + desc.len + ")";
					break;
				case Desc.FMT_DIGIT :
					sql    += " NUMBER(" + desc.len + ")";
					break;
				case Desc.FMT_DATE :
					sql    += " DATE ";
					break;
				case Desc.FMT_TIME :
					sql    += " DATE ";
					break;
				default:
					sql    += " VARCHAR2(" + desc.len + ")";
			}

			sql    += ",";
		}

		// letztes Komma loeschen
		sql = sql.substring(0, sql.length()-1);
		
		sql += " ) ";
		
		log.debug(sql);
		
		return sql;
	}
	
	public String generateSelectSQL(Table bean)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;
		
		String sql  = "SELECT ";
		
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];
		
			if(desc.dbname == null)
				continue;
			
			sql += "a." + desc.dbname;  // column name
			sql += ",";
			
		}
		
		sql = sql.substring(0, sql.length()-1);
		
		sql += " FROM ";
		sql += bean.getTableName();
		sql += " a ";
		
		return sql;
	}
	
	public String generateFindSQL(Table bean)
	{
		String sql = generateSelectSQL(bean);
		
		// ID Spalte f�r UPDATE zwingend
		sql += generatePsWHERE(bean);

		log.debug(sql);
		
		return sql;
	}
	
	public String generateDeleteSQL(Table bean)
	{
		try {
			String sql  = "DELETE FROM " + bean.getTableName();
			
			// ID Spalte f�r UPDATE zwingend
			sql += generatePuWHERE(bean);
			
			return sql;
		} 
		catch(Exception e) {
			log.debug(bean.getTableName() + " ohne primary key", e);
			return null;
		}
	}

	public final String generatePsWHERE(Table bean) {

		String sql = " WHERE ";

		Desc[] descids = bean.getDescIDs();
		sql +=  "a." +descids[0].dbname + "=?";

		for(int i=1; i < descids.length; i++)
	    {
			sql +=  " AND a." + descids[i].dbname + "=?";
	    }
		
		return sql;
	}

	public final String generatePuWHERE(Table bean) {

		String sql = " WHERE ";

		Desc[] descids = bean.getDescIDs();
		sql += descids[0].dbname + "=?";

		for(int i=1; i < descids.length; i++)
	    {
			sql +=  " AND " + descids[i].dbname + "=?";
	    }
		
		return sql;
	}

	
	public String generateUpdateSQL(Table bean)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;
		
		String sql  = "UPDATE " + bean.getTableName() + " SET ";
		
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];
		
			if(desc.dbname == null)
				continue;
			
			if(bean.isPrimary(desc)) 
				continue;
			
			sql += desc.dbname + "=?";  // column name
			sql += ",";
		}
		
		sql = sql.substring(0, sql.length()-1);

		try {	
			// ID Spalte f�r UPDATE zwingend
			sql += generatePuWHERE(bean);
		} catch (Exception e) {
			log.error("GAWKY: Primarykey is not defined");
		}
		
		log.debug(sql);
		
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
		
		int setter = 0;
		
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];
			
			if(desc.dbname == null)
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
						stmt.setTimestamp(setter, new Timestamp(df_HHMMSS.parse(val).getTime()));
						break;
				}
			} catch(Exception e) {
				try { stmt.setString(setter, null); } catch (Exception ee) {}
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
}
