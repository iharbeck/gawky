package test.file;

import gawky.file.Locator;
import junit.framework.TestCase;

public class TestLocator extends TestCase {

	public void testGetName() throws Exception 
	{
	    String path = Locator.findPath("properties.xml");
	    assertNotNull(path);
	}
}
