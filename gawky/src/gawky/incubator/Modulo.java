package gawky.incubator;

public class Modulo {

	public static void main(String[] args) 
	{
		String number = "107035600000000001173468521";
		
		System.out.println(modulo(0, 9));
		
		int sum = 0;
		
		for(int i=0; i < number.length(); i++)
		{
			int x = Integer.parseInt(number.substring(i, i+1));
			int t = 0;
			if(modulo(i, 2) == 0)
				t = modulo(x*2, 9);  // 18 bug
			else
				t = x;
			
			sum += t;
			System.out.println(t + " : " + sum);
		}
		
		System.out.println(10 - modulo(sum, 10));
		
		
	}
	
	public static int modulo(int val, int div)
	{
		return  val - ((int)val / div) * div;
	}
}

