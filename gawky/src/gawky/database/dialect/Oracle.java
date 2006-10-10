package gawky.database.dialect;

public class Oracle implements Dialect 
{
	public String getLastIDQuery(String param) {
		return "SELECT " + param + ".currval FROM DUALS";
	}
	
	public String getTypeString() {
		return "";
	}
	
	public String getTypeID() {
		return "";
	}
}
