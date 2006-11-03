package gawky.list.listener;

import gawky.list.datasource.Datasource;

public interface CellListener {
	public String getAttribute(String name);
	public String process(Datasource ds, int column);
}
