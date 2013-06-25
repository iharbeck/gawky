package gawky.global;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapBuilder {

	public static Map buildMap(Object[][] map) 
	{
		Map hs = new  LinkedHashMap(map.length);
		
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
		
		Map m = MapBuilder.buildMap(map);

		System.out.println(m.get("key2"));
	}
	
}
