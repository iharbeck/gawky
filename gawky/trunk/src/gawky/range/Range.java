package gawky.range;

public class Range<K, V>
{
    public K upper;
    public V value;
	  
    public Range(K upper, V value)
    {
	this.upper = upper;
	this.value = value;
    }
}
