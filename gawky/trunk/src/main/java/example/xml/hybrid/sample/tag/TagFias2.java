package example.xml.hybrid.sample.tag;

import gawky.xml.hybrid.XMLStore;
import gawky.xml.hybrid.XMLTagHandler;

import java.util.ArrayList;

public class TagFias2 implements XMLTagHandler
{
	@Override
    public String getTagName()
    {
	    return "fias2";
    }

	@Override
    public boolean buildDOM()
    {
	    return false;
    }	
	
	@Override
    public void handle(XMLStore store)
    {
		//System.out.println(store.getInfo());
		System.out.println("IDIDIDID: " + store.get("fias2@uniqueid"));
		
		store.clear();
    }
}
