package gawky.lang.enums.test;

import gawky.lang.enums.EnumHandler;

public enum SampleEnum
{
	ERSTER(
	        "1"),
	ZWEITER(
	        "2"),
	DRITTER(
	        "3");

	String id;

	private SampleEnum(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public static SampleEnum lookupEnumById(String id) throws Exception
	{
		return EnumHandler.lookupEnumReverse(SampleEnum.class, "id", id);
	}

}
