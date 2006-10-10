package gawky.database.generator;


import java.sql.Connection;

import gawky.database.DB;
import gawky.database.part.Table;

public class IDGenerator {

	String seq = null;
	
	public IDGenerator() {
		this.seq = "";
	}
	public IDGenerator(String seq) {
		this.seq = seq;
	}
	public String getSequence() {
		return seq;
	}
	
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
}
