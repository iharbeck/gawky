package gawky.range;

public class Range<K, V>
{
	public K range_end;
	public V value;

	public Range(K range_end, V value)
	{
		this.range_end = range_end;
		this.value = value;
	}
}
