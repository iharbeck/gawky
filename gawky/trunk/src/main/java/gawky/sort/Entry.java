package gawky.sort;

public class Entry implements Comparable<Entry>
{
	public Entry(byte[] key, int line)
	{
		this.line = line;
		this.key = new String(key);
	}

	String key;
	int line;

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public int getLine()
	{
		return line;
	}

	public void setLine(int line)
	{
		this.line = line;
	}

	@Override
	public int compareTo(Entry o)
	{
		//val > o.getVal() ? 1 : -1;
		return key.compareToIgnoreCase(o.getKey());
	}
}
