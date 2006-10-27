package gawky.data.datasource.listener;

import gawky.data.datasource.Datasource;

public interface CellListener {
	public String getAttribute(String name);
	public String process(Datasource ds, int column);
}
