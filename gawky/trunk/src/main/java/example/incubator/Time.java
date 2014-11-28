package example.incubator;

class MyTimer
{
	private final long start;

	public MyTimer()
	{
		start = System.currentTimeMillis();
	}

	public long getElapsed()
	{
		return System.currentTimeMillis() - start;
	}
}

public class Time
{
	static final int n = 47500;

	public static void main(String args[])
	{

		// build up string using +

		MyTimer mt = new MyTimer();
		//        String str1 = "";
		//        for (int i = 1; i <= n; i++) {
		//            str1 = str1 + "*";
		//        }
		System.out.println("elapsed time #1 = " +
		        mt.getElapsed());

		// build up string using stringbuffer

		mt = new MyTimer();
		StringBuffer sb = new StringBuffer();
		for(int i = 1; i <= n; i++)
		{
			sb.append("*");
		}
		String str2 = sb.toString();
		System.out.println("elapsed time #2 = " +
		        mt.getElapsed());

		// sanity check

		//        if (!str1.equals(str2)) {
		//          System.out.println("str1/str2 mismatch");
		//        }
	}
}
