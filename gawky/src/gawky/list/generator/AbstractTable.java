package gawky.list.generator;

import gawky.list.datasource.Datasource;
import gawky.list.listener.CellListener;
import gawky.list.listener.DefaultListener;

public abstract class AbstractTable 
{
	CellListener getListener(Datasource ds, int column) 
	{
		if(ds.getListener(column) == null)
			return DefaultListener.getInstance();
		
		return ds.getListener(column);
	}
}
