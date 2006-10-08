package gawky.database.generator;


import gawky.database.part.Table;
import gawky.message.part.Desc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;


public class Generator 
{
	private static Logger log = Logger.getLogger(Generator.class);
	
	SimpleDateFormat df_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat df_HHMMSS = new SimpleDateFormat("HHmmss");
	
	Locale locale = new Locale("de", "DE");
	
	String customcolumns = "";
	String customparams = "";
	
	public void setDateFormat(String format) {
		df_YYYYMMDD = new SimpleDateFormat(format);
	}
	
	public void setTimeFormat(String format) {
		df_HHMMSS   = new SimpleDateFormat(format); 
	}
	
	public void setLocale(Locale locale)
	{   // EN für ,.  DE für .,
		this.locale = locale;
	}
	
	public double parseNumber(String val) throws Exception
	{
			NumberFormat fmt = NumberFormat.getInstance(locale);
			fmt.setMaximumFractionDigits(2);
			fmt.setMinimumFractionDigits(2);
			fmt.setGroupingUsed(true);
			return fmt.parse(val).doubleValue();
	}
	
	public String generateInsertSQL(Table bean)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;
		
		String sql  = "INSERT into " + bean.getTableName() + " ( " + customcolumns;
		
		String params = "";
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];
		
			if(desc.dbname == null)
				continue;

			if(desc == bean.getDescID()) // ID Element überspringen
				continue;
			
			sql    += desc.dbname;  // column name
			params += "?";		  // parameter
			
			if(i < descs.length-1) { // beim letzten ohne Komma
				sql    += ",";
				params += ",";
			} else if(bean.getDescID() != null) { // ID definiert
			    sql    += "," + bean.getDescID().dbname;
			    params += "," + bean.getIdgenerator().getSequence(); // generate Methode einfügen
			} 
		}
		
		sql += " ) VALUES ( ";
		sql += customparams + params ;
		sql += " ) ";
		
		log.debug(sql);
		
		return sql;
	}
	
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

			if(desc == bean.getDescID()) // ID Element überspringen
				continue;
			
			sql    += desc.dbname;  // column name
			sql    += " varchar(" + desc.len + ")";
			
			if(i < descs.length-1) { // beim letzten ohne Komma
				sql    += ",";
			} else if(bean.getDescID() != null) { // ID definiert
			    sql    += "," + bean.getDescID().dbname;
			    //params += "," + bean.getIdgenerator().getSequence(); // generate Methode einfügen
			} 
		}
		
		sql += " ) ";
		
		log.debug(sql);
		
		return sql;
	}
	
	// NEW
	public String generateFindSQL(Table bean)
	{
		Desc   descs[] = bean.getCachedDesc();
		Desc   desc;
		
		String sql  = "SELECT ";
		
		
		for(int i = 0; i < descs.length; i++)
		{
			desc = descs[i];
		
			if(desc.dbname == null)
				continue;
			
			sql += desc.dbname;  // column name
			
			if(i < descs.length-1) { // beim letzten ohne Komma
				sql += ",";
			}
		}

		sql += " FROM ";
		sql += bean.getTableName();
		
		// ID Spalte für UPDATE zwingend
		sql += " WHERE ";
		sql +=  bean.getDescID().dbname + "=?";
		
		log.debug(sql);
		
		return sql;
		
	}
	
	// NEW
	public String generateDeleteSQL(Table bean)
	{
		String sql  = "DELETE FROM " + bean.getTableName();
		
		// ID Spalte für UPDATE zwingend
		sql += " WHERE ";
		sql +=  bean.getDescID().dbname + "=?";
		
		log.debug(sql);
		
		return sql;
	}

	// NEW
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
			
			if(desc == bean.getDescID()) 
				continue;
			
			sql += desc.dbname + "=?";  // column name
			
			if(i < descs.length-1) { // beim letzten ohne Komma
				sql += ",";
			}
		}

		// ID Spalte für UPDATE zwingend
		sql += " WHERE ";
		sql +=  bean.getDescID().dbname + "=?";
		
		log.debug(sql);
		
		return sql;
	}
	
	public void fillPreparedStatement(PreparedStatement stmt, Table bean)
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
				if(desc == bean.getDescID()) // ID überspringen
					continue;
				
				setter++;

				String val = (String)PropertyUtils.getSimpleProperty(bean, desc.name);
				
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
			int c = stmt.getParameterMetaData().getParameterCount();

			// fehlt noch einer muss es wohl die ID sein.
			if(c == setter+1);
				stmt.setString(setter+1, (String)PropertyUtils.getSimpleProperty(bean, bean.getDescID().name)); 
				
		} catch(Exception e) {
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
