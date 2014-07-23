package gawky.list.datasource;

import gawky.list.listener.CellListener;
import gawky.list.listener.RowListener;

import java.util.ArrayList;

public class ArrayListDatasource implements Datasource
{
	public int getRowCount()
	{
		if(this.columns == null)
			return array.size() - 1;
		else
			return array.size();
	}

	ArrayList<String[]> array;
	Column[] columns;
	ArrayList<Column> columnslist = new ArrayList<Column>();

	int rowcount;
	int pos = -1;

	int columncount;
	int columnshidden = -1;

	RowListener rowlistener = null;

	public RowListener getRowListener()
	{
		return rowlistener;
	}

	public void setRowListener(RowListener rowlistener)
	{
		this.rowlistener = rowlistener;
	}

	/**
	 * Initialisiere Datenquelle
	 * @param array
	 * @param column
	 */
	public ArrayListDatasource(ArrayList<String[]> array, Column[] columns)
	{
		this.array = array;
		this.columns = columns;
		this.rowcount = array.size() - 1;
		this.columncount = columns.length;
	}

	/**
	 * Firstline includes header
	 * @param array
	 */
	public ArrayListDatasource(ArrayList<String[]> array)
	{
		this.array = array;
		this.columns = null;
		this.rowcount = array.size() - 1;
		this.columncount = array.get(0).length;
		pos++;
	}

	public int getColumnsHidden()
	{
		if(columnshidden == -1)
		{
			columnshidden = 0;

			for(int i = 0; i < columncount; i++)
			{
				if(columns[i].getWidth() == Column.HIDDEN)
					columnshidden++;
			}
		}
		return columnshidden;
	}

	public int getColumns()
	{
		return columncount;
	}

	public CellListener getListener(int i)
	{
		if(columns == null)
			return null;
		return columns[i].getListener();
	}

	public String getHead(int i)
	{
		if(columns == null)
			return array.get(0)[i];

		return columns[i].getLable();
	}

	public int getType(int i)
	{
		if(columns == null)
			return Column.TYPE_STRING;
		return columns[i].getType();
	}

	public int getWidth(int i)
	{
		if(columns == null)
			return 0;
		return columns[i].getWidth();
	}

	public Object getValue(int i)
	{
		return array.get(pos)[i];
	}

	public void setCurrRow(int pos)
	{
		this.pos = pos;
		if(columns != null)
			this.pos--;
	}

	public boolean nextRow()
	{
		if(pos < rowcount)
		{
			pos++;
			return true;
		}
		return false;
	}

	public void reset()
	{
		pos = -1;

		// Header überspringen
		if(columns == null)
			pos++;
	}

	public void addColumn(Column col)
	{
		columnslist.add(col);

		columns = columnslist.toArray(new Column[columnslist.size()]);
		this.columncount = columns.length;
		pos = -1;
	}
}
