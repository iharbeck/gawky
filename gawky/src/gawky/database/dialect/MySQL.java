package gawky.database.dialect;

public class MySQL implements Dialect 
{
	public String getLastIDQuery(String param) {
		return "SELECT LAST_INSERT_ID()";
	}

	public String getTypeString() {
		return "";
	}
	
	public String getTypeID() {
		return "";
	}
}
