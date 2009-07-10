package example.incubator;

public class Modder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String number = "107035600000000001173468521";
		//String number = "70103200000000001171313175";  //-> 5
		//String number = "70103200000000000706633554";  //-> 7
		
		String number = "3";
		int sum = 0;
		
		for(int i=0; i < number.length(); i++)
		{
			int x = Integer.parseInt(number.substring(i, i+1));
			int t = 0;
			if(modulo(i, 2) == 0)
				t = x;
			else
				t = (x==9) ? 9 : modulo(x*2, 9);  
			
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
