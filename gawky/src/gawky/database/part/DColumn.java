package gawky.database.part;

import java.util.Date;

import gawky.database.generator.Generator;
import gawky.message.part.Desc;

public class DColumn extends Desc {
	
	public static String fill(Date date) {
		return Generator.df_YYYYMMDD.format(date);
	}
	
	public DColumn(String name) {
		super(Desc.FMT_DATE, Desc.CODE_O, 0, name, Desc.ENDCOLON);
	}
}
