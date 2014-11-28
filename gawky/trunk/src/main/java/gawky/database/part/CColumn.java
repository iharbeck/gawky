package gawky.database.part;

import gawky.message.part.Desc;

public class CColumn extends Desc
{
	public CColumn(String name)
	{
		super(Desc.FMT_A, Desc.CODE_O, 0, name, Desc.ENDCOLON);
	}
}
