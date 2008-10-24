package gawky.database.dialect;

public class Oracle implements Dialect 
{
	public String getLastIDQuery(String param) {
		return "SELECT " + param + ".currval FROM dual";
	}
	
	public String getSequence(String param) {
		return param + ".nextval";
	}
	
	public String getTypeString() {
		return "";
	}
	
	public String getTypeID() {
		return "";
	}
}
