package gawky.database.generator;


import gawky.database.DB;
import gawky.database.part.Table;

import java.sql.Connection;

public class IDGeneratorSQL extends IDGenerator 
{
	String sql = null;

	public IDGeneratorSQL(String sql) {
		this.sql = sql;
	}
	
	public String nextVal(Connection conn, Table table) {
		try {
			return DB.getString(conn, sql); 
		} catch (Exception e) {
			return "0";
		}
	}
}
