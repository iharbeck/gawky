package gawky.data.datasource;

import gawky.data.datasource.listener.CellListener;

import java.util.ArrayList;

public class ArrayListDatasource implements Datasource 
{
	ArrayList array;
	Column[] column;
	
	int rowcount;
	int pos = -1;
	
	int columncount;
	
	/**
	 * Initialisiere Datenquelle
	 * @param array
	 * @param column
	 */
	public ArrayListDatasource(ArrayList array, Column[] column) 
	{
		this.array  = array;
		this.column = column;
		this.rowcount  = array.size()-1;
		this.columncount = column.length;
	}
	
	/**
	 * Firstline includes header
	 * @param array
	 */
	public ArrayListDatasource( ArrayList array ) 
	{
		this.array  = array;
		this.column = null;
		this.rowcount  = array.size()-1;
		this.columncount = ((Object[])array.get(0)).length;
		pos++;
	}
	 
	public int getColumns() {
		return columncount;
	}
	
	public CellListener getListener(int i) {
		if(column == null)
			return null;
		return column[i].getListener();
	}

	public String getHead(int i) {
		if(column == null)
			return (String)((Object[])array.get(0))[i];
		
		return column[i].getLable();
	}

	public int getType(int i) {
		if(column == null)
			return Column.TYPE_STRING;
		return column[i].getType();
	}
	
	public int getWidth(int i) {
		if(column == null)
			return 0;
		return column[i].getWidth();
	}

	public Object getValue(int i) {
		return ((Object[])array.get(pos))[i];
	}

	public boolean nextRow() {
		if(pos < rowcount) {
			pos++;
			return true;
		}
		return false;
	}
	
	public void reset() {
		pos = -1;
		
		// Header überspringen
		if(column == null)
			pos++;
	}
}
