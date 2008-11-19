package gawky.list.listener;

import gawky.list.datasource.Datasource;

import java.text.DecimalFormat;

public class NumberFormatListener implements CellListener
{
	DecimalFormat df = new DecimalFormat  ( ",##0.00" );
	String clazz = "rightalign";
	
	public void setClass(String clazz) {
		this.clazz = clazz;
	}
	
	public void setPattern(String pattern) {
		df = new DecimalFormat( pattern );
	}
	
	public String process(Datasource ds, int column) { 
		String value = (String)ds.getValue(column);
		try {
			return  df.format( Double.parseDouble(value.replaceAll(",", "\\.")) ); 
		} catch (Exception e) {
			return "!" + value;
		}
	}
	public String getAttribute(String name) {
		if(name.equals("class"))
			return clazz;
		return ""; 
	}
	
	static CellListener defaultListener = new NumberFormatListener();

	public static CellListener getInstance() {
		return defaultListener;
	}
}
