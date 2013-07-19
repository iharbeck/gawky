package example.xml.hybrid.tnp;

import example.xml.hybrid.tnp.tag.TagDebitorAggregationData;
import example.xml.hybrid.tnp.tag.TagImport;
import gawky.global.Constant;
import gawky.xml.hybrid.XMLReaderPull;

public class TNPXMLHandler 
{
	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();

		String filename = "c:/TEST/100055-RSSAGGREGIERTEDATEN-20120719-0001.xml";

		XMLReaderPull reader = new XMLReaderPull();

		reader.registerTagHandler(new TagImport());
		reader.registerTagHandler(new TagDebitorAggregationData());
		
		reader.process(filename, Constant.ENCODE_UTF8);
		
		System.out.println("ms " + (System.currentTimeMillis() - start));
	}
}
