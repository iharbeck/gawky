package gawky.sort;

import java.math.BigInteger;

public class Entry implements Comparable<Entry>
{
	public Entry(byte[] key, int line, boolean binary)
	{
		this.line = line;
		this.binary = binary;
	
		if(binary)
			val = new BigInteger(key).longValue();  
		else
			this.key = new String(key);
	}
	
	String key;
	int line;
	boolean binary;
	long val;
	
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
	public int compareTo(Entry o)
    {
		if(binary)
			return val > o.getVal() ? 1 : -1;
		else
			return key.compareTo(o.getKey());
    }
	public long getVal()
    {
    	return val;
    }
	public void setVal(long val)
    {
    	this.val = val;
    }
}
