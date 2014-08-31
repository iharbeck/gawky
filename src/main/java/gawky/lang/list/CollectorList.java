package gawky.lang.list;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectorList<K, T>
{
	HashMap<K, ArrayList<T>> store;

	int capacitylist;
	
	public CollectorList()
	{
		this(200, 50);
	}
	
	public CollectorList(int capacity, int capacitylist)
	{
		store = new HashMap<K, ArrayList<T>>(capacity);
		this.capacitylist = capacitylist;
	}
	
	public void add(K key, T value)
	{
		ArrayList<T> list = store.get(key);

		if(list == null)
		{
			list = new ArrayList<T>(capacitylist);
			store.put(key, list);
		}
		
		list.add(value);
	}
	
	public ArrayList<T> get(K key)
	{
		ArrayList<T> poslist = store.get(key);
		
		if(poslist == null)
			poslist = new ArrayList<T>();
		
		return poslist;
	}
}

