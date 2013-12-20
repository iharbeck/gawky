package gawky.global;

public class Bit 
{
	public static int set( int bitholder, int pos )
	{
	  return bitholder | (1 << pos);
	}
	public static int clear( int bitholder, int pos )
	{
	  return bitholder & ~(1 << pos);
	}
	public static int flip( int bitholder, int pos )
	{
	  return bitholder ^ (1 << pos);
	}
	public static boolean test( int bitholder, int pos )
	{
	  return (bitholder & (1 << pos)) != 0;
	}
}
