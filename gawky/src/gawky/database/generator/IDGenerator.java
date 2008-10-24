package gawky.database.generator;


import gawky.database.DB;
import gawky.database.part.Table;

import java.sql.Connection;

public class IDGenerator {

	String seq = null;
	
	public IDGenerator() {
		this.seq = "";
	}
	public IDGenerator(String seq) {
		this.seq = seq;
	}
	
	/**
	 * Get Expression for sequence generation
	 * @param table
	 * @return
	 */
	public String getSequence(Table table) {
		return table.getDialect().getSequence(seq);
	}
	
	/**
	 * 
	 * @param conn
	 * @param table
	 * @return Last generated ID
	 * @throws Exception
	 */
	public String getGeneratedID(Connection conn, Table table) throws Exception{
			
		//manuel -> referenz
		
		if(table.getDialect() == null)
			throw new Exception();

		// ORACLE -> 
		// MYSQL  ->
		
		return DB.getString(conn, table.getDialect().getLastIDQuery(seq));
	}
	
	public final static IDGenerator ID_SEQUENCE(String seq_name)
	{
		return new IDGenerator(seq_name);
	}
	
	public final static IDGenerator ID_AUTO()
	{
		return new IDGenerator(null);
	}
	
	public final static IDGenerator ID_SQL(String sql)
	{
		return new IDGeneratorSQL(sql);
	}
	
	public final static IDGenerator ID_MAX()
	{
		return new IDGeneratorMAX();
	}
}
