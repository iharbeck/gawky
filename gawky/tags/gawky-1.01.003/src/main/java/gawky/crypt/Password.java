package gawky.crypt;

import java.util.Random;

public class Password {

	public static void main(String[] args) 
	{
		System.out.println(generator(characters, 4) + generator(numbers, 4));
		System.out.println(generator(characters, 4) + generator(numbers, 4));
		System.out.println(generator(characters, 4) + generator(numbers, 4));
		System.out.println(generator(characters, 4) + generator(numbers, 4));
 	}
	
	public static char[] characters = 
		new char[]{'A','B','C', 'D', 'E', 'F', 'G',
				   'H','I','J', 'K', 'L', 'M', 'N',
				   'O','P','Q', 'R', 'S', 'T', 'U',
				   'V','W','X', 'Y', 'Z', 'a', 'b',
				   'c','d','e', 'f', 'g', 'h', 'i',
				   'j','k','l', 'm', 'n', 'o', 'p',
				   'q','r','s', 't', 'u', 'x', 'w',
				   'x','y','z'
	};
	
	public static char[] numbers = 
		new char[]{'0','1','2','3','4','5','6','7','8','9'
	};
	
	static Random rand = new Random(System.currentTimeMillis());
	
	public static String generator(char[] chars, int len) 
	{
		StringBuilder buff = new StringBuilder();
		for (int i=0; i < len; i++)
			 buff.append( chars[ rand.nextInt(chars.length) ]);
 
		return buff.toString();
	}
}
