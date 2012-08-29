package gawky.xml.hybrid.tnp.tag;

import gawky.xml.hybrid.XMLStore;
import gawky.xml.hybrid.XMLTagHandler;

public class TagDebitorAggregationData implements XMLTagHandler
{
	@Override
    public String getTagName()
    {
	    return "tns:DebitorAggregationData";
    }

	@Override
    public boolean handle(XMLStore store)
    {
		System.out.println("Kunde: " + store.get("tns:DebitorAggregationData/tns:DebitorNumber"));
		//System.out.println(store.getInfo());
		return true;
    }

	@Override
    public boolean buildDOM()
    {
	    return true;
    }
}
