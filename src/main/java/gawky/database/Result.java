package gawky.database;

import java.util.HashMap;

public class Result
{
	transient String[][] list = new String[1000][2];
	transient HashMap<String, Integer> head = new HashMap<String, Integer>(50);
	transient int columns = 0;
	transient int ipos = 0;
	transient int pos = 0;
	transient String[] current;

	public Result(int columns)
	{
		this.columns = columns;
	}

	public final void add(String name, int i)
	{
		head.put(name, i);
	}

	public final void put(int i, String value)
	{
		current[i] = value;
	}

	public final String get(String key)
	{
		return current[head.get(key)];
	}

	public final String get(int i)
	{
		return current[i];
	}

	public final void insert()
	{
		current = list[ipos++];
		//list.add(current);
	}

	public final int size()
	{
		//return list.size();
		return ipos;
	}

	public final void next()
	{
		current = list[pos++];
		//current = list.get(pos);
		//pos++;
	}
}
