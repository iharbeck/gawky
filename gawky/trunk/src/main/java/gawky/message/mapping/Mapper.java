package gawky.message.mapping;

import gawky.message.part.Desc;
import gawky.message.part.Part;

public class Mapper 
{
	public static void process(Part source_obj, 
							   Part target_obj, 
							   MappingPart mappingpart) throws Exception 
	{
		Desc[][] mappp = mappingpart.getCachedMappingDesc(source_obj, target_obj);
		
		for(int x=0; x < mappp.length; x++) 
		{
			mappp[x][1].setValue(target_obj,
			 mappp[x][0].getValue(source_obj) 
			);
		}
	}
}
