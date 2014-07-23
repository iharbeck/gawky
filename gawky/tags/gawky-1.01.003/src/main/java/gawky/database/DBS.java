package gawky.database;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class DBS 
{
	HashMap<String, Object> map = new HashMap<String, Object>();
	ArrayList<Object> param = new ArrayList<Object>();
	
	Connection conn = null;
	int pool = 0; 
	
	public DBS addParam(String key, Object value)
	{
		param.add(value);
		return this;
	}
	
	public void setConnection(Connection conn)
	{
		this.conn = conn;
	}
	
	public void setPool(int pool)
	{
		this.pool = pool;
	}
	
	
	
	
}
