package gawky.data.datasource.listener;

import gawky.data.datasource.Datasource;

public class LinkListener implements CellListener
{
	String reference;
	
	public String getAttribute(String name) { return ""; };
	public String process(Datasource ds, int column)
	{
		return "link";
	}
}
