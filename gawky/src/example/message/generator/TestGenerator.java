package example.message.generator;

import example.message.parser.RequestHead;

public class TestGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		RequestHead head = new RequestHead();

		head.setId("11");
		
		head.setSortcode("20090500");
		head.setAccount("7072007");
		
		head.setBinary("11");
		head.setVlist1("var1");
		head.setVlist2("var2");
		head.setDatum("20050805");
		
		System.out.println( head.toString() );
	}

}
