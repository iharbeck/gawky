package gawky.incubator;

public class Modulo
{

	public static void main(String[] args)
	{
		//String number = "107035600000000001173468521";
		String number = "70103200000000001171313175"; //-> 5
		//String number = "70103200000000000706633554";  //-> 7

		String str = "123";

		char[] chars = str.toCharArray();

		for(char c : chars)
		{
			int z = Character.digit(c, 10);
			System.out.println(z);
		}

		System.out.println("-->" + modulo(0, 9));

		int sum = 0;

		chars = number.toCharArray();
		for(int i = 0; i < number.length(); i++)
		{
			int x = Character.digit(chars[i], 10);
			int t = 0;
			if(modulo(i, 2) == 0)
			{
				t = x;
			}
			else
			{
				t = (x == 9) ? 9 : modulo(x * 2, 9);
			}

			sum += t;
			System.out.println(t + " : " + sum);
		}

		System.out.println(10 - modulo(sum, 10));

	}

	public static int modulo(int val, int div)
	{
		return val - (val / div) * div;
	}
}
