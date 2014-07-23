package gawky.xml.simple;

import gawky.global.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Sort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList<String> ar = new ArrayList<String>();
		
		ar.add("3433");
		ar.add("1122");
		ar.add("555");
		ar.add("111");
		ar.add("999");
		
		Collections.sort(ar, new Sorter());
		
		for (Iterator<String> it = ar.iterator(); it.hasNext();) {
			String element = it.next();
			System.out.println(element);
		}
		
	}

}

class Sorter implements Comparator {

	public int compare(Object arg0, Object arg1) {
		long o0 = Format.getLong((String)arg0);
		long o1 = Format.getLong((String)arg1);
		
		int desc = 1;
		
		if(o0 > o1)
			return 1 * desc;
		if(o0 < o1)
			return -1 * desc;
		
		return 0;
	}
	
}
