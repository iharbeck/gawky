package example.incubator;

import java.util.ArrayList;
import java.util.HashSet;

public class Trove
{

	public static void main(String[] args)
	{

		long start = System.currentTimeMillis();

		ArrayList sett_ = new ArrayList<String>(5000);

		for(int i = 0; i < 6000; i++)
		{
			sett_.add(Integer.toString(i));
		}

		HashSet sett = new HashSet<String>(sett_);

		HashSet sett2 = new HashSet<String>(5000);

		for(int i = 0; i < 1000; i++)
		{
			sett2.add(Integer.toString(i));
		}

		System.out.println(System.currentTimeMillis() - start);

		sett_.retainAll(sett2);

		System.out.println(System.currentTimeMillis() - start);

	}
}
