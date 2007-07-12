package gawky.incubator;

public class Banko {

	public static boolean method00(String konto)
	{
		return method00(konto, new int[]{2,1,2,1,2,1,2,1,2,0}); 
	}
	
	public static boolean method00(String konto, int[] gewichtung) 
	{
		char[] chars = konto.toCharArray(); 
	
		int tmp = 0;
		int z;
		for(int i=chars.length-1; i >= 0; i--) {
			z = Character.digit(chars[i],10);
			tmp += quer(z*gewichtung[i]);
		}
		
		int p = einer(10-einer(tmp));
		
		return Character.digit(chars[9],10) == p;
	}

	public static boolean method01(String konto)
	{
		return method01(konto, new int[]{1,7,3,1,7,3,1,7,3,0}); 
	}

	public static boolean method01(String konto, int[] gewichtung) 
	{
		char[] chars = konto.toCharArray(); 
	
		int tmp = 0;
		int z;
		for(int i=chars.length-1; i >= 0; i--) {
			z = Character.digit(chars[i],10);
			tmp += z*gewichtung[i];
		}
		
		int p = einer(10-einer(tmp));
		
		return Character.digit(chars[9],10) == p;
	}
	
	public static boolean method02(String konto)
	{
		return method02(konto, new int[]{2,9,8,7,6,5,4,3,2,0}); 
	}
	
	public static boolean method02(String konto, int[] gewichtung) 
	{
		char[] chars = konto.toCharArray(); 
	
		int tmp = 0;
		int z;
		for(int i=chars.length-1; i >= 0; i--) {
			z = Character.digit(chars[i],10);
			tmp += z*gewichtung[i];
		}
		
		int p = (tmp % 11);
		
		if(p == 1)
			return true;
		
		if(p != 0)
			p = 11 - p; 
		
		return Character.digit(chars[9],10) == p;
	}
	
	public static boolean method03(String konto) 
	{
		return method01(konto, new int[]{2,1,2,1,2,1,2,1,2,0}); 
	}
	
	public static boolean method04(String konto) 
	{
		return method02(konto, new int[]{4,3,2,7,6,5,4,3,2,0}); 
	}
	
	public static boolean method05(String konto) 
	{
		return method01(konto, new int[]{1,3,7,1,3,7,1,3,7,0}); 
	}

	public static boolean method06(String konto)
	{
		return method06(konto, new int[]{4,3,2,7,6,5,4,3,2,0}, 0); 
	}
	
	public static boolean method06(String konto, int[] gewichtung)
	{
		return method06(konto, gewichtung, 0); 
	}
	
	public static boolean method06(String konto, int[] gewichtung, int korrektur) 
	{
		char[] chars = konto.toCharArray(); 
	
		int tmp = 0;
		int z;
		for(int i=chars.length-1; i >= 0; i--) {
			z = Character.digit(chars[i],10);
			tmp += z*gewichtung[i];
		}

		int p = (tmp % 11);
		
		if(p == 1)
			p = korrektur;
		
		if(p != 0)
			p = 11 - p; 
		
		return Character.digit(chars[9],10) == p;
	}

	public static boolean method07(String konto)
	{
		return method02(konto, new int[]{10,9,8,7,6,5,4,3,2,0}); 
	}

	public static boolean method08(String konto)
	{
		if(Long.parseLong(konto) <= 60000) 
			return true;
		return method00(konto, new int[]{2,1,2,1,2,1,2,1,2,0}); 
	}

	public static boolean method09(String konto)
	{
		return true; 
	}
	
	public static boolean method10(String konto)
	{
		return method06(konto, new int[]{10,9,8,7,6,5,4,3,2,0}); 
	}
	
	public static boolean method11(String konto)
	{
		return method06(konto, new int[]{10,9,8,7,6,5,4,3,2,0}, 9); 
	}
	
	public static boolean method12(String konto)
	{
		return true; 
	}
	
	
	public static void main(String[] args) 
	{
//		System.out.println( method00("0009290701") );
//		System.out.println( method00("0539290858") );
//		System.out.println( method00("0001501824") );
//		System.out.println( method00("0001501832") );
//
		System.out.println( method06("0094012341") );
		System.out.println( method06("5073321010") );

		System.out.println( method10("0012345008") );
		System.out.println( method10("0087654008") );
		
		
		
	}
	
	public static int modulo(int val, int div)
	{
		return  val - ((int)val / div) * div;
	}
	
	
	
	public static int quer(int s) {
		if(s < 10) return s;
		  return quer((s / 10) + s % 10);
	}
	
	public static int einer(int s) {
		return (s % 10);
	}

}

