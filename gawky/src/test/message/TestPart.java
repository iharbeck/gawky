package test.message;

import junit.framework.TestCase;

public class TestPart extends TestCase {

	public void testParsePart() throws Exception 
	{
	    String str = "H" +
	    			 "abcd" + 
	    			 "1234567890" +
	    			 "1234567890" +
	    			 "1010" + 
	    			 "abc" + "TOT" + 
	    			 "abc" + "DDS" +
	    			 "alpha";
	    
	    SamplePart samplepart = new SamplePart();
	    samplepart.parse(str);
	    
	    assertEquals(samplepart.feld1, "abcd");
	    assertEquals(samplepart.feld2, "1234567890");
	    assertEquals(samplepart.feld3, "1234567890");
	    assertEquals(samplepart.feld4, "1010");
	    assertEquals(samplepart.feld5, "abc");
	    assertEquals(samplepart.feld6, "abc");
	    assertEquals(samplepart.feld7, "alpha");
	}
}
