package gawky.list.datasource;

import gawky.list.listener.CellListener;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;

public class ResultSetDatasource implements Datasource {

	ResultSet rset;
	HashMap columns = new HashMap();
	
	int columnshidden = -1;
	
	int rowcount = 0;
	
	public int getRowCount() {
		return rowcount;
	}

	public ResultSetDatasource(ResultSet rset) 
	{
		this.rset = rset;
		
		try {
			rset.last();
			rowcount = rset.getRow();
			rset.beforeFirst();
		} catch(Exception e) {
			System.out.println("unable to get row count: " + e);	
		}
		
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
		
		if(columns.size() == 0)
			return Column.TYPE_STRING;
		
		int rsettype;
		try {
			rsettype = rset.getMetaData().getColumnType(i+1);
		} catch (Exception e) {
			return Column.TYPE_STRING;
		}
		
		switch (rsettype) {
			case java.sql.Types.BIGINT:
			case java.sql.Types.DECIMAL:	
			case java.sql.Types.DOUBLE:	
			case java.sql.Types.FLOAT:	
			case java.sql.Types.INTEGER:	
			case java.sql.Types.NUMERIC:	
			case java.sql.Types.REAL:	
			case java.sql.Types.ROWID:	
			case java.sql.Types.SMALLINT:	
			case java.sql.Types.TINYINT:	
				return Column.TYPE_NUMBER;
			case java.sql.Types.DATE:	
			case java.sql.Types.TIME:	
			case java.sql.Types.TIMESTAMP:
				return Column.TYPE_DATE;
			case java.sql.Types.DISTINCT:	
			case java.sql.Types.NVARCHAR:	
			case java.sql.Types.OTHER:	
			case java.sql.Types.REF:	
			case java.sql.Types.SQLXML:	
			case java.sql.Types.STRUCT:	
			case java.sql.Types.VARBINARY:	
			case java.sql.Types.VARCHAR:	
			case java.sql.Types.JAVA_OBJECT:	
			case java.sql.Types.LONGNVARCHAR:	
			case java.sql.Types.LONGVARBINARY:	
			case java.sql.Types.LONGVARCHAR:	
			case java.sql.Types.NCHAR:	
			case java.sql.Types.NCLOB:	
			case java.sql.Types.NULL:	
			case java.sql.Types.ARRAY:
			case java.sql.Types.BINARY:	
			case java.sql.Types.BIT:	
			case java.sql.Types.BLOB:	
			case java.sql.Types.BOOLEAN:	
			case java.sql.Types.CHAR:	
			case java.sql.Types.CLOB:	
			case java.sql.Types.DATALINK:	
				return Column.TYPE_STRING;
		}
//		TYPE_STRING   = 1;
//		TYPE_CURRENCY = 2;
//		TYPE_NUMBER   = 3;
//		TYPE_DATE     = 4;
		
		return Column.TYPE_STRING;
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
			getType(i);
			return rset.getString(i+1);
		} catch(Exception e) {
			return null;
		}
	}
	
	boolean notdone = true;

	public boolean nextRow() {
		if(notdone == false)
			return notdone;
		try {
			notdone = rset.next();
			return notdone;
		} catch(Exception e) {
			return false;
		}
	}
	
	public void reset() {
		try {
			rset.beforeFirst();
			notdone = true;
			
			//rset.first();
		} catch(Exception e) {
		}
	}

	public CellListener getListener(int i) 
	{
		String name;
		try {
			name = rset.getMetaData().getColumnName(i+1);
		} catch (Exception e) {
			return null;
		}
		
		//Column col = (Column) columns.get(getHead(i));
		Column col = (Column) columns.get(name);
		
		if(col == null)
			return null;
		
		return col.getListener();
	}
	
	public void addColumn(String name, Column col) {
		columns.put(name, col);
	}
}
