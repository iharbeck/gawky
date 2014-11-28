package example.message.mapper;

import gawky.message.mapping.Mapper;

public class Tester
{
	public static void main(String[] args) throws Exception
	{
		// 2 Part Elemente
		Client source_obj = new Client();
		Kunde target_obj = new Kunde();

		source_obj.setClient_id("3");
		source_obj.setClientname("ingo");

		// Defines Attributes Mapping between Client / Kunde
		CKMapping ck = new CKMapping();

		// Loop 30 Times
		for(int i = 0; i < 300000; i++)
		{

			source_obj.setClient_id("" + i);

			// do Mapping
			Mapper.process(source_obj, target_obj, ck);

			//System.out.println(target_obj.toString());

		}
	}
}
