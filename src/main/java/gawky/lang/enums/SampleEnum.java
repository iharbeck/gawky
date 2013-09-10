package gawky.lang.enums;

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
	
	public static Enum<SampleEnum> getEnumById(String id) throws Exception
	{
		return EnumHandler.lookupEnumReverse(SampleEnum.class, "id", id);
	}

}
