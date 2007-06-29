package gawky.database.part;

import gawky.message.part.Desc;

public class Column extends Desc {
	public Column(String name) {
		super(Desc.FMT_A, Desc.CODE_O, 0, name, Desc.END01);
	}
}
