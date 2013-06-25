package gawky.message.part;

public interface Accessor 
{
	public void setValue(Object obj, Object value) throws Exception;
	public Object getValue(Object obj) throws Exception;
}
