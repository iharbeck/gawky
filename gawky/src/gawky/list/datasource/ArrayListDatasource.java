package gawky.list.datasource;

import gawky.list.generator.listener.CellListener;

import java.util.ArrayList;

public class ArrayListDatasource implements Datasource 
{
	ArrayList array;
	Column[] columns;
	
	int rowcount;
	int pos = -1;
	
	int columncount;
	int columnshidden = -1;
	
	/**
	 * Initialisiere Datenquelle
	 * @param array
	 * @param column
	 */
	public ArrayListDatasource(ArrayList array, Column[] columns) 
	{
		this.array  = array;
		this.columns = columns;
		this.rowcount  = array.size()-1;
		this.columncount = columns.length;
	}
	
	public int getColumnsHidden()
	{
		if(columnshidden == -1)
		{
			columnshidden = 0;
			
			for(int i=0; i < columncount; i++)
			{
				if(columns[i].getWidth() == Column.HIDDEN)
					columnshidden++;
			}
		}
		return columnshidden;
	}
	
	
	/**
	 * Firstline includes header
	 * @param array
	 */
	public ArrayListDatasource( ArrayList array ) 
	{
		this.array  = array;
		this.columns = null;
		this.rowcount  = array.size()-1;
		this.columncount = ((Object[])array.get(0)).length;
		pos++;
	}
	 
	public int getColumns() {
		return columncount;
	}
	
	public CellListener getListener(int i) {
		if(columns == null)
			return null;
		return columns[i].getListener();
	}

	public String getHead(int i) {
		if(columns == null)
			return (String)((Object[])array.get(0))[i];
		
		return columns[i].getLable();
	}

	public int getType(int i) {
		if(columns == null)
			return Column.TYPE_STRING;
		return columns[i].getType();
	}
	
	public int getWidth(int i) {
		if(columns == null)
			return 0;
		return columns[i].getWidth();
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
		if(columns == null)
			pos++;
	}
}
