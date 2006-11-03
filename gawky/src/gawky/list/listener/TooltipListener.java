package gawky.list.listener;

import gawky.list.datasource.Datasource;

public class TooltipListener implements CellListener
{
	int reference = 0;
	
	public String getAttribute(String name) { return ""; };
	
	public TooltipListener()
	{
		reference = 0;
	}
	
	public String process(Datasource ds, int column)
	{
		return "<span title='" + ds.getValue(reference) + "'>" + ds.getValue(column) + "</span>";
	}
}
