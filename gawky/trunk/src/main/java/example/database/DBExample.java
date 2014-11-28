package example.database;

import example.database.part.DaoObject;
import gawky.database.DB;
import gawky.database.part.Table;

import java.util.List;

public class DBExample
{
	public static void main(String[] args) throws Exception
	{
		List<String> list = DB.getList("", null);

		DaoObject obj = Table.find(DaoObject.class, "1");
	}

}
