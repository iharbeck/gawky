package gawky.database.generator;


import gawky.database.DB;
import gawky.database.part.Table;

import java.sql.Connection;

public class IDGeneratorMAX extends IDGenerator 
{
	String sql = null;
	
	public String nextVal(Connection conn, Table table) 
	{
		if(sql == null) 
			sql = "SELECT max(" + table.getPrimdesc().dbname + ")+1  FROM " + table.getTableName();
		
		try {
			String id = DB.getString(conn, sql);
			
			if(id != null)
				return id;
			else
				return "1";
		} catch (Exception e) {
			return "0";
		}
	}
}
