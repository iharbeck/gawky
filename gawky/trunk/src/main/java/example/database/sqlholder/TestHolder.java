package example.database.sqlholder;

import gawky.database.sqlholder.SimpleSqlHolder;
import junit.framework.Assert;

import org.junit.Test;

public class TestHolder
{
	@Test
	public void testHolder_absolut() throws Exception
	{
		SimpleSqlHolder holder = new SimpleSqlHolder(TestHolder.class);

		doHolder(holder);
	}

	@Test
	public void testHolder_relativ() throws Exception
	{
		SimpleSqlHolder holder = new SimpleSqlHolder();

		doHolder(holder);
	}

	public void doHolder(SimpleSqlHolder holder) throws Exception
	{
		holder.addParameter("PARAMETER", "123");

		String result = holder.getSql("test");

		Assert.assertEquals("", "HELLO 123", result.trim());
	}
}
