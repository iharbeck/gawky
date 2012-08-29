package gawky.xml.hybrid;

public interface XMLTagHandler
{
	public String getTagName(); 
	public boolean buildDOM();
	public boolean handle(XMLStore store);
}
