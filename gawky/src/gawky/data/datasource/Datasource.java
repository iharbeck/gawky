package gawky.data.datasource;

public interface Datasource 
{
	int getColumns();

	String getHead(int i);

	boolean nextRow();

	Object getValue(int i);
	int getType(int i);
}
