package gawky.processor;

public class ParserC
{

	int pos = 0; //tag position
	int epos = 0; //tag ende

	int cepos = 0; //child ende

	String xml = null;
	char[] cxml;

	int size;

	public ParserC(byte[] data)
	{
		xml = new String(data);
		cxml = xml.toCharArray();
		size = xml.length();
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
		epos = size - 1;
		pos = move("<" + name + " ");

		if(pos == -1)
		{
			return -1;
		}

		epos = move("/>");

		// no children
		//cepos = -1;

		return pos;
	}

	public final int move(String name)
	{
		int targetcount = name.length(); // length;

		char first = name.charAt(0); // [0];
		for(int i = pos; i <= epos; i++)
		{
			/* Look for first character. */
			if(cxml[i] != first)
			{
				while(++i <= epos && cxml[i] != first)
				{
					;
				}
			}
			/* Found first character, now look at the rest of v2 */
			if(i <= epos)
			{
				int end = i + targetcount;
				i++;
				for(int k = 1; i < end && cxml[i] == name.charAt(k); i++, k++)
				{
					;
				}
				if(i == end)
				{ // && cxml[i+1] == '=') {
					return i;
				}
			}
		}

		return -1;
	}

	public final String getAttribut(String name)
	{
		int a = move(name);

		if(a == -1)
		{
			return "";
		}

		int start = a + 2; // ="
		a++;

		while(++a <= epos && cxml[a] != '"')
		{
			;
		}

		//System.out.println(xml.substring(start, a) + name);

		return xml.substring(start, a);
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
