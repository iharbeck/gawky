package test;

import gawky.database.DB;
import gawky.global.Option;

import java.sql.Connection;

public class Connect
{
	public static void main(String[] args) throws Exception
    {
	    Option.init();
	    
	    for(int i = 0; i < 300; i++)
	    {
	    	Connection conn = DB.getConnection();
	    	//System.out.println(DB.dbpool.get("0").getTotalCreatedConnections() + " " + conn + " " + conn.isClosed());
	    
	    	DB.doClose(conn);
	    	System.out.println(DB.dbpool.get("0").getTotalCreatedConnections() + " " + conn + " " + conn.isClosed());
	    }
    }
}
