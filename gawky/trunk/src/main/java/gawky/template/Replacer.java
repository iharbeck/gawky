package gawky.template;

public class Replacer
{

	public static void main(String[] args)
	{

		String text = "asdhkjh >${dumm}< dsfhkj kjhdsfkjhf hdsfkhkfd ${dumm} jdsfhkh ";

		StringBuilder buf = new StringBuilder(text);

		replace(buf, "dumm", "bo");

		System.out.println(buf.toString());
	}

	static void replace(StringBuilder buf, String name, Object value)
	{
		String search = "${" + name + "}";
		int index = buf.indexOf(search);
		if(index >= 0)
		{
			int len = search.length();
			buf.replace(index, index + len, value.toString());
			replace(buf, name, value);
		}
	}
}
