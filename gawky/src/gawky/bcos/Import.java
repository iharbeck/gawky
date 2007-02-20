package gawky.bcos;


public class Import 
{
	public static void main(String[] args) throws Exception 
	{
		long start = System.currentTimeMillis();

		Import importer = new Import();
		importer.run();

		System.out.println("RUN: " + (System.currentTimeMillis() -start));
	}
	
	public void run() throws Exception 
	{
		XMLGenerator xmlgen = new XMLGenerator();
	    
		// Output path
		
		xmlgen.init("c:/test_out.xml");
	    
	    BaseObjectI obj = new BaseObject();

	    
	    long totalamount = 0;
		
	    xmlgen.genxml(obj);
			
	    if(!obj.getAmount().equals(""))
				totalamount += Long.parseLong(obj.getAmount());

	    xmlgen.close();
	}
	
}
