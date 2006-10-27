package gawky.data.datasource;

public interface Datasource 
{
	int getColumns();		  // number of columns

	String getHead(int i);	  // headline

	boolean nextRow();		  // next record

	Object getValue(int i);	  // get value
	int getType(int i);		  // get type
	
	public CellListener getListener(int i);
}
