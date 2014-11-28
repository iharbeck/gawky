package gawky.processor;

public class Parser
{

	int pos = 0; //tag position
	int epos = 0; //tag ende

	int cepos = 0; //child ende

	String xml = null;

	public Parser(byte[] data)
	{
		xml = new String(data);
	}

	public final void toStart()
	{
		pos = 0;
	}

	/**
	 * Find position of EMPTY Tag
	 * @param name
	 * @return
	 */
	public final int toTagEmpty(String name)
	{
		pos = xml.indexOf("<" + name + " ", pos);

		if(pos != -1)
		{
			pos = pos + name.length() + 1;
		}
		else
		{
			return -1;
		}

		epos = xml.indexOf("/>", pos);
		// no children
		cepos = -1;

		return pos;
	}

	public final String getAttribut(String name)
	{
		// Tag String ermitteln
		String tag = xml.substring(pos, epos);

		int apos = tag.indexOf(name + "=\"");

		if(apos == -1)
		{
			return "";
		}

		apos = apos + name.length() + 2;

		return tag.substring(apos, tag.indexOf("\"", apos));
	}

	/**
	 * Find position of FULL Tag with Attributes
	 * @param name
	 * @return
	 */
	public final int toTagFull(String name)
	{
		pos = xml.indexOf("<" + name + " ", pos);

		if(pos != -1)
		{
			pos = pos + name.length() + 1;
		}
		else
		{
			return -1;
		}

		epos = xml.indexOf(">", pos);
		// children to this pos
		cepos = xml.indexOf("</" + name + ">", pos);

		return pos;
	}
}
