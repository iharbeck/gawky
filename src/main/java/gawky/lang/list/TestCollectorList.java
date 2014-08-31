package gawky.lang.list;

import org.junit.Assert;
import org.junit.Test;

public class TestCollectorList
{
	@Test
	public void test()
	{
		CollectorList<String, String> cl = new CollectorList<String, String>(200, 200);
		
		cl.add("1", "a1");
		cl.add("1", "a2");
		cl.add("1", "a3");
		cl.add("1", "a4");

		cl.add("2", "b1");
		cl.add("2", "b2");
		
		Assert.assertEquals(cl.get("1").size(), 4);
		Assert.assertEquals(cl.get("2").size(), 2);
	}
	
}
