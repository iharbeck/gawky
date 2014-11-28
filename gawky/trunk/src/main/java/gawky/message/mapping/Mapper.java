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

		for(Desc[] element : mappp)
		{
			element[1].setValue(target_obj,
			        element[0].getValue(source_obj)
			        );
		}
	}
}
