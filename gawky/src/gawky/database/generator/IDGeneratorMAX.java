package gawky.database.generator;


import gawky.database.DB;
import gawky.database.part.Table;

import java.sql.Connection;

public class IDGeneratorMAX extends IDGenerator 
{
	String sql = null;
	String id;
	
	public IDGeneratorMAX() {
	}
	
	/**
	 * return next value and store in field id
	 */
	public String getSequence(Table table) {
		
		if(sql == null) {
			sql = "SELECT max(" + table.getDescIDs()[0].dbname + ") FROM " + table.getTableName();
		}
		
		try {
			id = DB.getString(DB.getConnection(table.getDefaultconnection()), sql); 
		} catch (Exception e) {
			id = "0";
		}
		return id;
	}
	
	/**
	 * 
	 * @param conn
	 * @param table
	 * @return Last generated ID
	 * @throws Exception
	 */
	public String getGeneratedID(Connection conn, Table table) throws Exception{
		return id;
	}
}
