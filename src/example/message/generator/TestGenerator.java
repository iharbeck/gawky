package example.message.generator;

import example.message.parser.RequestHead;

public class TestGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		long start = System.currentTimeMillis();
		
		double dd = 0;
		
		StringBuilder buf = new StringBuilder(50000);



		for(int i=1; i < 5000; i++)
		{
			RequestHead head = new RequestHead();

			head.setId("11");
    		
    		head.setSortcode("20090500");
    		head.setAccount("7072007");
    		
    		head.setBinary("11");
    		head.setVlist1("var1");
    		head.setVlist2("var2");
    		head.setDatum("20050805");
       		head.setBinary("11");
    		head.setVlist1("var1");
    		head.setVlist2("var2");
    		head.setDatum("20050805");
       		head.setBinary("11");
    		head.setVlist1("var1");
    		head.setVlist2("var2");
    		head.setDatum("20050805");
       		head.setBinary("11");
    		head.setVlist1("var1");
    		head.setVlist2("var2");
    		head.setDatum("20050805");
    		head.setVlist1("var1");
    		head.setVlist2("var2");
    		head.setDatum("20050805");
    		head.setBinary("11");
    		head.setVlist1("var1");
    		head.setVlist2("var2");
    		head.setDatum("20050805");
    		head.setBinary("11");
    		head.setVlist1("var1");
    		head.setVlist2("var2");
    		head.setDatum("20050805");
    		head.setBinary("11");
    		head.setVlist1("var1");
    		head.setVlist2("var2");
    		head.setDatum("20050805");
       		head.setBinary("11");
    		head.setVlist1("var1");
    		head.setVlist2("var2");
    		head.setDatum("20050805");
    	
    		dd += head.buildString().length();
    		
    		//buf.append(head.buildString());
//    		if(i % 100000 == 0)
//    			System.out.println( head.buildString() );
		}
		
		
		System.out.println("done " + (System.currentTimeMillis() - start) + " " + buf.length() + dd);
	}

}
