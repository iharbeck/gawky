package gawky.data.datasource;

import java.sql.ResultSet;

public class ResultSetDatasource implements Datasource {

	ResultSet rset;
	
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

	public CellListener getListener(int i) {
		return null;
	}
}
