package gawky.xml.hybrid;

import java.util.ArrayList;

public final class XMLStore
{
	ArrayList<Entry> items = new ArrayList<Entry>(500);
	
	public long line_start;
	public long line_end;

	public void setLineStart(long line_start)
	{
		if(this.line_start > 0)
			return;
		
		this.line_start = line_start;
	}
	
	public void setLineEnd(long line_end)
	{
		this.line_end = line_end;
	}
	
	public String getInfo()
	{
		return "@" + line_start + ':' + line_end;
	}
	
	public void clear()
	{
		line_start = 0;
		line_end = 0;
		items.clear();
	}

	public void put(String xpath, String value)
	{
		value = value.trim();
		if(value.length() == 0)
			return;

		items.add(new Entry(xpath, value));
	}

	public String get(String xpath)
	{
		for(Entry entry : items)
		{
			if(entry.xpath.equals(xpath))
				return entry.value;
		}

		return "";
	}

	public ArrayList<String> getAll(String xpath)
	{
		ArrayList<String> list = new ArrayList<String>(50);

		for(Entry entry : items)
		{
			if(entry.xpath.equals(xpath))
				list.add(entry.value);
		}

		return list;
	}

	private final class Entry
	{
		public Entry(String xpath, String value)
		{
			this.xpath = xpath;
			this.value = value;
		}

		String xpath;
		String value;
	}
}
