package gawky.incubator.sort;

public class Mod10 
{
	static public void main(String [] args)
	{
			if(check10("706511227"))
				System.out.println("good");
			else 
				System.out.println("bad");
			
			System.out.println(isCheckDigitValid());
			System.out.println(getCheckDigit());
	}
 
	static boolean check10(String s)
	{
 
		int c, sum = 0;
		for(int i=0;i<s.length();i++)
		{
			c = (int)s.charAt(i)-48;
			if(c<0 || c>9)
				return false;	// not a number
			if(0 == (i%2))
			{
				c*=2;
				c = (c%10)+c/10;
			}
			sum+=c;
		}
		System.out.println ("sum="+sum);
		return 0 == (sum %10);
	}
	
	static int getCheckDigit()
	{
		// 70651122[?]
	   int weightedSumOfDigits = 7 + z(0) + 6 + z(5) + 1 + z(1) + 2 +z(2) ;
	   
	   if((weightedSumOfDigits % 10) == 0)
		   return 0;
	   else
		   return 10 - (weightedSumOfDigits % 10);
	}
	
	static boolean isCheckDigitValid()
	{
		// 70651122[7]
	   int weightedSumOfDigits = 7 + z(0) + 6 + z(5) + 1 + z(1) + 2 +z(2) +    7 ;
	   
	   return weightedSumOfDigits % 10 == 0;
	}

	static private int z ( int digit)
	{
	   // 0->0 1->2 2->4 3->6 4->8 5->10->1 6->12->3 7->14->5 8->16->7 9->18->9
	   if ( digit == 9 ) 
		   return 9;
	   else 
		   return( digit * 2 ) % 9;
	}
}
