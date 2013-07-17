package example.xml.hybrid.tnp.tag;

import gawky.xml.hybrid.XMLStore;
import gawky.xml.hybrid.XMLTagHandler;

public class TagImport implements XMLTagHandler
{
	@Override
    public String getTagName()
    {
	    return "tns:import";
    }

	@Override
    public void handle(XMLStore store)
    {
		System.out.println("IMPORT: " + store.get("tns:import@xmlns:tns"));
		//System.out.println(store.getInfo());

		store.clear();
    }

	@Override
    public boolean buildDOM()
    {
	    return false;
    }
}
