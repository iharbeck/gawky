package gawky.database.part;

import java.util.Date;

import gawky.database.generator.Generator;
import gawky.message.part.Desc;

public class TColumn extends Desc {
	
	public static String fill(Date date) {
		return Generator.df_YYYYMMDDHHMMSS.format(date);
	}
	
	public static String fill() {
		return fill(new Date());
	}
	
	public TColumn(String name) {
		super(Desc.FMT_TIME, Desc.CODE_O, 0, name, Desc.ENDCOLON);
	}
}
