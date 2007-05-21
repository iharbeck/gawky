package test.message.performance;

import gawky.message.Formatter;

public class Stinger {

	public static void main(String[] args) {
		
		long l = System.currentTimeMillis();
		
		String fo = "";
		
		for(int i=0; i < 1000; i++)
		{
			fo = Formatter.rtrim("    asINGO22   ");
		}
		System.out.println(fo);

		System.out.println(System.currentTimeMillis() - l);
	}
}
