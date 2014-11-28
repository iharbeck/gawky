package gawky.processor;

public class Import
{
	XMLGenerator xmlgen = new XMLGenerator();

	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();

		String pluginname = "gawky.bcos.ImportFIASLT";

		//** RUN ImportFIAS
		ImportI plugin = (ImportI)Class.forName(pluginname).newInstance();
		//new ImportFIAS();

		Import importer = new Import();

		importer.open("c:/test_out.xml");

		plugin.execute("", importer);

		importer.close();

		System.out.println("RUN: " + (System.currentTimeMillis() - start));
	}

	public void open(String outfile) throws Exception
	{
		xmlgen.init(outfile);
	}

	public void run(BaseObjectI obj) throws Exception
	{
		xmlgen.genxml(obj);
	}

	public void close() throws Exception
	{
		xmlgen.close();
	}

}
