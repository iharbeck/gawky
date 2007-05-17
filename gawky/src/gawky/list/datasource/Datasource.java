package gawky.list.datasource;

import gawky.list.listener.CellListener;

public interface Datasource 
{
	int getColumns();		  // number of columns

	String getHead(int i);	  // headline

	boolean nextRow();		  // next record
	public void reset();

	Object getValue(int i);	  // get value
	int getType(int i);		  // get type
	int getWidth(int i);	  // get width
	
	public CellListener getListener(int i);
	public int getColumnsHidden();
	
	public int getRowCount();
}
