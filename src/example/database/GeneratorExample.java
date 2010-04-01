package example.database;

import example.database.part.DaoObject;

public class GeneratorExample {
	
	public static void main(String[] args) throws Exception 
	{
		System.out.println( new DaoObject().buildTableCreate() );
		System.out.println( new DaoObject().buildTableAlter() );
		System.out.println( new DaoObject().buildVars() );
	}

}
