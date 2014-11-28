package example.database;

import example.database.part.DaoObject;

public class GeneratorExample
{

	public static void main(String[] args) throws Exception
	{
		new DaoObject().buildTableCreate();
		new DaoObject().buildTableAlter();
		new DaoObject().buildVars();
	}

}
