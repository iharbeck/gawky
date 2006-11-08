package gawky.global;

public class Bit 
{
	public static int set( int n, int pos )
	{
	  return n | (1 << pos);
	}
	public static int clear( int n, int pos )
	{
	  return n & ~(1 << pos);
	}
	public static int flip( int n, int pos )
	{
	  return n ^ (1 << pos);
	}
	public static boolean test( int n, int pos )
	{
	  // return (n & 1<<pos) != 0;
	  int mask = 1 << pos;
	  return (n & mask) == mask;
	}
}
