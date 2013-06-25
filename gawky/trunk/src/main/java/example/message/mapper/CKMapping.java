package example.message.mapper;

import gawky.message.mapping.MappingDesc;
import gawky.message.mapping.MappingPart;

public class CKMapping extends MappingPart {

	public MappingDesc[] getMappingDesc() 
	{
		return new MappingDesc[] {
			new MappingDesc("client_id",   "kunde_id"),
			new MappingDesc("clientname",  "kundename"),
		};
	}
}
