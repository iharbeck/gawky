package example;

import java.util.Random;

public class RAN
{
	static long base = 1234567890L;

	static String[][] val = {
	        { "B", "F", "H", "P", "R", "L", "K", "E", "X", "C" },
	        { "G", "J", "I", "M", "Q", "V", "T", "N", "W", "S" },
	        { "O", "Y", "D", "A", "U", "Z", "K", "N", "X", "C" },
	};

	static int val_range_col = 10;
	static int val_range_row = 3;

	static Random random;

	public static void main(String[] args)
	{
		generate(0, 20000);
		generate(20001, 40000);
	}

	public static void generate(int start, int anzahl)
	{
		for(int i = start; i < start + anzahl; i++)
		{
			System.out.println(generate_num(i));
		}
	}

	public final static String generate_num(int num)
	{
		if(random == null)
		{
			random = new Random(System.currentTimeMillis());
		}

		String ran = "" + (base + num);

		// DDBBBAACCC
		ran = ran.substring(8, 10) + ran.substring(2, 5) + ran.substring(0, 2) + ran.substring(5, 8);

		ran = randomize(ran);
		ran = randomize(ran);
		ran = randomize(ran);
		ran = randomize(ran);
		ran = randomize(ran);

		return ran;
	}

	private final static String randomize(String str)
	{
		int col = random.nextInt(val_range_col);
		int row = random.nextInt(val_range_row);

		return str.replaceAll("" + col, val[row][col]);
	}
}
