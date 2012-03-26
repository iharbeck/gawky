package gawky.message.mapping;

import gawky.message.part.Desc;
import gawky.message.part.Part;

import java.util.HashMap;

public abstract class MappingPart {
	
	private static volatile HashMap hsMappingDesc = new HashMap();
	
	public final Desc[][] getCachedMappingDesc(Part source_obj, Part target_obj) 
	{
		String key = this.getClass().getName();
		Desc[][] dispatcher = (Desc[][])hsMappingDesc.get(key); 
		
		if(dispatcher == null) 
		{
			MappingDesc[] mappingdesc = getMappingDesc();
			
			dispatcher = new Desc[mappingdesc.length][2];
			
			for(int i=0; i < mappingdesc.length; i++)
			{
				dispatcher[i][0] = source_obj.getDescByName(mappingdesc[i].source);
				dispatcher[i][1] = target_obj.getDescByName(mappingdesc[i].target);
			}
			
			hsMappingDesc.put(key, dispatcher);
		}
		
		return dispatcher;
	}

//	public final MappingDesc[] getCachedMappingDesc() 
//	{
//		String key = this.getClass().getName();
//		MappingDesc[] desc = (MappingDesc[])hsDesc.get(key); 
//		if(desc == null) {
//			desc = getMappingDesc();
//			hsDesc.put(key, desc);
//		}
//		
//		return desc;
//	}
	abstract public MappingDesc[] getMappingDesc();
}
