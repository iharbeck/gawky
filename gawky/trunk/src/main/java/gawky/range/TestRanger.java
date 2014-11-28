package gawky.range;

import org.junit.Test;

public class TestRanger
{
	@Test
	public void check_number_range() throws Exception
	{
		Ranger<Integer, String> ranger = new Ranger<Integer, String>();

		ranger.add(2, 3, "::0");
		ranger.add(5, 10, "::1");
		ranger.add(100, 200, "::2");

		System.out.println(ranger.freeRange(0, 1) == true);
		System.out.println(ranger.freeRange(0, 3) == false);
		System.out.println(ranger.freeRange(3, 4) == false);
		System.out.println(ranger.freeRange(4, 4) == true);
		System.out.println(ranger.freeRange(4, 5) == false);
		System.out.println(ranger.freeRange(8, 9) == false);
		System.out.println(ranger.freeRange(10, 11) == false);
		System.out.println(ranger.freeRange(11, 20) == true);
		System.out.println(ranger.freeRange(300, 400) == true);

		System.out.println("lookup: " + ranger.lookup(110));
	}

	@Test
	public void check_string_range() throws Exception
	{
		Ranger<String, String> ranger_str = new Ranger<String, String>();

		ranger_str.add("AFFE", "BAUM", "1");
		ranger_str.add("F", "H", "2");
		ranger_str.add("Q", "T", "3");

		System.out.println(ranger_str.freeRange("AFFE", "BAUM") == false);
		System.out.println(ranger_str.freeRange("C", "D") == true);
		System.out.println(ranger_str.freeRange("D", "E") == true);
		System.out.println(ranger_str.freeRange("X", "Z") == true);

		System.out.println(ranger_str.lookup("AMPEL"));
	}

	@Test
	public void check_alpha_range() throws Exception
	{
		Ranger<String, String> ranger_str = new Ranger<String, String>();

		ranger_str.add("600100000:1000", "600100000:2000", "1");
		ranger_str.add("600100000:5000", "600100000:5999", "2");
		ranger_str.add("600100000:6000", "600100000:7000", "3");

		System.out.println(ranger_str.freeRange("600200000:1000", "600200000:2000") == true);
		System.out.println(ranger_str.freeRange("600100000:0500", "600100000:1500") == false);
		System.out.println(ranger_str.freeRange("600100000:3000", "600100000:4000") == true);
		System.out.println(ranger_str.freeRange("600100000:6500", "600100000:6600") == false);

		System.out.println(ranger_str.lookup("600100000:5500"));
	}
}
