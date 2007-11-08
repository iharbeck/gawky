package gawky.processor;

public interface ConsumerI 
{
	public void open();
	public void processline(BaseObjectI obj);
	public void close();
}
