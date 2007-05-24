package gawky.list.listener;

import gawky.list.datasource.Datasource;

public class DefaultRowListener implements RowListener
{
	public String process(Datasource ds, int row) { 
		return ""; 
	}
	
	static RowListener defaultListener = new DefaultRowListener();

	public static RowListener getInstance() {
		return defaultListener;
	}
}
