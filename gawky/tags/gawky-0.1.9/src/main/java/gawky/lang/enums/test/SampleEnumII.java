package gawky.lang.enums.test;

import gawky.lang.enums.EnumHandler;

public enum SampleEnumII
{
	ERSTER(
	        "1"),
	ZWEITER(
	        "2"),
	DRITTER(
	        "3");

	String id;

	private SampleEnumII(String id)
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
	
	public static SampleEnumII lookupEnumById(String id) throws Exception
	{
		return EnumHandler.lookupEnumReverse(SampleEnumII.class, "id", id);
	}

}
