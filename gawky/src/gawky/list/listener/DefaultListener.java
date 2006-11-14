package gawky.list.listener;

import gawky.list.datasource.Datasource;

public class DefaultListener implements CellListener
{
	public String process(Datasource ds, int column) { 
		return (String)ds.getValue(column); 
	}
	public String getAttribute(String name) {
		return ""; 
	}
	
	static CellListener defaultListener = new DefaultListener();

	public static CellListener getInstance() {
		return defaultListener;
	}
}
