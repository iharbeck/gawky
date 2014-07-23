package test;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.file.TestLocator;
import test.message.TestPart;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test gawky lib");
		//$JUnit-BEGIN$

		suite.addTestSuite(TestLocator.class);
		suite.addTestSuite(TestPart.class);
		
		//$JUnit-END$
		return suite;
	}

}
