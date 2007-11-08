package gawky.bank;

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
	
	public static boolean method14(String konto)
	{
		return method02(konto, new int[]{0,0,0,7,6,5,4,3,2,0}); 
	}
	
	public static boolean method15(String konto)
	{
		return method06(konto, new int[]{0,0,0,0,0,5,4,3,2,0}); 
	}
	
	public static boolean method32(String konto)
	{
		return method06(konto, new int[]{0,0,0,7,6,5,4,3,2,0}); 
	}
	
	public static boolean method81(String konto)
	{
		return method06(konto, new int[]{0,0,0,7,6,5,4,3,2,0}); 
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

		System.out.println( method32("0009141405") );
		System.out.println( method32("1709107983") );
		System.out.println( method32("0122116979") );
		System.out.println( method32("0121114867") );
		System.out.println( method32("9030101192") );
		System.out.println( method32("9245500460") );

		System.out.println( method81("0000646440") );
		System.out.println( method81("0001359100") );
		
		
		System.out.println( "cc" + luhn("49927398716") );
		System.out.println( "cc" + luhn("") );
		
		
	}

	
	static String MASTER 		   = "1";
	static String VISA   		   = "2";
	static String AMEX   		   = "3";
	static String DINERSCARDBLANCH = "4";
	static String DISCOVER         = "5";
	static String ENROUTE          = "6";
	static String JCB              = "7";

	static String[] vlabel = {"MASTER", 
		                      "VISA", 
		                      "AMEX", 
		   					  "DINERSCARDBLANCH",
		   					  "DISCOVER", 
		   					  "ENROUTE", 
		   					  "JCB"};
		
	static String [][] type = 
	{
			{"51",   MASTER},
			{"52",   MASTER},
			{"53",   MASTER},
			{"54",   MASTER},
			{"55",   MASTER},
			{"4",    VISA},
			{"34",   AMEX},
			{"37",   AMEX},
			{"300",  DINERSCARDBLANCH},
			{"301",  DINERSCARDBLANCH},
			{"302",  DINERSCARDBLANCH},
			{"303",  DINERSCARDBLANCH},
			{"304",  DINERSCARDBLANCH},
			{"305",  DINERSCARDBLANCH},
			{"36",   DINERSCARDBLANCH},
			{"38",   DINERSCARDBLANCH},
			{"6011", DISCOVER},
			{"2014", ENROUTE},
			{"2149", ENROUTE},
			{"3",    JCB},
			{"2131", JCB},
			{"1800", JCB},
	};

	public static boolean luhn(String cardnumber) 
	{
		int[] gewichtung = {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1}; 
		char[] chars = cardnumber.toCharArray(); 
		
		int tmp = 0;
		int z;
		int g=0;
		for(int i=chars.length-1; i >= 0; i--) {
			z = Character.digit(chars[i],10);
			tmp += quer(z*gewichtung[g++]);
		}

		for(int i=0; i < type.length; i++) {
			if(cardnumber.startsWith(type[i][0]))
			{
				System.out.println(vlabel[Integer.parseInt(type[i][1])-1]);
				break;
			}
		}
		
		return tmp%10 == 0;
	}
	
	public static String getType(String cardnumber) 
	{
		for(int i=0; i < type.length; i++) {
			if(cardnumber.startsWith(type[i][0]))
			{
				return(vlabel[Integer.parseInt(type[i][1])-1]);
			}
		}		
		return "UNKNOWN";
	}
	
	
	public static int modulo(int val, int div)
	{
		
		//  http://www.delphipraxis.net/post61384.html
		//  http://www.beachnet.com/~hstiles/cardtype.html
			
		// --------------------------------------------------------- //
		// überprüft auf gültige Kreditkarte Zahl mit Luhn
		// Algorithmus-//---------------------------------------------------------
		// allgemeiner abstrakter Kategorie LuhnCheck
		// {//-------------------------------- // filtern heraus Nichtstelle
		// Buchstaben //-------------------------------- private statische
		// Zeichenkette getDigitsOnly (Zeichenkette s) {StringBuffer digitsOnly
		// = neues StringBuffer (); Putzfrau c; für (intern i = 0; I < s="">= 0;
		// I--) {Stelle = Integer.parseInt (digitsOnly.substring (i, i + 1));
		// wenn (timesTwo) {Addend = Stelle * 2; wenn (Addend > 9) {Addend - =
		// 9; }} sonst {Addend = Stelle; } Summe += Addend; timesTwo =!
		// timesTwo; } interner Modul = Summe % 10; Rückholmodul == 0; } //-----
		// // Test-//----- allgemeine Staticlücke hauptsächlich (Zeichenkette []
		// args) {Zeichenkette cardNumber = „4408 0412 3456 7890“; Boolesches
		// gültiges = LuhnCheck.isValid (cardNumber); System
		
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

