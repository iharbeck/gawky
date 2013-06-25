package example.database;

import java.util.ArrayList;
import java.util.List;

import example.database.part.DaoObject;

import gawky.database.DB;
import gawky.database.part.Table;

public class DBExample 
{
	public static void main(String[] args) throws Exception
	{
		List<String> list = DB.getList("", null);
		
		DaoObject obj =  Table.find(DaoObject.class, "1");
	}

}
