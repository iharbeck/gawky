package gawky.data.datasource.listener;

import gawky.data.datasource.Datasource;

public class LinkListener implements CellListener
{
	String reference;
	
	public String getAttribute(String name) 
	{ 
		if(name.equals("class"))
			return "BOLDSTYLE";
		else
			return "";
	}
	
	public String process(Datasource ds, int column)
	{
		return "<a href=''>" + ds.getValue(column) + "</a>";
	}
}
