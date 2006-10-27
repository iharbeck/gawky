package gawky.data.datasource.generator;

import gawky.data.datasource.Datasource;
import gawky.data.datasource.listener.CellListener;
import gawky.data.datasource.listener.DefaultListener;

public abstract class AbstractTable 
{
	CellListener getListener(Datasource ds, int column) 
	{
		if(ds.getListener(column) == null)
			return DefaultListener.getInstance();
		
		return ds.getListener(column);
	}
}
