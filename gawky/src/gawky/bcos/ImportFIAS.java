package gawky.bcos;

public class ImportFIAS {

	public static void main(String[] args) throws Exception 
	{
		long start = System.currentTimeMillis();

		Import importer = new Import();
		
		importer.open("c:/test_out.xml");
		
	    BaseObjectI obj = new BaseObject();

	    obj.setClientid("123");
	    obj.setAmount("2");

	    long totalamount = 0;
		
	    for(int i=0; i < 10000; i++) 
	    {
		    if(!obj.getAmount().equals(""))
					totalamount += Long.parseLong(obj.getAmount());

		    importer.run(obj);
	    }
		
		importer.close();
		
		System.out.println("RUN: " + (System.currentTimeMillis() -start));
	}
}
