package example.database;

import gawky.database.sqlholder.SimpleSqlHolder;

public class TestHolder
{
	public static void main(String[] args) throws Exception
    {
	    SimpleSqlHolder holder = new SimpleSqlHolder(TestHolder.class);
	    
	    holder.addParameter("PAR", "123");
	    
	    System.out.println(holder.getSql("test"));
    }
}
