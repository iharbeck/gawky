package gawky.list.datasource;

import gawky.list.listener.CellListener;
import gawky.list.listener.RowListener;

public class PagedDatasource implements Datasource
{
	Datasource ds;
	int rows;
	int page;
	int currow;
	int maxrow;

	int pages = 0;

	public RowListener getRowListener()
	{
		return ds.getRowListener();
	}

	public void setRowListener(RowListener rowlistener)
	{
		ds.setRowListener(rowlistener);
	}

	public int getRowCount()
	{
		return ds.getRowCount();
	}

	public int getPages()
	{
		if(pages != 0)
			return pages;

		pages = (int)((getRowCount() / rows));

		if(getRowCount() % rows != 0)
			pages++;

		return pages;
	}

	public PagedDatasource(Datasource ds, int rows, int page)
	{
		this.ds = ds;
		this.rows = rows;
		this.page = page;
		reset();
	}

	public int getColumns()
	{
		return ds.getColumns();
	}

	public int getColumnsHidden()
	{
		return ds.getColumnsHidden();
	}

	public String getHead(int i)
	{
		return ds.getHead(i);
	}

	public CellListener getListener(int i)
	{
		return ds.getListener(i);
	}

	public int getType(int i)
	{
		return ds.getType(i);
	}

	public Object getValue(int i)
	{
		return ds.getValue(i);
	}

	public int getWidth(int i)
	{
		return ds.getWidth(i);
	}

	boolean notdone = true;

	public boolean nextRow()
	{
		if(ds.getRowCount() == 0)
			return false;

		if(notdone == false)
			return notdone;

		notdone = ds.nextRow();

		if(currow + 1 <= maxrow && notdone)
		{
			currow++;
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean hasnext()
	{
		//return currow < maxrow;	
		return currow < getRowCount();
	}

	public boolean hasprev()
	{
		return page > 1;
	}

	public void setCurrRow(int pos)
	{
	}

	public void reset()
	{
		ds.reset();
		notdone = true;

		// move to current position
		// TODO implement setCurrRow
		currow = rows * (page - 1);

		ds.setCurrRow(currow);
		//for(int skip=0; skip < currow; skip++)
		//	ds.nextRow();

		maxrow = currow + rows;
	}
}
