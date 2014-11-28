package gawky.lang.list;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Collector implements Map<String, Object>
{
	HashMap<String, Object> map = new HashMap<String, Object>();

	private String root = "/";

	public void setRoot(String root)
	{
		if(root.length() == 0)
		{
			this.root = "/";
		}
		else
		{
			int start = root.startsWith("/") ? 1 : 0;
			int end = root.endsWith("/") ? root.length() - 1 : root.length();
			this.root = "/" + root.substring(start, end) + "/";
		}
	}

	@Override
	public Set<String> keySet()
	{
		Set<String> elements = new TreeSet<String>();

		for(String element : map.keySet())
		{
			String el = element.split("/")[1];

			elements.add(el);
		}

		return elements;
	}

	private final String buildKey(String key)
	{
		return root + key;
	}

	@Override
	public Object get(Object key)
	{
		if(((String)key).startsWith("/"))
		{
			setRoot("");
			key = ((String)key).substring(1);
		}

		return map.get(buildKey((String)key));
	}

	public void put(String key, long value)
	{
		map.put(buildKey(key), value);
	}

	public void put(String key, String value)
	{
		map.put(buildKey(key), value);
	}

	public void put(String key, double value)
	{
		map.put(buildKey(key), value);
	}

	public void add(String key, long value)
	{
		String _key = buildKey(key);
		Long v = (Long)map.get(_key);

		if(v == null)
		{
			v = 0L;
		}

		v += value;
		map.put(_key, v);
	}

	public void add(String key, double value)
	{
		String _key = buildKey(key);
		Double v = (Double)map.get(_key);

		if(v == null)
		{
			v = 0D;
		}

		v += value;
		map.put(_key, v);
	}

	public void add(String key, String value)
	{
		String _key = buildKey(key);

		String v = (String)map.get(_key);

		if(v == null)
		{
			v = "";
		}

		v += value;
		map.put(_key, v);
	}

	private void addList()
	{

	}

	private void getList()
	{

	}

	private void putMin()
	{

	}

	private void putMax()
	{

	}

	@Override
	public int size()
	{
		return map.size();
	}

	@Override
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key)
	{
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value)
	{
		return map.containsValue(value);
	}

	@Override
	public Object put(String key, Object value)
	{
		return map.put(key, value);
	}

	@Override
	public Object remove(Object key)
	{
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m)
	{
		map.putAll(m);
	}

	@Override
	public void clear()
	{
		map.clear();
	}

	@Override
	public Collection<Object> values()
	{
		return map.values();
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet()
	{
		return map.entrySet();
	}
}
