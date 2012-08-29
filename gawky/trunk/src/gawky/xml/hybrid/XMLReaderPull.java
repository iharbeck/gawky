package gawky.xml.hybrid;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLReaderPull
{
	HashMap<String, XMLTagHandler> hshandler = new HashMap<String, XMLTagHandler>();

	public void registerTagHandler(XMLTagHandler handler)
	{
		hshandler.put(handler.getTagName(), handler);
	}

	public void process(String filename) throws Exception
	{
		InputStream in = new FileInputStream(filename);

		process(in);

		in.close();
	}

	public void process(InputStream in) throws Exception
	{
		XmlPullParserFactory pullfactory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = pullfactory.newPullParser();
		
		parser.setInput(in, null);
		
		for(int event = parser.next(); event != XmlPullParser.END_DOCUMENT; event = parser.next())
		{
			if(event == XmlPullParser.START_TAG)
			{
				String tagname = parser.getName();
				XMLTagHandler handler = hshandler.get(tagname);

				if(handler != null)
				{
					store.setLineStart(parser.getLineNumber());
					handleRecord(parser, tagname, handler.buildDOM());
					store.setLineEnd(parser.getLineNumber());
					
					if(handler.handle(store))
						store.clear();
				}
			}
		}

		//parser.close();
	}

	public XMLTagHandler getHandler(String tagname)
	{
		return hshandler.get(tagname);
	}

	String level = "";
	XMLStore store = new XMLStore();

	public void processAttributes(XmlPullParser parser)
	{
		for(int i = 0; i < parser.getAttributeCount(); i++)
		{
			store.put(level + '@' + parser.getAttributeName(i), parser.getAttributeValue(i));
		}
	}

	public XMLStore handleRecord(XmlPullParser parser, String tagname, boolean buildDOM) throws Exception
	{
		level = parser.getName();

		processAttributes(parser);

		if(!buildDOM)
			return store;
		
		for(int event = parser.next(); event != XmlPullParser.END_DOCUMENT; event = parser.next())
		{
			switch(event)
			{
				case XmlPullParser.START_TAG:
					level += "/" + parser.getName();
					processAttributes(parser);
					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals(tagname))
						return store;
					else
						level = level.substring(0, level.lastIndexOf('/'));
					break;
				case XmlPullParser.TEXT:
					store.put(level, parser.getText());
					break;
			}
		}

		return store;
	}
}
