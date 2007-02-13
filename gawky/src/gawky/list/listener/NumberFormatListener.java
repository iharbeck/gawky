package gawky.list.listener;

import java.text.DecimalFormat;

import gawky.list.datasource.Datasource;

public class NumberFormatListener implements CellListener
{
	DecimalFormat df = new DecimalFormat  ( ",##0.00" );

	public String process(Datasource ds, int column) { 
		String value = (String)ds.getValue(column);
		try {
			return  df.format( df.parse(value.replaceAll("\\.", ",")) ); 
		} catch (Exception e) {
			return "!" + value;
		}
	}
	public String getAttribute(String name) {
		return ""; 
	}
	
	static CellListener defaultListener = new NumberFormatListener();

	public static CellListener getInstance() {
		return defaultListener;
	}
}
