package gawky.database.dialect;

public interface Dialect 
{
	public String getLastIDQuery(String param);
	
	public String getTypeString();
	public String getTypeID();
}
