package gawky.message.parser;

public interface Accessor 
{
	public void setValue(Object obj, String value) throws Exception;
	public String getValue(Object obj) throws Exception;
}
