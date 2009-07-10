package gawky.list.listener;

import gawky.list.datasource.Datasource;

public interface RowListener {
	public String process(Datasource ds, int row);
}
