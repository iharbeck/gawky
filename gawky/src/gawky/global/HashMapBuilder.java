package gawky.global;

import java.util.HashMap;

public class HashMapBuilder {

	public static HashMap buildMap(Object[][] map) 
	{
		HashMap hs = new HashMap(map.length);
		
		for(int i=0; i < map.length; i++)
		{
			hs.put(map[i][0], map[i][1]);
		}
		
		return hs;
	}
		
	public static void main(String[] args) {
		
		Object map[][] = { //  key, obj
				{ "key1", "erster" },
			  	{ "key2", "zweiter" },
			  	{ "key3", "dritter" },
		};
		
		HashMap m = HashMapBuilder.buildMap(map);

		System.out.println(m.get("key2"));
	}
	
}
