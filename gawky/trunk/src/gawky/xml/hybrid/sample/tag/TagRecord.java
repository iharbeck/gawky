package gawky.xml.hybrid.sample.tag;

import gawky.xml.hybrid.XMLStore;
import gawky.xml.hybrid.XMLTagHandler;

import java.util.ArrayList;

public class TagRecord implements XMLTagHandler
{
	@Override
    public String getTagName()
    {
	    return "record";
    }

	@Override
    public boolean buildDOM()
    {
	    return true;
    }	
	
	@Override
    public boolean handle(XMLStore store)
    {
		//System.out.println(store.getInfo());
		System.out.println("Kunde: " + store.get("record@client_id"));
		System.out.println("Ort:   " + store.get("record/address@address_line_1"));
		System.out.println("Str:   " + store.get("record/address@address_line_2"));
		
		ArrayList<String> positionen = store.getAll("record/booking/lineitem@text_line");
		
		for(String position : positionen)
		{
			System.out.println("Positionen:   " + position);
		}
	
		return true;
    }
}
