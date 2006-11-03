package gawky.list.listener;

import gawky.list.datasource.Datasource;

public class LinkListener implements CellListener
{
	int reference = 0;
	
	public LinkListener()
	{
		reference = 0;
	}
	
	public String getAttribute(String name) 
	{ 
		if(name.equals("class"))
			return "BOLDSTYLE";
		else
			return "";
	}
	
	public String process(Datasource ds, int column)
	{
		return "<a href='" + ds.getValue(reference) + "'>" + ds.getValue(column) + "</a>";
	}
}
