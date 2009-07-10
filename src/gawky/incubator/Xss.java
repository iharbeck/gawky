package gawky.incubator;

class Xss {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String script = "><(hello) +(neo)[]'\";:/";
		
		String xssfilter = "[><\\[\\]'\";:/]";
		
		System.out.println(script.replaceAll(xssfilter, " "));
		
	}

}
