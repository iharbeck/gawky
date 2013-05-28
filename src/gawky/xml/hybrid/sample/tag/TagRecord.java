package gawky.xml.hybrid.sample.tag;

import gawky.xml.hybrid.XMLStore;
import gawky.xml.hybrid.XMLTagHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class TagRecord implements XMLTagHandler
{
	public static int count = 0;
	
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
		// System.out.println(store.getInfo());
		System.out.println("Kunde: " + store.get("record@client_id"));
		System.out.println("Ort:   " + store.get("record/address@address_line_1"));
		System.out.println("Str:   " + store.get("record/address@address_line_2"));

		ArrayList<String> positionen = store.getAll("record/booking/lineitem@text_line");

		for(String position : positionen)
		{
			System.out.println("Positionen:   " + position);
		}

		count++;
		return true;
	}

	/*
	@XmlElement(name = "record@client_id")
	private String client_id;

	public String getClient_id()
	{
	return client_id;
	}

	public void setClient_id(String client_id)
	{
	this.client_id = client_id;
	}
	
	public static void main(String[] args) throws Exception
	{
	long stat = System.currentTimeMillis();
	
	Field field = TagRecord.class.getDeclaredFields()[0];
	field.setAccessible(true);
	
	Annotation[] annotations = field.getAnnotations(); // getDeclaredFields(); // getAnnotations();
	
	TagRecord rec = new TagRecord();
	
	for(int i=1; i < 3000; i++)
	    field.set(rec, "HELLO");
	
	
	System.out.println(annotations.length);
	
	for(Annotation anno : annotations)
	    if(anno instanceof XmlElement)
		System.out.println(((XmlElement)anno).name());
	
	
	System.out.println(rec.getClient_id());
	
	System.out.println(System.currentTimeMillis() - stat);
	}
	*/
}
