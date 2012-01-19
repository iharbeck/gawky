package gawky.database.part;

public interface Result 
{
	public <T extends Table> void process(T inst);
}
