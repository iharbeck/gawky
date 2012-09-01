package gawky.xml.hybrid.sample;

import org.apache.xml.serialize.XMLSerializer;

import gawky.file.Locator;
import gawky.global.Constant;
import gawky.xml.hybrid.XMLReaderPull;
import gawky.xml.hybrid.alternative.XMLReaderStax;
import gawky.xml.hybrid.sample.tag.TagFias2;
import gawky.xml.hybrid.sample.tag.TagRecord;
import gawky.xml.sax.SaxSample;

public class SampleHandler
{
	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();

		String filename = Locator.findPath("", SaxSample.class) + "../data/exampleatt_large.xml";

		XMLReaderPull reader = new XMLReaderPull();

		for(int i = 0; i < 10; i++)
		{
			TagFias2 tagfias2 = new TagFias2();
			TagRecord tagrecord = new TagRecord();
			
			reader.registerTagHandler(tagfias2);
			reader.registerTagHandler(tagrecord);

			reader.process(filename, Constant.ENCODE_UTF8);
		}
		System.out.println("ms " + (System.currentTimeMillis() - start));
	}
}
