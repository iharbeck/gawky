package gawky.range;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Ranger<K extends Comparable<K>, V>
{
	NavigableMap<K, Range<K, V>> map;

	public Ranger()
	{
		map = new TreeMap<K, Range<K, V>>();
	}

	public boolean freeRange(K range_start, K range_end)
	{
		Map.Entry<K, Range<K, V>> entryleft = map.floorEntry(range_start);

		// linker Range ist kleiner als von
		if(entryleft != null && range_start.compareTo(entryleft.getValue().range_end) <= 0)
		{
			return false;
		}

		Map.Entry<K, Range<K, V>> entryright;

		if(entryleft == null)
		{
			entryright = map.firstEntry();
		}
		else
		{
			entryright = map.higherEntry(entryleft.getValue().range_end);
		}

		// rechter Range ist kleiner als bis
		if(entryright != null && entryright.getKey().compareTo(range_end) <= 0)
		{
			return false;
		}

		return true;
	}

	public void add(K range_start, K range_end, V value)
	{
		map.put(range_start, new Range<K, V>(range_end, value));
	}

	public V lookup(K key)
	{
		Map.Entry<K, Range<K, V>> entry = map.floorEntry(key);

		if(entry == null)
		{
			return null;
		}

		// key ist innerhalb range
		if(key.compareTo(entry.getValue().range_end) <= 0)
		{
			return entry.getValue().value;
		}

		return null;
	}
}
