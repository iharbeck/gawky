package gawky.list.datasource;

import gawky.list.listener.CellListener;


public class PagedDatasource implements Datasource {

	Datasource ds;
	int rows;
	int page;
	int currow;
	int maxrow;
	public PagedDatasource(Datasource ds, int rows, int page) {
		this.ds = ds;
		this.rows = rows;
		this.page = page;
		reset();
	}

	public int getColumns() {
		return ds.getColumns();
	}

	public int getColumnsHidden() {
		return ds.getColumnsHidden();
	}

	public String getHead(int i) {
		return ds.getHead(i);
	}

	public CellListener getListener(int i) {
		return ds.getListener(i);
	}

	public int getType(int i) {
		return ds.getType(i);
	}

	public Object getValue(int i) {
		return ds.getValue(i);
	}

	public int getWidth(int i) {
		return ds.getWidth(i);
	}

	public boolean nextRow() {
		return (currow++ < maxrow&&ds.nextRow()  );
	}

	public void reset() {
		ds.reset();
		
		// move to current position
		// TODO implement setCurrRow
		currow = rows * (page-1);
		for(int skip=0; skip < currow; skip++)
			ds.nextRow();

		maxrow = currow + rows;
	}
}
