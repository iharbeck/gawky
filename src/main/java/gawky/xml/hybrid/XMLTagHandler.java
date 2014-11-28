package gawky.xml.hybrid;

public interface XMLTagHandler
{
	public String getTagName();

	public boolean buildDOM();

	public void handle(XMLStore store);
}
