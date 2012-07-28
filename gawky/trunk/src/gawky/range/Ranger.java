package gawky.range;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Ranger<K extends Comparable<K>, V>
{
    public static void main(String[] args)
    {
	Ranger<Integer, String> ranger = new Ranger<Integer, String>();

	ranger.add(2, 3, "::0");
	ranger.add(5, 10, "::1");
	ranger.add(100, 200, "::2");

	System.out.println(ranger.freeRange(0, 1) == true);
	System.out.println(ranger.freeRange(0, 3) == false);
	System.out.println(ranger.freeRange(3, 4) == false);
	System.out.println(ranger.freeRange(4, 4) == true);
	System.out.println(ranger.freeRange(4, 5) == false);
	System.out.println(ranger.freeRange(8, 9) == false);
	System.out.println(ranger.freeRange(10, 11) == false);
	System.out.println(ranger.freeRange(11, 20) == true);
	System.out.println(ranger.freeRange(300, 400) == true);

	System.out.println(ranger.lookup(110));

	Ranger<String, String> ranger_str = new Ranger<String, String>();

	ranger_str.add("AFFE", "BAUM", "1");
	ranger_str.add("F", "H", "2");
	ranger_str.add("Q", "T", "3");

	System.out.println(ranger_str.freeRange("AFFE", "BAUM") == false);
	System.out.println(ranger_str.freeRange("C", "D") == true);
	System.out.println(ranger_str.freeRange("D", "E") == true);
	System.out.println(ranger_str.freeRange("X", "Z") == true);

	System.out.println(ranger_str.lookup("AMPEL"));

	ranger_str = new Ranger<String, String>();

	ranger_str.add("600100000:1000", "600100000:2000", "1");
	ranger_str.add("600100000:5000", "600100000:5999", "2");
	ranger_str.add("600100000:6000", "600100000:7000", "3");

	System.out.println(ranger_str.freeRange("600200000:1000", "600200000:2000") == true);
	System.out.println(ranger_str.freeRange("600100000:0500", "600100000:1500") == false);
	System.out.println(ranger_str.freeRange("600100000:3000", "600100000:4000") == true);
	System.out.println(ranger_str.freeRange("600100000:6500", "600100000:6600") == false);

	System.out.println(ranger_str.lookup("600100000:5500"));
    }

    NavigableMap<K, Range<K, V>> map;

    public Ranger()
    {
	map = new TreeMap<K, Range<K, V>>();
    }

    public boolean freeRange(K von, K bis)
    {
	Map.Entry<K, Range<K, V>> entryleft = map.floorEntry(von);

	// linker Range ist kleiner als von
	if(entryleft != null && von.compareTo(entryleft.getValue().upper) <= 0)
	    return false;

	Map.Entry<K, Range<K, V>> entryright;

	if(entryleft == null)
	    entryright = map.firstEntry();
	else
	    entryright = map.higherEntry(entryleft.getValue().upper);

	// rechter Range ist kleiner als bis
	if(entryright != null && entryright.getKey().compareTo(bis) <= 0)
	    return false;

	return true;
    }

    public void add(K von, K bis, V value)
    {
	map.put(von, new Range<K, V>(bis, value));
    }

    public V lookup(K key)
    {
	Map.Entry<K, Range<K, V>> entry = map.floorEntry(key);

	if(entry == null)
	    return null;

	// if(key <= entry.getValue().upper)
	if(key.compareTo(entry.getValue().upper) <= 0)
	    return entry.getValue().value;

	return null;
    }

}
