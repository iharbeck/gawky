package gawky.data.datasource;

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
		this.rowcount  = array.size();
		this.columncount = column.length;
	}
	
	public int getColumns() {
		return columncount;
	}

	public String getHead(int i) {
		return column[i].getLable();
	}

	public int getType(int i) {
		return column[i].getType();
	}

	public Object getValue(int i) {
		return ((Object[])array.get(pos))[i];
	}

	public boolean nextRow() {
		if(pos++ < rowcount)
			return true;
		return false;
	}

}
