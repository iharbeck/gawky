package gawky.list.listener;

import gawky.list.datasource.Datasource;

public class LinkListener implements CellListener
{
	int reference;
	String classname;
	
	public LinkListener(int reference)
	{
		this.reference = reference;
	}
	
	public String getAttribute(String name) 
	{ 
		if(name.equals("class"))
			return classname;
		else
			return "";
	}
	
	public String process(Datasource ds, int column)
	{
		return "<a href='" + ds.getValue(reference) + "'>" + ds.getValue(column) + "</a>";
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}
}
