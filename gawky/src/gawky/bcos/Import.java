package gawky.bcos;


public class Import 
{
	XMLGenerator xmlgen = new XMLGenerator();

	public void open(String outfile) throws Exception {
		xmlgen.init(outfile);
	}
	
	public void run(BaseObjectI obj) throws Exception 
	{
		xmlgen.genxml(obj);
	}
	
	public void close() throws Exception {
		xmlgen.close();		
	}
	
}
