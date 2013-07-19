package gawky.list.generator;

import gawky.list.datasource.Datasource;
import gawky.list.listener.CellListener;
import gawky.list.listener.DefaultListener;
import gawky.list.listener.DefaultRowListener;
import gawky.list.listener.RowListener;

public abstract class AbstractTable 
{
	RowListener getRowListener(Datasource ds) 
	{
		if(ds.getRowListener() == null)
			return DefaultRowListener.getInstance();
		else
			return ds.getRowListener();
	}
	
	CellListener getListener(Datasource ds, int column) 
	{
		if(ds.getListener(column) == null)
			return DefaultListener.getInstance();
		
		return ds.getListener(column);
	}
}
