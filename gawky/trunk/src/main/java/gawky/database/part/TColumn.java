package gawky.database.part;

import gawky.database.generator.Generator;
import gawky.message.part.Desc;

import java.util.Date;

public class TColumn extends Desc
{

	public static String fill(Date date)
	{
		return Generator.df_YYYYMMDDHHMMSS.print(date.getTime());
	}

	public static String fill()
	{
		return fill(new Date());
	}

	public TColumn(String name)
	{
		super(Desc.FMT_TIME, Desc.CODE_O, 0, name, Desc.ENDCOLON);
	}
}
