package gawky.database.part;

import gawky.message.part.Desc;

public class NColumn extends Desc
{
	public NColumn(String name)
	{
		super(Desc.FMT_9, Desc.CODE_O, 0, name, Desc.ENDCOLON);
	}
}
