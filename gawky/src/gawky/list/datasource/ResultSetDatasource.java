package gawky.list.datasource;

import gawky.list.generator.listener.CellListener;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;

public class ResultSetDatasource implements Datasource {

	ResultSet rset;
	HashMap columns = new HashMap();
	
	int columnshidden = -1;
	
	public ResultSetDatasource(ResultSet rset) 
	{
		this.rset = rset;
	}
	
	public int getColumnsHidden()
	{
		if(columnshidden == -1)
		{
			columnshidden = 0;
			Iterator it = columns.values().iterator();
			
			while(it.hasNext()) 
			{
				if(((Column)it.next()).getWidth() == Column.HIDDEN)
					columnshidden++;
			}
		}
		return columnshidden;
	}
	
	public int getColumns() {
		try {
			return rset.getMetaData().getColumnCount();
		} catch(Exception e) {
			return 0;
		}
	}

	public String getHead(int i) 
	{
		try {
			String name = rset.getMetaData().getColumnName(i+1);
			if(columns.get(name) == null)
				return name;
			else
				return ((Column) columns.get(name)).getLable();
		} catch(Exception e) {
			return "";
		}
	}

	public int getType(int i) {
		// TODO map rset types
		return 0;
	}
	
	public int getWidth(int i) {
		try {
			String name = rset.getMetaData().getColumnName(i+1);
			if(columns.get(name) == null)
				return 0;
			else
				return ((Column) columns.get(name)).getWidth();
		} catch(Exception e) {
			return 0;
		}
	}

	public Object getValue(int i) {
		try {
			return rset.getString(i+1);
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
	
	public void reset() {
		try {
			rset.first();
		} catch(Exception e) {
		}
	}

	public CellListener getListener(int i) 
	{
		Column col = (Column) columns.get(getHead(i));
		
		if(col == null)
			return null;
		
		return col.getListener();
	}
	
	public void addColumn(String name, Column col) {
		columns.put(name, col);
	}
}
