package gawky.data.datasource;

import gawky.data.datasource.listener.CellListener;

import java.sql.ResultSet;
import java.util.HashMap;

public class ResultSetDatasource implements Datasource {

	ResultSet rset;
	HashMap column = new HashMap();
	
	public ResultSetDatasource(ResultSet rset) 
	{
		this.rset = rset;
	}
	
	public int getColumns() {
		try {
			return rset.getMetaData().getColumnCount();
		} catch(Exception e) {
			return 0;
		}
	}

	public String getHead(int i) {
		try {
			return rset.getMetaData().getColumnName(i);
		} catch(Exception e) {
			return "";
		}
	}

	public int getType(int i) {
		// TODO map rset types
		return 0;
	}
	
	public int getWidth(int i) {
		return 0;
	}

	public Object getValue(int i) {
		try {
			return rset.getObject(i);
		} catch(Exception e) {
			return null;
		}
	}
	
	public boolean nextRow() {
		try {
			return rset.next();
		} catch(Exception e) {
			return false;
		}
	}

	public CellListener getListener(int i) 
	{
		Column col = (Column) column.get(getHead(i));
		
		if(col == null)
			return null;
		
		return col.getListener();
	}
	
	public void addColumn(String name, Column col) {
		column.put(name, col);
	}
}
