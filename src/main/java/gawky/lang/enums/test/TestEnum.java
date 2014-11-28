package gawky.lang.enums.test;

import gawky.lang.enums.EnumHandler;

import java.util.Map;

public class TestEnum
{
	public static void main(String[] args) throws Exception
	{
		Map<String, SampleEnum> map = EnumHandler.buildEnumReverseMap(SampleEnum.class, "id");

		System.out.println(map.get("1"));
		System.out.println(map.get("2"));
		System.out.println(map.get("3"));

		for(int i = 0; i < 10; i++)
		{
			System.out.println(EnumHandler.lookupEnumReverse(SampleEnum.class, "id", "3"));
		}

		System.out.println(SampleEnum.lookupEnumById("2"));
		System.out.println(SampleEnumII.lookupEnumById("1"));
	}
}
