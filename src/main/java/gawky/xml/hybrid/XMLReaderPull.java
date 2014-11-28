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

	public void process(String filename, String encoding) throws Exception
	{
		InputStream in = new FileInputStream(filename);

		process(in, encoding);

		in.close();
	}

	public void process(InputStream in, String encoding) throws Exception
	{
		XmlPullParserFactory pullfactory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = pullfactory.newPullParser();

		parser.setInput(in, encoding);

		XMLStore store = new XMLStore();

		for(int event = parser.next(); event != XmlPullParser.END_DOCUMENT; event = parser.next())
		{
			if(event == XmlPullParser.START_TAG)
			{
				String tagname = parser.getName();
				XMLTagHandler handler = hshandler.get(tagname);

				if(handler != null)
				{
					store.setLineStart(parser.getLineNumber());
					handleRecord(store, parser, tagname, handler.buildDOM());
					store.setLineEnd(parser.getLineNumber());

					handler.handle(store);
				}
			}
		}

		//parser.close();
	}

	public XMLTagHandler getHandler(String tagname)
	{
		return hshandler.get(tagname);
	}

	public void processAttributes(XMLStore store, XmlPullParser parser)
	{
		for(int i = 0; i < parser.getAttributeCount(); i++)
		{
			store.put(store.level + '@' + parser.getAttributeName(i), parser.getAttributeValue(i));
		}
	}

	public void handleRecord(XMLStore store, XmlPullParser parser, String tagname, boolean buildDOM) throws Exception
	{
		store.level = parser.getName();

		processAttributes(store, parser);

		if(!buildDOM)
		{
			return;
		}

		int tagnamelen = 0;

		for(int event = parser.next(); event != XmlPullParser.END_DOCUMENT; event = parser.next())
		{
			switch(event)
			{
				case XmlPullParser.START_TAG:
					String name = parser.getName();
					store.level += '/' + name;

					tagnamelen = name.length();

					processAttributes(store, parser);
					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals(tagname))
					{
						return;
					}

					store.level = store.level.substring(0, store.level.length() - tagnamelen - 1);
					break;
				case XmlPullParser.TEXT:
					store.put(store.level, parser.getText());
					break;
			}
		}
	}
}
