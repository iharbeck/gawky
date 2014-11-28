package gawky.list.listener;

import gawky.list.datasource.Datasource;

import java.text.DecimalFormat;

public class NumberFormatListener implements CellListener
{
	DecimalFormat df = new DecimalFormat(",##0.00");
	String cssclass = "rightalign";

	public void setClass(String cssclass)
	{
		this.cssclass = cssclass;
	}

	public void setPattern(String pattern)
	{
		df = new DecimalFormat(pattern);
	}

	@Override
	public String process(Datasource ds, int column)
	{
		String value = (String)ds.getValue(column);
		try
		{
			return df.format(Double.parseDouble(value.replaceAll(",", "\\.")));
		}
		catch(Exception e)
		{
			return "!" + value;
		}
	}

	@Override
	public String getAttribute(String name)
	{
		if(name.equals("class"))
		{
			return cssclass;
		}
		return "";
	}

	static CellListener defaultListener = new NumberFormatListener();

	public static CellListener getInstance()
	{
		return defaultListener;
	}
}
